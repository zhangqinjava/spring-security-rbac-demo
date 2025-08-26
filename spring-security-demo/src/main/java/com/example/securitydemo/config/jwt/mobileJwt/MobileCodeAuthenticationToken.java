package com.example.securitydemo.config.jwt.mobileJwt;

import lombok.Data;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class MobileCodeAuthenticationToken extends AbstractAuthenticationToken {
    private String phone;
    private String phoneCode;

    public MobileCodeAuthenticationToken(Collection<? extends GrantedAuthority> authorities, String phone, String phoneCode) {
        super(authorities);
        this.phone = phone;
        this.phoneCode = phoneCode;
    }

    public MobileCodeAuthenticationToken(String phone, String phoneCode) {
        super(null);
        this.phone = phone;
        this.phoneCode = phoneCode;
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
        return this.phone;
    }

    @Override
    public Object getPrincipal() {
        return this.phoneCode;
    }
}
