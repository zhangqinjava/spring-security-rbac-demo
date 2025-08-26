package com.example.securitydemo.common;

import com.example.securitydemo.common.result.R;
import com.example.securitydemo.util.SecurityUserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusiException.class)
    public R handleBusiException(BusiException e) {
        if(StringUtils.isEmpty(e.getCode())){
            return R.fail(500,e.getMessage());
        }
        return R.fail(Integer.valueOf(e.getCode()),e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public R handleRunTimeException(RuntimeException e) {
        log.info("当前用户:{}操作运营平台报错:{}", SecurityUserUtils.getUser().getUsername(),e.getMessage());
        return R.fail(500,"服务器运行异常");
    }

    @ExceptionHandler(Exception.class)
    public R handleException(RuntimeException e) {
        return R.fail(500,"系统异常: "+e.getMessage());
    }

}
