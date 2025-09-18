package com.example.securitydemo.service.sku;

import com.example.securitydemo.bean.dto.sku.SkuDto;
import com.example.securitydemo.bean.vo.sku.Sku;

import java.util.List;

public interface SkuService {
    List<Sku> queryAll(SkuDto skuDto);
    boolean delete(SkuDto skuDto);
    boolean save(SkuDto skuDto);
    boolean update(SkuDto skuDto);
}
