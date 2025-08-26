package com.example.securitydemo.common.aop;

import com.example.securitydemo.common.event.LogInfoEvent;
import com.example.securitydemo.common.thread.RequestHolder;
import com.example.securitydemo.util.SecurityUserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

@ConditionalOnProperty(prefix = "log",name = "enable",matchIfMissing = true)
@Aspect
@Configuration
@RequiredArgsConstructor
@Slf4j
public class LogAspect {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Pointcut("execution(* com.example.securitydemo.controller..*.*(..))")
    public void pointCut() {

    }
    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        RequestHolder.set(System.currentTimeMillis());
    }
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
       return joinPoint.proceed();
    }

    /**
     * 方法正常返回
     * @param joinPoint
     */
    @AfterReturning(value = "pointCut()",returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        //成功返回后，做日志记录
        try {
            log.info("event start");
            MethodSignature signature = (MethodSignature)joinPoint.getSignature();
            Method method = signature.getMethod();
            Log annotation = method.getAnnotation(Log.class);
            if(Objects.isNull(annotation)){
                return;
            }
            Authentication authentication = SecurityUserUtils.getAuthentication();
            if(authentication==null){
                log.error("用户没有登陆,不进行日志发布");
                return;
            }
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if(requestAttributes==null){
                log.error("无请求链路，无法将登陆的日志存储");
            }
            HttpServletRequest request = requestAttributes.getRequest();

            log.info("发布event事件");
            applicationEventPublisher.publishEvent(new LogInfoEvent(this , SecurityUserUtils.getUser(), (Long) RequestHolder.get(), joinPoint, request,result));
            log.info("发布event事件成功");

        }catch (Exception e){
            throw  e;
        }finally {
            RequestHolder.remove();
        }
    }
    @AfterThrowing("pointCut()")
    public void afterThrowing(JoinPoint joinPoint) {
        RequestHolder.remove();
    }

}
