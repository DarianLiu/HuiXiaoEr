package com.geek.huixiaoer.storage;

/**
 * 服务器返回数据封装
 * Created by Administrator on 2018/2/1.
 */
public class BaseResponse<T> {
    private String result;
    private T data;
    private String msg;

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
