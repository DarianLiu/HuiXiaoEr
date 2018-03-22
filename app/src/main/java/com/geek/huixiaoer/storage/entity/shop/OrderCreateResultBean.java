package com.geek.huixiaoer.storage.entity.shop;

import com.google.gson.annotations.SerializedName;

/**
 * 创建订单返回结果
 * Created by Administrator on 2018/3/7.
 */

public class OrderCreateResultBean {
    @SerializedName(value = "outTradeNo", alternate = {"orderStr"})
    private String outTradeNo;

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }
}
