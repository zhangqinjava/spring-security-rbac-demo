package com.example.securitydemo.bean.dto.sku;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.bytearray.ByteArrayImageConverter;
import com.example.securitydemo.config.converter.StringToIntegerConverter;
import lombok.Data;

@Data
public class SkuImportDto {
    @ExcelProperty( "分类名称")
    private String skuName;
    @ExcelProperty(value = "商品编号")
    private Integer productId;
    @ExcelProperty("价格(元)")
    private String price;
    @ExcelProperty(value = "库存数量")
    private Integer stock;
    @ExcelProperty(value = "图片")
    private byte[] image;

}
