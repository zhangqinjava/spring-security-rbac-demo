package com.example.securitydemo.config.jwt.mobileJwt;

import com.example.securitydemo.bean.dto.system.SysUserDto;
import com.example.securitydemo.bean.vo.system.SysUserDetails;
import com.example.securitydemo.common.BusiException;
import com.example.securitydemo.service.impl.system.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MobileUserDetailService implements UserDetailsService {
    @Autowired
    private SysUserServiceImpl userService;
    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        try {
            SysUserDto sysUserDto = new SysUserDto();
            sysUserDto.setPhone(phone);
            return new SysUserDetails(userService.login(sysUserDto));
        }catch (Exception e){
            throw new BusiException(e.getMessage());
        }
    }
}
