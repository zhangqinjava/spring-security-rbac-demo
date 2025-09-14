package com.example.securitydemo.bean.vo.sku;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品分类表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
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
     * 创建时间
     */
    private LocalDateTime createdAt;
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
    /**
     * 子分类
     */
    private List<Category> children;
}
