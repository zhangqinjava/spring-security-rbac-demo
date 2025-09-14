package com.example.securitydemo.bean.vo.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExcelReadResult <T>{
    private List<T> successList = new ArrayList<>(); // 成功数据
    private List<String> errorList = new ArrayList<>(); // 错误信息

}
