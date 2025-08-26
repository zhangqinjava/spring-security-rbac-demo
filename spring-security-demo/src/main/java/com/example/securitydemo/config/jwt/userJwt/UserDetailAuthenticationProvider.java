package com.example.securitydemo.config.jwt.userJwt;

import com.example.securitydemo.bean.vo.system.SysUserDetails;
import com.example.securitydemo.common.BusiException;
import com.example.securitydemo.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

/**
 * 登录账号认证
 */
@Slf4j
@DependsOn("customUserDetailsService")
public class UserDetailAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private  CustomUserDetailsService userDetailsService;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetailAuthenticationToken tokenReq = (UserDetailAuthenticationToken) authentication;
        /**
         * 如果开启手机号认证 我们需要将这段注释打开
         */
        String loginElement = Constants.STATE_INVALID;
        log.info("<==============账号认证处理器处理开始===============>");
        try {
            // 根据账号，获取登录人员信息
            SysUserDetails userDetails = (SysUserDetails) userDetailsService.loadUserByUsername(tokenReq.getUsername());
            log.info("获取到的用户登录的信息:{}", userDetails);
            //储存用户信息必须是SecurityUtils类
            SysUserDetails securityUtils = (SysUserDetails)userDetails;
            if(Constants.STATE_EFFECTIVE.equals(loginElement)){
                if(!StringUtils.hasText(tokenReq.getPhone())){
                    throw new BusiException("手机号不能为空");
                }
                if(!StringUtils.hasText(tokenReq.getPhoneCode())){
                    throw new BusiException("验证码不能为空");
                }
            }
            boolean matches = passwordEncoder.matches(tokenReq.getPassword(), securityUtils.getPassword());
            if(!matches){
                throw new BusiException("密码不正确,请重新填写");
            }
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            log.info("<==============账号认证处理器处理结束===============>");
            return usernamePasswordAuthenticationToken;
        } catch (BusiException e) {
            log.error("账号登录出现可控异常:"+e.getMessage());
            throw new BadCredentialsException(e.getMessage());
        } catch (Exception e) {
            log.error("账号登录出现不可控异常:",e);
            throw new BadCredentialsException("账号登录认证异常");
        }
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        return null;
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return (UserDetailAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
