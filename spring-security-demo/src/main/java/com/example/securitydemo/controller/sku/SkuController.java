package com.example.securitydemo.controller.sku;

import com.example.securitydemo.bean.dto.sku.SkuDto;
import com.example.securitydemo.bean.vo.sku.Sku;
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
import java.util.List;
import java.util.Objects;

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
                 @RequestParam("files") List<MultipartFile> files) throws Exception {
        if(Objects.nonNull(files)){
            skuDto.setImage(files.get(0).getBytes());
        }
        return R.ok(skuService.save(skuDto));
    }
    @GetMapping("/update")
    public R update(SkuDto skuDto) {
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
        List<Sku> skus = skuService.queryAll(SkuDto.builder().id(id).build());
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
        List<Sku> skus = skuService.queryAll(skuDto);
        ExcelUtils.export(response,"商品细分","第一页",skus,Sku.class);
    }
}
