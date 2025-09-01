package com.example.securitydemo.bean.vo.sku;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryExcel {
    @ExcelProperty("分类编码")
    private Integer id;
    @ExcelProperty("分类名称")
    private String name;
    @ExcelProperty("父类编码")
    private Integer parentId;
    @ExcelProperty("层级")
    private Integer level;
}
