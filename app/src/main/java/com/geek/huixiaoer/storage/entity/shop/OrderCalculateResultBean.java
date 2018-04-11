package com.geek.huixiaoer.storage.entity.shop;

/**
 * 订单计算返回结果
 * Created by Administrator on 2018/3/7.
 */

public class  OrderCalculateResultBean {

    /**
     * amount : 0.02
     * taxRate : 0.06
     * tax : 0
     * freight : 0
     * rewardPoint : 2
     * couponDiscount : 0
     * promotionDiscount : 0
     */

    private String amount;
    private String taxRate;
    private String tax;
    private String freight;
    private String rewardPoint;
    private String couponDiscount;
    private String promotionDiscount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(String rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    public String getCouponDiscount() {
        return couponDiscount;
    }

    public void setCouponDiscount(String couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public String getPromotionDiscount() {
        return promotionDiscount;
    }

    public void setPromotionDiscount(String promotionDiscount) {
        this.promotionDiscount = promotionDiscount;
    }
}
