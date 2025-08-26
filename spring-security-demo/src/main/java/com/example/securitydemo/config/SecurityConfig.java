package com.example.securitydemo.config;

import com.example.securitydemo.config.jwt.*;
import com.example.securitydemo.config.jwt.filter.JWTAuthenticationFilter;
import com.example.securitydemo.config.jwt.filter.JWTAuthorizationFilter;
import com.example.securitydemo.config.jwt.mobileJwt.MobileDetailAuthenticationProvider;
import com.example.securitydemo.config.jwt.userJwt.UserDetailAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public UserDetailAuthenticationProvider userDetailAuthenticationProvider() {
        return new UserDetailAuthenticationProvider();
    }
    @Bean
    public MobileDetailAuthenticationProvider mobileDetailAuthenticationProvider() {
        return new MobileDetailAuthenticationProvider();
    }
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //自定义认证处理器
        //账密认证
        auth.authenticationProvider(userDetailAuthenticationProvider());
        //手机号认证
        auth.authenticationProvider(mobileDetailAuthenticationProvider());
        //邮箱登陆

    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(new JWTAuthenticationEntryPoint())
                .accessDeniedHandler(new JWTAccessDeniedHandler())
                .and()
                .logout().logoutUrl("/logout").logoutSuccessHandler(new JWTLogoutSuccessHandler());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
