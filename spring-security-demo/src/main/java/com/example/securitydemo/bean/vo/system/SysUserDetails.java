package com.example.securitydemo.bean.vo.system;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
@Data
public class SysUserDetails implements UserDetails {
    private int id;
    private String username;
    private String password;
    private String nickname;
    private Integer flag;
    private Integer type;
    private String phone;
    private String role;
    private String loginTabs;
    private boolean firstFlag;
    private boolean timeExpired;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public SysUserDetails(SysUser user) {
        this.id=user.getId();
        this.username=user.getUsername();
        this.password=user.getPassword();
        this.nickname=user.getNickname();
        this.flag=user.getFlag();
        this.type=user.getType();
        this.role=user.getRole();
        this.createTime=user.getCreateTime();
        this.updateTime=user.getUpdateTime();
        this.phone=user.getPhone();
        this.loginTabs=user.getLoginTabs();
        this.firstFlag=user.getFirstFlag()=="0"?true:false;
        if(this.updateTime!=null){
            LocalDateTime threeMonthsLater = LocalDateTime.now().minusMonths(3);
            if(this.updateTime.isAfter(threeMonthsLater)){
                this.timeExpired=true;
            }
        }else {
            this.timeExpired = false;
        };

    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAccountNonExpired() {
        return false;
    }

    public boolean isAccountNonLocked() {
        return false;
    }

    public boolean isCredentialsNonExpired() {
        return false;
    }

    public boolean isEnabled() {
        return false;
    }
}
