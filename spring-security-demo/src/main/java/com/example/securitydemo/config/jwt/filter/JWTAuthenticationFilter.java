package com.example.securitydemo.config.jwt.filter;

import com.example.securitydemo.bean.vo.system.LoginEnum;
import com.example.securitydemo.bean.vo.system.SysUser;
import com.example.securitydemo.common.BusiException;
import com.example.securitydemo.config.jwt.JWTResponseUtils;
import com.example.securitydemo.config.jwt.mobileJwt.MobileCodeAuthenticationToken;
import com.example.securitydemo.config.jwt.userJwt.UserDetailAuthenticationToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录拦截器
 */
@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("=====================登录认证开始==========================");
        SysUser user;
        Authentication authenticate;
        try {
            user = new ObjectMapper().readValue(request.getInputStream(), SysUser.class);
            log.info("获取到的username信息为:{}" , user);
            if (user == null) {
                throw new BusiException("登录信息不能为空");
            }
            if (!StringUtils.hasText(user.getLoginTabs())) {
                throw new BusiException("登录类型不能为空");
            }
            LoginEnum value = LoginEnum.getValue(user.getLoginTabs());
            if (value == null) {
                throw new BusiException("登录类型未匹配");
            }
            log.info("获取到的value值:{}",value);
            //登录认证不需要抽离 只依赖Security 鉴权 目前只有手机号和账密登录后续自行叠加
            switch (value){
                case ACC:
                    authenticate = authenticationManager.authenticate(new UserDetailAuthenticationToken(user));
                    break;
                case Phone:
                    authenticate = authenticationManager.authenticate(new MobileCodeAuthenticationToken(user.getPhone(), user.getPhoneCode()));
                    break;
                case WEIXIN:
                    //此处暂未添加
                default:
                    throw new BusiException("登录类型错误");
            }
            return authenticate;
        } catch (Exception e) {
            log.error("登录认证失败:", e);
            throw new BadCredentialsException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("=======================登录认证成功===========================");
        JWTResponseUtils.successfulAuthentication(request, response,chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("=====================登录认证失败==============================");
        JWTResponseUtils.unsuccessfulAuthentication(request, response, failed);
    }
}
