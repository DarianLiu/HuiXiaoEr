package com.geek.huixiaoer.storage.entity.shop;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车
 * Created by Administrator on 2018/3/5.
 */

public class CartBean implements Serializable {

    /**
     * expire : 1520824769000
     * effectivePrice : 0.02
     * quantity : 1
     * key : 939009f4ef5a09874ac131c17d690bd4
     */

    private List<MerchantBean> datas;//购物车商户列表
    private long expire;//到期时间
    private double effectivePrice;//有效价格
    private int quantity;//数量
    private String key;

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public double getEffectivePrice() {
        return effectivePrice;
    }

    public void setEffectivePrice(double effectivePrice) {
        this.effectivePrice = effectivePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setDatas(List<MerchantBean> datas) {
        this.datas = datas;
    }

    public List<MerchantBean> getDatas() {
        return datas;
    }
}
