package com.geek.huixiaoer.storage.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 只有一个属性的Result(短信验证码：code，是否收藏：flag)
 * Created by Administrator on 2018/2/11.
 */
public class SingleResultBean {
    @SerializedName(value = "code", alternate = {"flag"})
    private String result;

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
