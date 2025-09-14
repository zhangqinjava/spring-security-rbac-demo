package com.example.securitydemo.bean.dto.sku;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    /**
     * 分类id
     */
    private Integer id;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 父分类id
     */
    private Integer parentId;
    /**
     * 子分类
     */
    private int pageNum=1;
    private int pageSize=20;

}
