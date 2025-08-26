package com.example.securitydemo.common.result;

public enum CodeEnum {
    SUCCESS(200, "success"),
    ERROR(500, "error"),
    PARAMETER_ERROR(400, "parameter error"),
    AUTH_NO_TOKEN(401,  "用户凭证已过期，请重新登录！"),
    AUTH_NO_USER_LOGIN(402,  "用户登录方式不存在，请重新登录！"),
    AUTH_NO_USER_ROLE(403,  "用户权限校验失败，请重新登录！"),
    AUTH_NO_ACCESS(403,  "无访问权限，请核实!"),
    AUTH_NONEXISTENT(404,  "请求路径不存在"),
    AUTH_NO_USER(405,  "没有用户信息，重新登录"),
    AUTH_NO_USER_TYPE(406,  "没有登录类型"),
    ;
    private int code;
    private String message;
    private CodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
    public static CodeEnum getByCode(int code) {
        for (CodeEnum codeEnum : values()) {
            if (codeEnum.getCode() == code) {
                return codeEnum;
            }
        }
        return null;
    }
}
