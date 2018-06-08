package com.geek.huixiaoer.storage.entity.shop;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 购物订单
 * Created by 刘力 on 2018/3/4.
 */

public class OrderBean {


    /**
     * amount : 14.9
     * merchantHeadURL : http://yanfumall.oss-cn-hangzhou.aliyuncs.com/upload/image/201612/71876fef-4e42-4a04-9063-e276c5a88793.png
     * items : [{"product_goods_id":122,"thumbnail":"http://182.254.223.66/shopxx/upload/image/201803/aeaae12e-58d9-4e8e-8253-8b0df0269d83-thumbnail.jpg","product_specifications":["两包"],"price":14.9,"product_goods_sn":"009","name":"维达(Vinda) 抽纸","product_goods_createDate":1521810238000,"quantity":1,"type":"general","product_goods_caption":""}]
     * address : 上海市徐汇区怒龙默默哦哦
     * status : pendingPayment
     * consignee : 刘力
     * merchantName : 华联超市
     * order_sn : 2018032839088
     * outTradeNo : b0a9886651bd4dfd9444956e465cdece
     * createDate : 1522208611000
     */

    private String id;
    private double amount;
    @SerializedName(value = "merchantHeadURL", alternate = {"merchant_headUrl"})
    private String merchantHeadURL;
    private String address;
    private String status;
    private String consignee;
    @SerializedName(value = "merchantName", alternate = {"merchant_name"})
    private String merchantName;
    @SerializedName(value = "order_sn", alternate = {"sn"})
    private String order_sn;
    private String outTradeNo;
    private long createDate;
    @SerializedName(value = "items", alternate = {"orderItems"})
    private List<OrderItemBean> items;
    private String memo;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMerchantHeadURL() {
        return merchantHeadURL;
    }

    public void setMerchantHeadURL(String merchantHeadURL) {
        this.merchantHeadURL = merchantHeadURL;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public List<OrderItemBean> getItems() {
        return items;
    }

    public void setItems(List<OrderItemBean> items) {
        this.items = items;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMemo() {
        return memo;
    }
}
