package com.example.securitydemo.bean.vo.task;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Data
@Builder
public class TaskLog {
    private Integer id;
    private String taskId;
    private String serverName;
    private Integer status;
    private LocalDateTime executionTime;
    private LocalDateTime createTime;
    private String remarks;
}
