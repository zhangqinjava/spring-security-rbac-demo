package com.example.securitydemo.bean.dto.system;

import lombok.Data;

@Data
public class SysLogDto {
    private String username;
    private String startTime;
    private String endTime;
    private Integer total;
    private Integer size;
    private Integer current;
}
