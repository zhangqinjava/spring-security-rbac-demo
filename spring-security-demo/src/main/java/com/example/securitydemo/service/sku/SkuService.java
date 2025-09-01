package com.example.securitydemo.service.sku;

import com.example.securitydemo.bean.vo.sku.Category;

import java.util.List;
import java.util.Map;

public interface SkuService {
    List<Category> query(Category category);
    boolean add(Category category);
    boolean update(Category category);
    boolean delete(Integer id);
    List<Category> list();
}
