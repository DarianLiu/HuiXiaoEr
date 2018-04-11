package com.geek.huixiaoer.storage.entity.shop;

import java.io.Serializable;
import java.util.List;

/**
 * 产品
 * Created by 刘力 on 2018/3/4.
 */

public class ProductBean implements Serializable {

    /**
     * id : 163
     * goods_sn : 2018030321715
     * sn : 2018030321715_1
     * goods_caption :
     * price : 0.01
     * goods_createDate : 1520064854000
     * goods_id : 114
     * goods_name : 004
     * createDate : 1520092957000
     * goods_image : http://182.254.223.66/shopxx/upload/image/201803/5b916b7f-e339-4366-9ffd-e1b993c7da05-thumbnail.jpg
     * cost : 0.02
     * specifications : ["两包","酸"]
     */

    private int id;
    private String goods_sn;
    private String sn;
    private String goods_caption;
    private double price;
    private long goods_createDate;
    private int goods_id;
    private String goods_name;
    private long createDate;
    private String goods_image;
    private String cost;
    private List<String> specifications;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoods_sn() {
        return goods_sn;
    }

    public void setGoods_sn(String goods_sn) {
        this.goods_sn = goods_sn;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getGoods_caption() {
        return goods_caption;
    }

    public void setGoods_caption(String goods_caption) {
        this.goods_caption = goods_caption;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getGoods_createDate() {
        return goods_createDate;
    }

    public void setGoods_createDate(long goods_createDate) {
        this.goods_createDate = goods_createDate;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public List<String> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(List<String> specifications) {
        this.specifications = specifications;
    }

    public String getSpecification() {
        StringBuilder buffer = new StringBuilder();
        if (specifications == null || specifications.size() == 0){
            return "";
        }else {
            for (int i = 0; i < specifications.size(); i++) {
                if (i == 0) {
                    buffer.append(specifications.get(i));
                } else {
                    buffer.append("/").append(specifications.get(i));
                }
            }
            return buffer.toString();
        }
    }
}
