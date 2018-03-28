package com.geek.huixiaoer.storage.entity.shop;

import java.io.Serializable;

/**
 * 收货地址
 */
public class ReceiverBean implements Serializable{
    /**
     * id : 82
     * phone : 13918175465
     * area_id : 794
     * areaName : 上海市徐汇区
     * address : 怒龙默默哦哦
     * isDefault : false
     * consignee : 刘力
     * zipCode : 123456
     * createDate : 1484104425000
     */

    private String id;
    private String phone;
    private String area_id;
    private String areaName;
    private String address;
    private boolean isDefault;
    private String consignee;
    private String zipCode;
    private long createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}