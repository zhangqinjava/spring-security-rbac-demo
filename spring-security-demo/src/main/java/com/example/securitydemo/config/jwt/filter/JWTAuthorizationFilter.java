package com.example.securitydemo.config.jwt.filter;

import com.alibaba.fastjson.JSONObject;
import com.example.securitydemo.bean.vo.system.SysUser;
import com.example.securitydemo.common.BusiException;
import com.example.securitydemo.common.result.CodeEnum;
import com.example.securitydemo.common.result.R;
import com.example.securitydemo.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * 用户鉴权拦截器
 */
@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.info("<==============用户鉴权开始===============>");
        String token = request.getHeader(JwtUtil.TOKEN_HEADER);
        log.info("token:{}",token);
        if (token == null || !token.startsWith(JwtUtil.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            Authentication authentication = getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            onSuccessfulAuthentication(request, response, authentication);
            chain.doFilter(request, response);
        } catch (Exception e) {
            if(e instanceof BusiException){
                log.error("登录校验出现可控异常: "+ e.getMessage());
            }else{
                log.error("登录校验出现未知异常: ",e);
            }
            onUnsuccessfulAuthentication(request, response, new AccountExpiredException(CodeEnum.AUTH_NO_TOKEN.getMessage()));
        }
    }

    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
        log.info("======================用户鉴权成功====================");
//        super.onSuccessfulAuthentication(request, response, authResult);
    }

    @Override
    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        log.info("======================用户鉴权失败====================");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSONObject.toJSONString(R.fail(HttpServletResponse.SC_UNAUTHORIZED, failed.getMessage(),null)));
    }

    /**
     * 从token中获取用户信息并新建一个token
     */
    private AbstractAuthenticationToken getAuthentication(String tokenHeader) {
        log.info("校验获取到的token信息:{}",tokenHeader);
        String token = tokenHeader.replace(JwtUtil.TOKEN_PREFIX, "");
        boolean expiration = JwtUtil.isExpiration(token);
        if (expiration) {
            throw new BusiException(CodeEnum.AUTH_NO_TOKEN.getMessage());
        }
        String username = JwtUtil.getUsername(token);
        String role = JwtUtil.getUserRole(token);
        SysUser user = JwtUtil.getUser(token);
        if(user == null ){
            throw new BusiException(CodeEnum.AUTH_NO_USER.getMessage());
        }
//        if(!StringUtils.hasText(user.getLoginTabs())){
//            throw new BusiException(CodeEnum.AUTH_NO_USER_LOGIN.getMessage());
//        }
        if (username == null) {
            throw new BusiException(CodeEnum.AUTH_NO_USER.getMessage());
        }
        AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, Collections.singleton(new SimpleGrantedAuthority(role)));
        authenticationToken.setDetails(user);
        return authenticationToken;
    }
}
