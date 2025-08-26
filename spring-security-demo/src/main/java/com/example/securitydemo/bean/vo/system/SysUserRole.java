package com.example.securitydemo.bean.vo.system;

import lombok.Data;

@Data
public class SysUserRole {
    private Integer id;
    /**
     * 用户名称
     */
    private String username ;
    /**
     * 角色Id
     */
    private Integer roleId;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
}
