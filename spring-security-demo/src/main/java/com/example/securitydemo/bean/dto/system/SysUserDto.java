package com.example.securitydemo.bean.dto.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserDto {
    private Integer id;
    private String username;
    private String nickname;
    private String password;
    private String rePassword;
    private Integer type;
    private Integer gender;
    private String birthday;
    private String address;
    private String orgId;
    private String login;
    private String phone;
    private String email;
    private String remarks;
    private String firstFlag;
    private String openId;
    private Integer page=1;
    private Integer size=10;
    private String createTime;
    private String updateTime;



}
