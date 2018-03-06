package com.geek.huixiaoer.storage.entity.shop;

import java.util.List;

/**
 * 获取普通订单的详细数据
 * Created by Administrator on 2018/3/6.
 */
public class OrderCheckResultBean {

    /**
     * amount : 0.02
     * balance : 0
     * couponCodeList : [{"name":"新会员5元优惠券","value":"reg_43C08E9630711284D2D1CE4AC5D6E60A"}]
     * taxRate : 0.06
     * tax : 0
     * freight : 0
     * rewardPoint : 2
     * couponDiscount : 0
     * defaultReceiver : [{"id":82,"phone":"13918175465","area_id":794,"areaName":"上海市徐汇区","address":"怒龙默默哦哦","isDefault":false,"consignee":"刘力","zipCode":"123456","createDate":1484104425000}]
     * promotionDiscount : 0
     */
    private double amount;//订单金额
    private double balance;//余额
    private double taxRate;//税率
    private double tax;//税金
    private double freight;//运费
    private double rewardPoint;//赠送积分
    private double couponDiscount;//优惠券折扣金额
    private double promotionDiscount;//促销折扣
    private List<CouponCodeBean> couponCodeList;
    private List<ReceiverBean> defaultReceiver;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public double getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(double rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    public double getCouponDiscount() {
        return couponDiscount;
    }

    public void setCouponDiscount(double couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public double getPromotionDiscount() {
        return promotionDiscount;
    }

    public void setPromotionDiscount(double promotionDiscount) {
        this.promotionDiscount = promotionDiscount;
    }

    public List<CouponCodeBean> getCouponCodeList() {
        return couponCodeList;
    }

    public void setCouponCodeList(List<CouponCodeBean> couponCodeList) {
        this.couponCodeList = couponCodeList;
    }

    public List<ReceiverBean> getDefaultReceiver() {
        return defaultReceiver;
    }

    public void setDefaultReceiver(List<ReceiverBean> defaultReceiver) {
        this.defaultReceiver = defaultReceiver;
    }

}
