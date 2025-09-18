package com.example.securitydemo.mapper.sku;

import com.example.securitydemo.bean.dto.sku.CategoryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<com.example.securitydemo.bean.vo.sku.Category> query(CategoryDto categoryDto);
    boolean add(com.example.securitydemo.bean.vo.sku.Category category);
    boolean update(com.example.securitydemo.bean.vo.sku.Category category);
    boolean delete(Integer id);
}
