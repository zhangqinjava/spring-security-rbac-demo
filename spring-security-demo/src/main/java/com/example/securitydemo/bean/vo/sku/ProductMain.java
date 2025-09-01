package com.example.securitydemo.bean.vo.sku;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 商品主表
 */
@Data
@Builder
public class ProductMain {
    /**
     * 商品id
     */
    private Integer id;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 分类ID
     */
    private Integer categoryId;
    /**
     * 商品描述
     */
    private String description;
    /**
     * 状态 1-上架 0-下架
     */
    private Integer status;
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

}
