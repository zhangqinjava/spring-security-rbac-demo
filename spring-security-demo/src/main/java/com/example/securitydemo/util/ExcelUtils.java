package com.example.securitydemo.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.example.securitydemo.bean.vo.common.ExcelReadResult;
import com.example.securitydemo.config.converter.StringToIntegerConverter;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class ExcelUtils {
    /**
     * 统一导出方法
     * @param response HttpServletResponse
     * @param fileName 导出的文件名（不带后缀）
     * @param sheetName sheet名称
     * @param data 导出的数据
     * @param clazz 数据对应的实体类（带 @ExcelProperty 注解）
     */
    public static <T> void export(HttpServletResponse response,
                                  String fileName,
                                  String sheetName,
                                  List<T> data,
                                  Class<T> clazz) throws IOException {
        // 设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");

        // 避免文件名中文乱码
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename=" + encodedFileName + ".xlsx");
        WriteCellStyle headStyle = new WriteCellStyle();
        headStyle.setHorizontalAlignment(HorizontalAlignment.CENTER); // 头部居中
        WriteFont headFont = new WriteFont();
        headFont.setBold(true);
        headFont.setFontName("微软雅黑");
        headFont.setFontHeightInPoints((short)10);
        headStyle.setWriteFont(headFont);
        // 设置内容的样式
        WriteCellStyle contentStyle = new WriteCellStyle();
        contentStyle.setHorizontalAlignment(HorizontalAlignment.LEFT); // 内容居中

        HorizontalCellStyleStrategy styleStrategy =
                new HorizontalCellStyleStrategy(headStyle, contentStyle);
        // 写入数据
        EasyExcel.write(response.getOutputStream(), clazz)
                .registerWriteHandler(styleStrategy)
                .sheet(sheetName==null?"sheet1":sheetName)
                .doWrite(data);
    }

    /**
     * 大数据量导出 - 分批分页写入
     *
     * @param response  HttpServletResponse
     * @param fileName  导出文件名（不带后缀）
     * @param clazz     导出实体类（带 @ExcelProperty 注解）
     * @param sheetName 分页名称
     * @param batchSize 每次查询的批大小，比如 5000
     * @param total     数据总量（可先 count）
     * @param fetchFunc 分页查询方法 (pageNo, pageSize) -> List<T>
     * @param <T>       导出数据类型
     */
    public static <T> void exportBig(HttpServletResponse response,
                                     String fileName,
                                     Class<T> clazz,
                                     String sheetName,
                                     int batchSize,
                                     long total,
                                     BiFunction<Integer, Integer, List<T>> fetchFunc) throws IOException {

        // 设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename=" + encodedFileName + ".xlsx");

        // 创建 ExcelWriter
        ExcelWriter excelWriter = null;

        try {
                 excelWriter = EasyExcel.write(response.getOutputStream(), clazz)
                        .autoCloseStream(Boolean.FALSE)
                        .build();
                WriteSheet sheet = EasyExcel.writerSheet(sheetName).build();
                long totalPage = (total + batchSize - 1) / batchSize;
                for (int pageNo = 1; pageNo <= totalPage; pageNo++) {
                    List<T> batch = fetchFunc.apply(pageNo, batchSize);
                    if (batch != null && !batch.isEmpty()) {
                        excelWriter.write(batch, sheet);
                        batch.clear(); // 释放内存
                    }
                }
        }finally {
            if (excelWriter != null) {
                excelWriter.finish();//关闭流写入器
            }
        }
    }
    /**
     * 统一读取 Excel 方法
     * @param file 上传的文件
     * @param clazz 目标对象类型
     * @param validator 校验逻辑（可选）
     * @return ExcelReadResult<T>
     */
    public static <T> ExcelReadResult<T> readExcel(MultipartFile file,
                                                   Class<T> clazz,
                                                   int headRowNumber,
                                                   Consumer<T> validator) {
        ExcelReadResult<T> result = new ExcelReadResult<>();
        try (InputStream inputStream = file.getInputStream()) {
            //开始读取数据
            EasyExcel.read(inputStream, clazz, new PageReadListener<T>(dataList -> {
                        for (int i = 0; i < dataList.size(); i++) {
                            T data = dataList.get(i);
                            try {
                                if (validator != null) {
                                    validator.accept(data); // 调用外部校验逻辑
                                }
                                result.getSuccessList().add(data);
                            } catch (Exception e) {
                                // 计算Excel中的行号：数据行号 = 当前数据索引 + 表头行数 + 1 (因为索引从0开始，表头占headRowNumber行)
                                result.getErrorList().add("数据第" + (i + headRowNumber + 1) + "行错误: " + data + "，原因: " + e.getMessage());
                            }
                        }
                    })).registerConverter(new StringToIntegerConverter())
                    .headRowNumber(headRowNumber) // 设置表头行数
                    .sheet()
                    .doRead();
        } catch (ExcelDataConvertException e) {
            // 转换异常的行号：e.getRowIndex()得到的是从0开始的行索引，所以+1得到Excel行号
            result.getErrorList().add("文件解析失败，第 " + (e.getRowIndex() + 1) + " 行，第 " + (e.getColumnIndex() + 1) + " 列: " + e.getCellData() + " 转换错误");
        } catch (Exception e) {
            result.getErrorList().add("文件解析失败: " + e.getMessage());
        }
        return result;
    }

}
