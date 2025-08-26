package com.example.securitydemo.common.aop;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 操作模块
     * @return
     */
    String model() default "";

    /**
     * 操作类型
     * @return
     */
    String type() default "";

    /**
     * 操作说明
     * @return
     */
    String desc() default "";
}
