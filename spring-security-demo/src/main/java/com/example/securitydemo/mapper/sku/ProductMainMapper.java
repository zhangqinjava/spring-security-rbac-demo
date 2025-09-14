package com.example.securitydemo.mapper.sku;

import com.example.securitydemo.bean.dto.sku.ProductMainDto;
import com.example.securitydemo.bean.vo.sku.ProductMain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMainMapper {
    List<ProductMain> query(ProductMainDto productMainDto);
    boolean update(ProductMainDto productMainDto);
    boolean insert(ProductMainDto productMainDto);
    boolean delete(Integer id);
}
