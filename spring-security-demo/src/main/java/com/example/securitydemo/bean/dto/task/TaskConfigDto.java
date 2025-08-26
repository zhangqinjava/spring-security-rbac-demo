package com.example.securitydemo.bean.dto.task;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class TaskConfigDto {
    private Integer id;
    private String code;
    private String name;
    private String expression;
    private String taskId;
    private String taskName;
    private String nextRunTime;
    private String retryCount;
    private String retryInterval;
    private Integer status;
    private Integer concurrent;
    private String lastRunTime;
    private String creater;
    private String updater;
    private String remarks;
    private Integer pageSize;
    private Integer pageNum;

}
