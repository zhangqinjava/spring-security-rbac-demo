package com.example.securitydemo.bean.vo.system;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class SysLogVo {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 模块
     */
    private String module;
    /**
     * 操作类型
     */
    private String type;
    /**
     * 操作描述
     */
    private String message;
    /**
     * 请求参数
     */
    private Object reqParam;
    /**
     * 响应参数
     */
    private Object resParam;
    /**
     * 执行时间
     */
    private Long takeUpTime;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 请求地址
     */
    private String uri;
    /**
     * 请求ip
     */
    private String ip;
    /**
     * 请求版本
     */
    private String version;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
