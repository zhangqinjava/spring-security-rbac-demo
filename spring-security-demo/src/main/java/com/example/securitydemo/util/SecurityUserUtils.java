package com.example.securitydemo.util;

import com.example.securitydemo.bean.vo.system.SysUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUserUtils {
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static SysUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (SysUser) authentication.getDetails();
//        return null;
    }

    public static void logout() {
        SecurityContextHolder.clearContext();
    }
}
