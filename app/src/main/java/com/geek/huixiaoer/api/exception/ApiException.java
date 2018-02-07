package com.geek.huixiaoer.api.exception;

/**
 * 服务器返回的code统一处理
 * Created by Administrator on 2017/12/21.
 */

public class ApiException extends Exception {


    private String code;

    public ApiException(String msg, String code) {
        super(msg);
        this.code = code;
    }


    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
