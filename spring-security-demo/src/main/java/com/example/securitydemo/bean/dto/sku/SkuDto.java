package com.example.securitydemo.bean.dto.sku;

import lombok.Builder;
import lombok.Data;
import org.apache.poi.hpsf.Decimal;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
public class SkuDto {
    /**
     * skuId
     */
    private Integer id;
    /**
     * 商品id
     */
    private Integer productId;
    /**
     * sku名称
     */
    private String skuName;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 库存数量
     */
    private Integer stock;
    /**
     * 图片地址
     */
    private byte[] image;
    /**
     * 创建时间
     */
    private String createdAt;
    /**
     * 创建时间
     */
    private String updatedAt;
}
