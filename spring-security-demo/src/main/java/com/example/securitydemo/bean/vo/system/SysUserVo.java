package com.example.securitydemo.bean.vo.system;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class SysUserVo {
    private int id;
    private String username;
    private String nickname;
    private String token;
    private boolean isMoreFlag;
    private Integer type;
    private boolean timeExpired;
    private boolean firstFlag;
    private LocalDateTime loginTime;
}
