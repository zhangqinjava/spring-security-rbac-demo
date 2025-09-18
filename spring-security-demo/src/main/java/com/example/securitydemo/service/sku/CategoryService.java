package com.example.securitydemo.service.sku;

import com.example.securitydemo.bean.dto.sku.CategoryDto;
import com.example.securitydemo.bean.vo.sku.Category;
import com.example.securitydemo.bean.vo.sku.CategoryExcel;

import java.util.List;

public interface CategoryService {
    List<Category> query(CategoryDto categoryDto);
    boolean add(Category category);
    boolean update(Category category);
    boolean delete(Integer id);
    List<Category> list();
    List<CategoryExcel> listExcel(Category category);
}
