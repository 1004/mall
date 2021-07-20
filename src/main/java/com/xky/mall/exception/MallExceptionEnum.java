package com.xky.mall.exception;

public enum MallExceptionEnum {
    USER_NEED_NAME(1000, "用户名不能为空");

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
