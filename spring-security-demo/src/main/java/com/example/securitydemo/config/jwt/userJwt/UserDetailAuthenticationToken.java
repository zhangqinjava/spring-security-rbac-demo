package com.example.securitydemo.config.jwt.userJwt;

import com.example.securitydemo.bean.vo.system.SysUser;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserDetailAuthenticationToken extends AbstractAuthenticationToken {
    private String username;
    private String password;
    private String phone;
    private String phoneCode;
    public UserDetailAuthenticationToken(Collection<? extends GrantedAuthority> authorities, SysUser user) {
        super(authorities);
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.phone = user.getPhone();
        this.phoneCode = user.getPhoneCode();
    }
    public UserDetailAuthenticationToken(SysUser sysUser) {
        super(null);
        this.phone = sysUser.getPhone();
        this.phoneCode = sysUser.getPhoneCode();
        this.username = sysUser.getUsername();
        this.password = sysUser.getPassword();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    @Override
    public Object getCredentials() {
        return username;
    }

    @Override
    public Object getPrincipal() {
        return password;
    }
}
