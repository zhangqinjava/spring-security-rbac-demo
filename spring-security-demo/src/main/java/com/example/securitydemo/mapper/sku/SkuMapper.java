package com.example.securitydemo.mapper.sku;

import com.example.securitydemo.bean.dto.sku.CategoryDto;
import com.example.securitydemo.bean.vo.sku.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SkuMapper {
    List<Category> query(CategoryDto categoryDto);
    boolean add(Category category);
    boolean update(Category category);
    boolean delete(Integer id);
}
