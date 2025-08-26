package com.example.securitydemo.bean.vo.system;

import lombok.*;

import java.time.LocalDateTime;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SysUser  {
    private Integer id;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 微信登录标识
     */
    private String open_id;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机验证码
     */
    private String phoneCode;
    /**
     * 登录类型 0-账户登录 1-手机号登录 2-微信扫码登录
     */
    private String loginTabs;
    /**
     * 用户类型 0-普通用户 1-超级管理员用户
     */
    private Integer type;
    /**
     * 是否可用的标识
     */
    private int flag;
    /**
     * 首次登录的标识
     */
    private String firstFlag;
    /**
     * 角色
     */
    private String role;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 出生日期
     */
    private String birthday;
    /**
     * 地址
     **/
    private String address;
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
