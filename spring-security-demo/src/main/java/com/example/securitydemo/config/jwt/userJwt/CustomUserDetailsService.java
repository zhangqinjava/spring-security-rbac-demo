package com.example.securitydemo.config.jwt.userJwt;

import com.example.securitydemo.bean.dto.system.SysUserDto;
import com.example.securitydemo.bean.vo.system.SysUserDetails;
import com.example.securitydemo.common.BusiException;
import com.example.securitydemo.service.system.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private SysUserService userService;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            SysUserDto sysUserDto=new SysUserDto();
            sysUserDto.setUsername(username);
            return new SysUserDetails(userService.login(sysUserDto));
        } catch (BusiException e) {
            throw new BusiException(e.getMessage());
        }
    }

}
