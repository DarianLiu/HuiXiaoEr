package com.geek.huixiaoer.storage.entity.shop;

import java.util.List;

/**
 * 购物订单子项
 */
public class OrderItemBean {
    /**
     * product_goods_id : 122
     * thumbnail : http://182.254.223.66/shopxx/upload/image/201803/aeaae12e-58d9-4e8e-8253-8b0df0269d83-thumbnail.jpg
     * product_specifications : ["两包"]
     * price : 14.9
     * product_goods_sn : 009
     * name : 维达(Vinda) 抽纸
     * product_goods_createDate : 1521810238000
     * quantity : 1
     * type : general
     * product_goods_caption :
     */

    private int product_goods_id;
    private String thumbnail;
    private double price;
    private String product_goods_sn;
    private String name;
    private long product_goods_createDate;
    private int quantity;
    private String type;
    private String product_goods_caption;
    private List<String> product_specifications;

    public int getProduct_goods_id() {
        return product_goods_id;
    }

    public void setProduct_goods_id(int product_goods_id) {
        this.product_goods_id = product_goods_id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProduct_goods_sn() {
        return product_goods_sn;
    }

    public void setProduct_goods_sn(String product_goods_sn) {
        this.product_goods_sn = product_goods_sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getProduct_goods_createDate() {
        return product_goods_createDate;
    }

    public void setProduct_goods_createDate(long product_goods_createDate) {
        this.product_goods_createDate = product_goods_createDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProduct_goods_caption() {
        return product_goods_caption;
    }

    public void setProduct_goods_caption(String product_goods_caption) {
        this.product_goods_caption = product_goods_caption;
    }

    public List<String> getProduct_specifications() {
        return product_specifications;
    }

    public String getSpecifications() {
        String specification = "";
        for (int i = 0; i < product_specifications.size(); i++) {
            specification = specification + product_specifications.get(i);
        }
        return specification;
    }

    public void setProduct_specifications(List<String> product_specifications) {
        this.product_specifications = product_specifications;
    }
}