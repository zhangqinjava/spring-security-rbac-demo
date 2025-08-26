package com.example.securitydemo.common.event;

import com.example.securitydemo.bean.vo.system.SysLogVo;
import com.example.securitydemo.bean.vo.system.SysUser;
import com.example.securitydemo.common.aop.Log;
import com.example.securitydemo.service.system.SysLogService;
import com.example.securitydemo.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

@Slf4j
@Component
public class LogInfoListener {
    @Autowired
    private SysLogService sysLogService;
    @EventListener(LogInfoEvent.class)
    public void sendLogInfo(LogInfoEvent event) throws Exception {
        AsyncContext asyncContext = event.getAsyncContext();
        JoinPoint joinPoint = event.getJoinPoint();
        SysUser sysUser = event.getSysUser();
        Long requestStartTime = event.getRequestStartTime();
        HttpServletRequest request = event.getRequest();
        try {
            String className = joinPoint.getTarget().getClass().getName();
            MethodSignature signature = (MethodSignature)joinPoint.getSignature();
            Method method = signature.getMethod();
            Log annotation = method.getAnnotation(Log.class);
            String name = method.getName();
            SysLogVo logInfo = SysLogVo.builder().build();
            String methodName = className + "." + name;
            logInfo.setMethod(methodName);
            if (Objects.nonNull(annotation)) {
                logInfo.setModule(annotation.model());
                logInfo.setType(annotation.type());
                logInfo.setMessage(annotation.desc());
            }
            logInfo.setReqParam(event.getResult());
            logInfo.setUsername(sysUser.getUsername());
            logInfo.setUserId(sysUser.getId());
            logInfo.setTakeUpTime(System.currentTimeMillis()-requestStartTime);
            logInfo.setReqParam(CommonUtil.parseRequest(request));
            logInfo.setIp(CommonUtil.getIp(request));
            logInfo.setUri(request.getRequestURI());
            sysLogService.save(logInfo);
            log.info("拦截日志入库完成:{}", logInfo);

        }catch (Exception e){
            log.info("操作人:{},操作时间:{],正常日志入库异常:{}",sysUser.getUsername(),System.currentTimeMillis(), e.getMessage());
            throw e;
        }finally {
            asyncContext.complete();
        }



    }
}
