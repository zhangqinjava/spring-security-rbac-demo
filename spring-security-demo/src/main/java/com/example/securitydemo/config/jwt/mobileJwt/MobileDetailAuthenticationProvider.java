package com.example.securitydemo.config.jwt.mobileJwt;

import com.alibaba.fastjson.JSON;
import com.example.securitydemo.common.BusiException;
import com.example.securitydemo.common.result.CodeEnum;
import com.example.securitydemo.config.jwt.userJwt.UserDetailAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@DependsOn("mobileUserDetailService")
public class MobileDetailAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    @Autowired
    private  MobileUserDetailService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            MobileCodeAuthenticationToken mobileToken = (MobileCodeAuthenticationToken) authentication;
            log.info("=============开始校验手机号登录校验===========:{}", JSON.toJSONString(mobileToken));
            if (StringUtils.isEmpty(mobileToken.getPhone())) {
                throw new BusiException(String.valueOf(CodeEnum.ERROR.getCode()), "手机号不存在");
            }
            if (StringUtils.isEmpty(mobileToken.getPhoneCode())) {
                throw new BusiException(String.valueOf(CodeEnum.ERROR.getCode()), "验证码不存在");
            }
            UserDetails userDetails = userDetailsService.loadUserByUsername(mobileToken.getPhone());
            if (userDetails == null) {
                throw new BusiException("登录的手机号码不存在");
            }
            //考虑从redis中获取验证码，待实现,我们在redis中设置了过期的时间，如果没有获取到，证明验证码已经过期无效
            String code = "";
            if (code.equals(mobileToken.getPhoneCode())) {
                throw new BusiException("验证码不正确，请输入正确的验证码");
            }
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            log.info("=================手机号验证结束=============:{}", JSON.toJSONString(userDetails));
            return usernamePasswordAuthenticationToken;
        }catch (Exception e){
//            if(e instanceof BusiException){
//                throw e;
//            }
            throw new BusiException(String.valueOf(CodeEnum.ERROR.getCode()), e.getMessage());
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
        return (MobileCodeAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
