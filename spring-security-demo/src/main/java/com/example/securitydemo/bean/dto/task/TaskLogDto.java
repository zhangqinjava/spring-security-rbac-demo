package com.example.securitydemo.bean.dto.task;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskLogDto {
    private Integer id;
    private String taskId;
    private String serverName;
    private Integer status;
    private LocalDateTime executionTime;
    private LocalDateTime createTime;
    private String remarks;
    private Integer pageNum;
    private Integer pageSize;
}
