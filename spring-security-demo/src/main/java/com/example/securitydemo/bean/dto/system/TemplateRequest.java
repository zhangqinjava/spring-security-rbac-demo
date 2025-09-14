package com.example.securitydemo.bean.dto.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateRequest {
    private String fileName;
    private List<String> headers;
}
