package com.example.securitydemo.controller.sku;

import com.example.securitydemo.bean.dto.sku.SkuDto;
import com.example.securitydemo.bean.dto.sku.SkuImportDto;
import com.example.securitydemo.bean.vo.common.ExcelReadResult;
import com.example.securitydemo.bean.vo.sku.Sku;
import com.example.securitydemo.common.BusiException;
import com.example.securitydemo.common.result.R;
import com.example.securitydemo.service.sku.SkuService;
import com.example.securitydemo.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@RestController
@RequestMapping("/sku")
@Slf4j
public class SkuController {
    @Autowired
    private SkuService skuService;
    @GetMapping("/query")
    public R query(SkuDto skuDto) {
        return R.ok(skuService.queryAll(skuDto));
    }
    @PostMapping("/add")
    public R add(SkuDto skuDto,
                 @RequestParam(value = "files",required = false) List<MultipartFile> files) throws Exception {
        if(Objects.nonNull(files)){
            skuDto.setImage(files.get(0).getBytes());
        }
        return R.ok(skuService.save(skuDto));
    }
    @PostMapping("/update")
    public R update(SkuDto skuDto,@RequestParam(value = "files",required = false) List<MultipartFile> files) throws Exception {
        log.info("skuDto:{},files:{}",skuDto,files);
        if(Objects.nonNull(files)){
            skuDto.setImage(files.get(0).getBytes());
        }
        if (skuService.update(skuDto)) {
            return R.ok(true);
        }
        return R.fail("更新数据有问题");
    }
    @GetMapping("/delete")
    public R delete(SkuDto skuDto) {
        if (skuService.delete(skuDto)) {
            return R.ok(true);
        }
        return R.fail("删除数据有问题");
    }
    @GetMapping("/image/{id}")
    public void image(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
        log.info("query sku image:{}",id);
        List<Sku> skus = skuService.export(SkuDto.builder().id(id).build());
        byte[] imageBytes = null;
        if(!CollectionUtils.isEmpty(skus)){
            imageBytes = skus.get(0).getImage();
        }
        response.setContentType("image/jpeg");
        ServletOutputStream os = response.getOutputStream();
        os.write(imageBytes);
        os.flush();
        os.close();
    }
    @GetMapping("/export")
    public void export(SkuDto skuDto,HttpServletResponse response) throws IOException {
        log.info("query sku export:{}",skuDto);
        List<Sku> skus = skuService.export(skuDto);
        skus.forEach(sku -> {
            if(sku.getImage() == null){
                sku.setImage(new byte[0]);
            }
        });
        ExcelUtils.export(response,"商品细分","第一页",skus,Sku.class);
    }
    @PostMapping("/batchExport")
    public R batchExport(MultipartFile file) throws IOException {
        log.info("batch insert:{}",file.getOriginalFilename());
        ExcelReadResult<SkuImportDto> skuExcelReadResult = ExcelUtils.readExcel(file, SkuImportDto.class, 1, new Consumer<SkuImportDto>() {
            /**
             * Performs this operation on the given argument.
             *
             * @param sku the input argument
             */
            @Override
            public void accept(SkuImportDto sku) {
                if (sku.getProductId() == null) {
                    throw  new BusiException("商品编号不能为空");
                }
                if (sku.getSkuName() == null) {
                    throw new BusiException("商品细分名称不能为空");
                }
                if (sku.getPrice() == null) {
                    throw new BusiException("价格不能为空");
                }
                if (sku.getStock() == null) {
                    throw new BusiException("库存数量不能为空");
                }
                if (sku.getImage() == null) {
                    sku.setImage(new byte[0]);
                }
            }
        },"导入模板");
        log.info("读取到数据的失败数量:{},成功数量:{}",skuExcelReadResult.getErrorList().size(),skuExcelReadResult.getSuccessList().size());
        if (CollectionUtils.isEmpty(skuExcelReadResult.getErrorList())){
            skuExcelReadResult.getSuccessList().forEach(sku -> {
                skuService.save(SkuDto.builder()
                        .skuName(sku.getSkuName())
                        .price(new BigDecimal(sku.getPrice()))
                        .productId(Integer.valueOf(sku.getProductId()))
                        .stock(sku.getStock())
                        .image(sku.getImage()).build());
            });
            return R.ok(skuExcelReadResult.getSuccessList());
        }
        return R.fail("数据校验不通过",skuExcelReadResult.getErrorList());
    }
}
