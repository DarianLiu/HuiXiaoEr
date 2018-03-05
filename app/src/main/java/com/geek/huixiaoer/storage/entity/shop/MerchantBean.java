package com.geek.huixiaoer.storage.entity.shop;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车卖家
 * Created by Administrator on 2018/3/5.
 */

public class MerchantBean implements Serializable {

    /**
     * merchantHeadURL : http://yanfumall.oss-cn-hangzhou.aliyuncs.com/upload/image/201612/71876fef-4e42-4a04-9063-e276c5a88793.png
     * merchantName : 商户1
     * merchantId : 3
     */

    @SerializedName(value = "merchantHeadURL")
    private String headURL;
    @SerializedName(value = "merchantName")
    private String name;
    @SerializedName(value = "merchantId")
    private int id;
    private List<CartGoodsBean> items;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setHeadURL(String headURL) {
        this.headURL = headURL;
    }

    public String getHeadURL() {
        return headURL;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setItems(List<CartGoodsBean> items) {
        this.items = items;
    }

    public List<CartGoodsBean> getItems() {
        return items;
    }
}
