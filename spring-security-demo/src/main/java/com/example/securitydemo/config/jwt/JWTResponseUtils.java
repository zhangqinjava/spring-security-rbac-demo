package com.example.securitydemo.config.jwt;

import com.alibaba.fastjson.JSONObject;
import com.example.securitydemo.bean.vo.system.SysUser;
import com.example.securitydemo.bean.vo.system.SysUserDetails;
import com.example.securitydemo.bean.vo.system.SysUserVo;
import com.example.securitydemo.common.Constants;
import com.example.securitydemo.common.result.CodeEnum;
import com.example.securitydemo.common.result.R;
import com.example.securitydemo.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

@Slf4j
public class JWTResponseUtils {
    /**
     * 成功验证登录信息后，生成token并返回
     * @param request
     * @param response
     * @param chain
     * @param authResult
     */
    public static void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        SysUserDetails user = (SysUserDetails) authResult.getPrincipal();
        String role = "";
        //默认未开启
        String elementLoginStatus = Constants.STATE_INVALID;
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            role = authority.getAuthority();
        }
        //有权限才会去查要素开关
//        if(Objects.equals(Constants.NOT_DEL_FALG, user.getType())){
//            try{
//                RedisKeyUtil redisKeyUtil = SpringBeanUtils.getBean(RedisKeyUtil.class);
//                elementLoginStatus = redisKeyUtil.get(RedisEnums.REDIS_LOGIN_ELEMENT.getCode());
//                log.info("查看登录要素入参: key = "+RedisEnums.REDIS_LOGIN_ELEMENT.getCode()+"; value = "+elementLoginStatus);
//                if(!StringUtils.hasText(elementLoginStatus)){
//                    elementLoginStatus = Constants.STATE_INVALID;
//                }
//            }catch (Exception exception){
//                log.error("查看登录要素出现异常,入参: key = "+RedisEnums.REDIS_LOGIN_ELEMENT.getCode()+"; exception: ",exception);
//            }
//        }
        //判断当前的登录是否超过三个月
        boolean flag=false;
        if (user.getUpdateTime() != null && !"".equals(user.getUpdateTime())) {
            LocalDateTime currentTime = LocalDateTime.now().minus(3, ChronoUnit.MONTHS);
            if (user.getUpdateTime().isBefore(currentTime) || user.getUpdateTime().isEqual(currentTime)) {
                log.info("用户的登录时间超过三个月:{}", user);
                flag = true;
            }
        }
        log.info("登录返回的数据为:{}",user);
        // 创建token
        String token = JwtUtil.createTonken(
                SysUser.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .type(user.getType())
                        .phone(user.getPhone())
                        .loginTabs(user.getLoginTabs())
                        .role(user.getRole())
                        .build(), false);
        R<Object> result = R.ok(
                SysUserVo.builder()
                        .username(StringUtils.hasText(user.getUsername()) ? user.getUsername() : user.getPhone())
                        .nickname(user.getNickname())
                        .token(token)
                        .id(user.getId())
                        .type(user.getType())
                        .isMoreFlag(flag)
                        .firstFlag(false)
                        .loginTime(LocalDateTime.now()).build());
        // 创建成功的token, 请求的格式应该是 `Bearer token`
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSONObject.toJSONString(result));
    }
    /**
     * 验证失败调用的方法
     **/
    public static void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        R<Object> result =  null;
        if (failed instanceof UsernameNotFoundException) {
            String message = failed.getMessage() != null ? failed.getMessage() : CodeEnum.AUTH_NONEXISTENT.getMessage();
            result = R.fail(CodeEnum.AUTH_NONEXISTENT.getCode(), message);
        } else if (failed instanceof BadCredentialsException) {
            String message = failed.getMessage() != null ? failed.getMessage() : CodeEnum.AUTH_NO_TOKEN.getMessage();
            result = R.fail(CodeEnum.AUTH_NO_TOKEN.getCode(), message);
        } else if (failed instanceof InternalAuthenticationServiceException) {
            String message = failed.getMessage() != null ? failed.getMessage() : CodeEnum.ERROR.getMessage();
            result = R.fail(CodeEnum.AUTH_NO_ACCESS.getCode(), message);
        }
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSONObject.toJSONString(result));
    }
}
