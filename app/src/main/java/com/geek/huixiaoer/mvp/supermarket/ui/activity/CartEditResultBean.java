package com.geek.huixiaoer.mvp.supermarket.ui.activity;

/**
 * 购物车项编辑，删除返回结果
 * Created by Administrator on 2018/3/5.
 */

public class CartEditResultBean {

    private double effectivePrice;//有效支付金额
    private double effectiveRewardPoint;//有效返还积分
    //    private List<String>promotionNames;
//    private List<String> giftNames;//
    private int quantity;//购物车商品总数量
    private boolean isLowStock;//是否锁定
    private double subtotal;

    public void setEffectivePrice(double effectivePrice) {
        this.effectivePrice = effectivePrice;
    }

    public double getEffectivePrice() {
        return effectivePrice;
    }

    public void setEffectiveRewardPoint(double effectiveRewardPoint) {
        this.effectiveRewardPoint = effectiveRewardPoint;
    }

    public double getEffectiveRewardPoint() {
        return effectiveRewardPoint;
    }

    public void setIsLowStock(boolean lowStock) {
        this.isLowStock = lowStock;
    }

    public boolean getIsLowStock() {
        return isLowStock;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
