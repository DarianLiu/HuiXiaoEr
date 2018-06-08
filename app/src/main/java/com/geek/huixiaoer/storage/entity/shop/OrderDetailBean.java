package com.geek.huixiaoer.storage.entity.shop;

import java.util.List;

/**
 * Created by Administrator on 2018/6/5.
 */

public class OrderDetailBean {

    /**
     * amount : 0.01
     * fee : 0
     * phone : 1234354345
     * tax : 0
     * memo :
     * address : 内蒙古自治区呼和浩特市新城区噶嘎嘎嘎嘎
     * consignee : 发发上的
     * rewardPoint : 0
     * freight : 0
     * orderStatusTips : 尊敬的客户，您的订单正在等待审核，请您耐心等待！
     * orderStatus : 支付成功
     * merchantCash : 0.01
     */

    private double amount;
    private int fee;
    private String phone;
    private int tax;
    private String memo;
    private String address;
    private String consignee;
    private int rewardPoint;
    private int freight;
    private String orderStatusTips;
    private String orderStatus;
    private double merchantCash;
    private List<OrderBean> orders;

    public void setAddress(String address) {
        this.address = address;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getMerchantCash() {
        return merchantCash;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getFreight() {
        return freight;
    }

    public void setFreight(int freight) {
        this.freight = freight;
    }

    public int getRewardPoint() {
        return rewardPoint;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getTax() {
        return tax;
    }

    public void setMerchantCash(double merchantCash) {
        this.merchantCash = merchantCash;
    }

    public List<OrderBean> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderBean> orders) {
        this.orders = orders;
    }

    public String getAddress() {
        return address;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setOrderStatusTips(String orderStatusTips) {
        this.orderStatusTips = orderStatusTips;
    }

    public String getMemo() {
        return memo;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setRewardPoint(int rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    public String getOrderStatusTips() {
        return orderStatusTips;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public String getPhone() {
        return phone;
    }
}
