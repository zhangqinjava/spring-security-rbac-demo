package com.example.securitydemo.common.event;

import com.example.securitydemo.bean.vo.system.SysUser;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.JoinPoint;
import org.springframework.context.ApplicationEvent;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;

@Setter
@Getter
public class LogInfoEvent extends ApplicationEvent {
    /**
     * 登录用户信息
     */
    private SysUser sysUser;
    /**
     * 返回值
     */
    private Object result;
    /**
     * 织入点
     */
    private JoinPoint joinPoint;
    /**
     * 请求
     */
    private HttpServletRequest request;
    /**
     * 请求开始时间
     */
    private Long requestStartTime;
    /**
     * 异步操作
     */
    private AsyncContext asyncContext;

    public LogInfoEvent(Object source, SysUser sysUser,Long requestStartTime, JoinPoint joinPoint, HttpServletRequest RequestAttributes,Object result) {
        super(source);
        this.sysUser = sysUser;
        this.requestStartTime = requestStartTime;
        this.joinPoint = joinPoint;
        this.request = (HttpServletRequest) RequestAttributes;
        this.asyncContext=request.startAsync();
        this.result = result;
    }
}
