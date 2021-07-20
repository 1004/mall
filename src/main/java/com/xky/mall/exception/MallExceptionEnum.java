package com.xky.mall.exception;

public enum MallExceptionEnum {
    USER_NEED_NAME(1000, "用户名不能为空"),
    USER_NAME_REPEAT(1001,"用户名已经存在"),
    USER_REGIST_FAILED(1002,"用户注册失败"),
    USER_NEED_PWD(1003, "密码不能为空"),
    USER_PWD_LENGTH(1004, "密码最少8位"),

    SYSTEM_ERROR(10000, "系统异常");

    private int code;
    private String msg;

    MallExceptionEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
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
}
