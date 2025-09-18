package com.example.securitydemo.mapper.sku;

import com.example.securitydemo.bean.dto.sku.SkuDto;
import com.example.securitydemo.bean.vo.sku.Sku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SkuMapper {
    List<Sku> query(SkuDto skuDto);
    boolean update(SkuDto skuDto);
    boolean delete(Integer id);
    boolean insert(SkuDto skuDto);
}
