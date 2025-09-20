package com.example.securitydemo.bean.vo.sku;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.bytearray.ByteArrayImageConverter;
import com.example.securitydemo.config.converter.StringToIntegerConverter;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Sku {
    /**
     * skuId
     */
    @ExcelProperty(value = "分类编号",order = 0)
    private Integer id;
    /**
     * sku名称
     */
    @ExcelProperty(value = "分类名称",order = 1)
    private String skuName;
    /**
     * 商品id
     */
    @ExcelProperty(value = "商品编号",order = 2,converter = StringToIntegerConverter.class)
    private Integer productId;
    /**
     * 价格
     */
    @ExcelProperty(value = "价格(元)",order = 3)
    private BigDecimal price;
    /**
     * 库存数量
     */
    @ExcelProperty(value = "上架数量",order = 4)
    private Integer stock;
    /**
     * 图片地址
     */
    @ExcelProperty(value = "图片" ,order = 5,converter = ByteArrayImageConverter.class)
    private byte[] image;
    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间",order = 6)
    private String createdAt;
    /**
     * 更新时间
     */
    @ExcelProperty(value = "最后更新时间",order = 7)
    private String updatedAt;
}
