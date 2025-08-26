package com.example.securitydemo.common;

public class BusiException extends RuntimeException {
    private String code;
    private String msg;
    public BusiException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BusiException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
