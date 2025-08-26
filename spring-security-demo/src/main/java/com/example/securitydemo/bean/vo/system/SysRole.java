package com.example.securitydemo.bean.vo.system;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class SysRole {
    /**
     * id
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
