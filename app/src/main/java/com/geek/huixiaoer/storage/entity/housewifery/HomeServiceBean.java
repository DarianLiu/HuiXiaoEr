package com.geek.huixiaoer.storage.entity.housewifery;

import com.google.gson.annotations.SerializedName;

/**
 * 家政服务项目
 * Created by Administrator on 2018/3/7.
 */

public class HomeServiceBean {

    /**
     * id : 1
     * createDate : 1519886442000
     * modifyDate : 1519886446000
     * version : 1
     * name : 打扫卫生1
     * remark : 30
     * point : 10
     * isEnabled : false
     * isExchange : false
     * new : false
     */

    private int id;
    private long createDate;
    private long modifyDate;
    private int version;
    private String name;
    private String remark;
    private int point;
    private boolean isEnabled;
    private boolean isExchange;
    @SerializedName("new")
    private boolean newX;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(long modifyDate) {
        this.modifyDate = modifyDate;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public boolean isIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public boolean isIsExchange() {
        return isExchange;
    }

    public void setIsExchange(boolean isExchange) {
        this.isExchange = isExchange;
    }

    public boolean isNewX() {
        return newX;
    }

    public void setNewX(boolean newX) {
        this.newX = newX;
    }
}
