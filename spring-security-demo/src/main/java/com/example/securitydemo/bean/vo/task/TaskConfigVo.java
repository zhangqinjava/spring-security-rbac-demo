package com.example.securitydemo.bean.vo.task;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class TaskConfigVo {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 任务编码
     */
    private String code;
    /**
     * 任务名称
     */
    private String name;
    /**
     * 任务id
     */
    private String taskId;
    /**
     * cron表达式
     */
    private String expression;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 是否并发执行
     */
    private Integer concurrent;
    /**
     * 重试次数
     */
    private Integer retryCount;
    /**
     * 失败重试间隔
     */
    private Integer retryInterval;
    /**
     * 最后执行时间
     */
    private LocalDateTime lastRunTime;
    /**
     * 下次执行时间
     */
    private LocalDateTime nextRunTime;
    /**
     * 创建人
     */
    private String creater;
    /**
     * 更新人
     */
    private String updater;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 备注
     */
    private String remarks;
}
