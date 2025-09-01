package com.example.securitydemo.controller.sku;

import com.alibaba.excel.EasyExcel;
import com.example.securitydemo.bean.vo.sku.Category;
import com.example.securitydemo.bean.vo.sku.CategoryExcel;
import com.example.securitydemo.common.result.R;
import com.example.securitydemo.service.sku.SkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sku")
public class SkuController {
    @Autowired
    private SkuService skuService;
    @GetMapping("/query")
    public R query(Category category){
        return R.ok(skuService.query(category));
    }
    @GetMapping("/delete")
    public R delete(@RequestParam Integer id){
        return R.ok(skuService.delete(id));
    }
    @GetMapping("/save")
    public R save(Category category){
        return R.ok(skuService.add(category));
    }
    @GetMapping("/update")
    public R update(Category category){
        return R.ok(skuService.update(category));
    }
    @GetMapping("/list")
    public R list(Category category){
        return R.ok(skuService.list());
    }
    @GetMapping("/export-tree")
    public void exportTree(HttpServletResponse response, Category category) throws Exception {
        log.info("export init:{}", category);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("商品分类", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        List<Category> list = skuService.query(category);
        log.info("export start:{}", list);
        List<CategoryExcel> categoryExcelList = new ArrayList<>();
        this.flattenTree(list, categoryExcelList ,list.get(0).getParentId());
        // 导出
        EasyExcel.write(response.getOutputStream(), CategoryExcel.class)
                .sheet("分类")
                .doWrite(categoryExcelList);

    }
    public void flattenTree(List<Category> list,List<CategoryExcel> flat,int level) {
        for (Category node : list) {
            flat.add(new CategoryExcel(node.getId(),node.getName(), node.getParentId(),  level));
            if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                flattenTree(node.getChildren(), flat, level + 1);
            }
        }
    }
}
