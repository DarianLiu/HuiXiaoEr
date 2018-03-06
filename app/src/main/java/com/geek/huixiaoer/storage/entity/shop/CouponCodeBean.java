package com.geek.huixiaoer.storage.entity.shop;

/**
 * 优惠券Code
 */
public class CouponCodeBean {
    /**
     * name : 新会员5元优惠券
     * value : reg_43C08E9630711284D2D1CE4AC5D6E60A
     */

    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString(){
        return name;
    }
}