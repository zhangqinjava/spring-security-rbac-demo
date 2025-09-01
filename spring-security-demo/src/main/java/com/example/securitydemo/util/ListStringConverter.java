package com.example.securitydemo.util;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.example.securitydemo.bean.vo.sku.Category;

import java.util.List;

public class ListStringConverter implements Converter<List<Category>> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return List.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public WriteCellData<?> convertToExcelData(List<Category> value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData<>(String.join((CharSequence) ",", (CharSequence) value));
    }
}
