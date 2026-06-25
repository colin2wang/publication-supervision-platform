package com.pub.supervision.common.enums;

import lombok.Getter;

@Getter
public enum ResultCode {

    OK(200, "OK"),
    PARAM_ERROR(40001, "参数错误"),
    UNAUTHORIZED(40101, "未授权"),
    FORBIDDEN(40301, "禁止访问"),
    NOT_FOUND(40401, "资源不存在"),
    SYSTEM_ERROR(50001, "系统错误");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
