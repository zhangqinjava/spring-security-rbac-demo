package com.example.securitydemo.controller.sku;

import com.example.securitydemo.bean.dto.sku.ProductMainDto;
import com.example.securitydemo.bean.vo.common.ExcelReadResult;
import com.example.securitydemo.common.BusiException;
import com.example.securitydemo.common.result.R;
import com.example.securitydemo.service.sku.ProductMainService;
import com.example.securitydemo.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductMainService productMainService;
    @GetMapping("/query")
    public R query(ProductMainDto productMainDto)  {
        return R.ok(productMainService.query(productMainDto));
    }
    @GetMapping("/save")
    public R save(ProductMainDto productMainDto) {
        return R.ok(productMainService.add(productMainDto));
    }
    @GetMapping("/update")
    public R update(ProductMainDto productMainDto) {
        return R.ok(productMainService.update(productMainDto));
    }
    @GetMapping("/delete")
    public R delete(@RequestParam(required = true) Integer id) {
        return R.ok(productMainService.delete(id));
    }
    @PostMapping("/batchInsert")
    public R batchInsert(MultipartFile file) {
        log.info("批量导入商品详情:{}", file.getOriginalFilename() );
        ExcelReadResult<ProductMainDto> result = ExcelUtils.readExcel(file, ProductMainDto.class,1, data -> {
            if (data.getName() == null || "".equals(data.getName())) {
                throw new BusiException("商品名称不能为空");
            }
            if (data.getCategoryId() == null || "".equals(data.getCategoryId())) {
                throw new BusiException("商品的父类id不能为空");
            }
        },null);
        if (!CollectionUtils.isEmpty(result.getErrorList())){
            return R.fail("当前的数据有问题",result.getErrorList());
        }
        if (CollectionUtils.isEmpty(result.getSuccessList())){
            return R.fail("导入的数据为空");
        }

        return R.ok(productMainService.batchAdd(result.getSuccessList()));
    }
}
