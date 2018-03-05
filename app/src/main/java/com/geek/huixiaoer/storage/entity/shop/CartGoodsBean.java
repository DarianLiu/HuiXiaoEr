package com.geek.huixiaoer.storage.entity.shop;

import java.io.Serializable;

/**
 * 购物车商品
 * Created by Administrator on 2018/3/5.
 */

public class CartGoodsBean implements Serializable {

    /**
     * product : {"id":163,"goods_sn":"2018030321715","sn":"2018030321715_1","goods_caption":"","price":0.01,"goods_createDate":1520064854000,"goods_id":114,"goods_name":"004","createDate":1520092957000,"goods_image":"http://182.254.223.66/shopxx/upload/image/201803/5b916b7f-e339-4366-9ffd-e1b993c7da05-thumbnail.jpg","cost":0.02,"specifications":["两包","酸"]}
     * id : 11
     * quantity : 2
     * createDate : 1520219969000
     */

    private ProductBean product;
    private String id;
    private int quantity;
    private long createDate;

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

}
