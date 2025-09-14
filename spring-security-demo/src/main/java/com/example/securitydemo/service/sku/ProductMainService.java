package com.example.securitydemo.service.sku;

import com.example.securitydemo.bean.dto.sku.ProductMainDto;
import com.example.securitydemo.bean.vo.sku.ProductMain;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductMainService {
    PageInfo<ProductMain> query(ProductMainDto productMainDto);
    boolean add(ProductMainDto productMainDto);
    boolean batchAdd(List<ProductMainDto> productMainDto);
    boolean update(ProductMainDto productMainDto);
    boolean delete(Integer id);
}
