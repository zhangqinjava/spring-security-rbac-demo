package com.example.securitydemo.controller.system;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.example.securitydemo.bean.dto.system.TemplateRequest;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Slf4j
@RestController
@RequestMapping("/download")
public class ExcelTemplateController {
    @PostMapping("/excel")
    public void excelTemplate(HttpServletResponse response, @RequestBody TemplateRequest templateRequest) throws Exception {
        log.info("excelTemplate:{}", templateRequest);
       // 设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(templateRequest.getFileName(), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        // 构建动态表头
        List<List<String>> head = new ArrayList<>();
        for (String h : templateRequest.getHeaders()) {
            head.add(Stream.of(h).collect(Collectors.toList())); // EasyExcel 的 head 需要 List<List<String>>
        }

        // 空数据
        List<List<String>> data = new ArrayList<>();

        // 写出 Excel
        EasyExcel.write(response.getOutputStream())
                .head(head).excelType(ExcelTypeEnum.XLSX)
                .sheet("导入模板")
                .doWrite(data);
    }
}
