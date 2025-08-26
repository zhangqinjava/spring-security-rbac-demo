package com.example.securitydemo.bean.dto.system;

import lombok.Data;

@Data
public class SysRoleDto {
    private Integer id;
    private String name;
    private Integer type;
    private String startTime;
    private String endTime;
    private Integer status;
    private Integer current;
    private Integer size;
}
