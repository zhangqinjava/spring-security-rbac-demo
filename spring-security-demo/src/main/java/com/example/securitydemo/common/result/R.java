package com.example.securitydemo.common.result;

import java.io.Serializable;

public class R <T> {
    private int code;
    private String  msg;
    private T data;
    public R() {

    }
    private R(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public static R ok() {
        return new R(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMessage(),null);
    }
    public static R ok(Object data) {
        return new R(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMessage(),data);
    }
    public static R ok(int code, String msg, Object data) {
        return new R(code, msg,data);
    }
    public static R fail() {
        return new R(CodeEnum.ERROR.getCode(), CodeEnum.ERROR.getMessage(),null);
    }
    public static R fail(String msg) {
        return new R(CodeEnum.ERROR.getCode(), msg,null);
    }
    public static R fail(int code, String msg, Object data) {
        return new R(code, msg,data);
    }
    public static R fail(int code, String msg) {
        return new R(code, msg,null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
