package com.geek.huixiaoer.storage.entity.shop;

import com.geek.huixiaoer.storage.entity.MediumImageBean;

/**
 * 商品
 * Created by Administrator on 2018/2/9.
 */

public class GoodsBean {
    /**
     * id : 106
     * unit :
     * sn : 2017040621320
     * marketPrice : 19.2
     * price : 16
     * MonthSales : 0
     * name : 泰国小老板烤海苔（原味）
     * mediumImage : {"url":"http://yanfumall.oss-cn-hangzhou.aliyuncs.com/upload/image/201704/a41e704d-a1b5-4577-af5b-d82f061d057d-medium.jpg","width":"300","height":"300"}
     * score : 0
     * caption :
     * createDate : 1491482285000
     */

    private int id;
    private String unit;
    private String sn;
    private double marketPrice;
    private double price;
    private int MonthSales;
    private String name;
    private MediumImageBean mediumImage;
    private int score;
    private String caption;
    private long createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMonthSales() {
        return MonthSales;
    }

    public void setMonthSales(int MonthSales) {
        this.MonthSales = MonthSales;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MediumImageBean getMediumImage() {
        return mediumImage;
    }

    public void setMediumImage(MediumImageBean mediumImage) {
        this.mediumImage = mediumImage;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

}
