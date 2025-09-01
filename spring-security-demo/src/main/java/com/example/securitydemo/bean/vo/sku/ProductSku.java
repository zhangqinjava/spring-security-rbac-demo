package com.example.securitydemo.bean.vo.sku;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品sku表
 */
@Data
@Builder
public class ProductSku {
    /**
     * skuIdid
     */
    private Integer id;
    /**
     * 产品id
     */
    private Integer productId;
    /**
     * 商品属性 红色-m
     */
    private String skuName;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 照片地址
     */
    private String image;
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

}
