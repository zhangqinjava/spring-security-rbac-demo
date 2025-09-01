package com.example.securitydemo.bean.vo.sku;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 购物车
 */
@Data
@Builder
public class Cart {
    /**
     * 购物车id
     */
    private Integer id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 商品skuId
     */
    private String skuId;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 是否选中
     */
    private Integer checked;
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
