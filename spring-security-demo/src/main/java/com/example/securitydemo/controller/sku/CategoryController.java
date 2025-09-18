package com.example.securitydemo.controller.sku;

import com.example.securitydemo.bean.dto.sku.CategoryDto;
import com.example.securitydemo.bean.vo.sku.Category;
import com.example.securitydemo.bean.vo.sku.CategoryExcel;
import com.example.securitydemo.common.result.Page;
import com.example.securitydemo.common.result.R;
import com.example.securitydemo.service.sku.CategoryService;
import com.example.securitydemo.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/query")
    public Page query(CategoryDto categoryDto){
        return Page.ok(categoryService.query(categoryDto),1,20);
    }
    @GetMapping("/delete")
    public R delete(@RequestParam Integer id){
        return R.ok(categoryService.delete(id));
    }
    @GetMapping("/save")
    public R save(Category category){
        return R.ok(categoryService.add(category));
    }
    @GetMapping("/update")
    public R update(Category category){
        return R.ok(categoryService.update(category));
    }
    @GetMapping("/list")
    public R list(Category category){
        return R.ok(categoryService.list());
    }
    @GetMapping("/export-tree")
    public void exportTree(HttpServletResponse response, Category category) throws Exception {
        log.info("export init:{}", category);
        List<CategoryExcel> categoryExcels = categoryService.listExcel(category);
        log.info("export start:{}", categoryExcels);
        ExcelUtils.export(response, "商品分类", "分类", categoryExcels, CategoryExcel.class);

    }

}
