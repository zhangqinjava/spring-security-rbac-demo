package com.example.securitydemo.bean.dto.sku;

import com.alibaba.excel.annotation.ExcelIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductMainDto {
    @ExcelIgnore
    private Integer id;
    private String name;
    private Integer categoryId;
    private String description;
    private Integer status;
    @ExcelIgnore
    private int pageNum=1;
    @ExcelIgnore
    private int pageSize=20;
}
