package com.geek.huixiaoer.storage.entity;

import java.io.Serializable;

/**
 * 图片
 * Created by Administrator on 2018/5/13.
 */

public class ImageBean implements Serializable{

    /**
     * url : http://yanfumall.oss-cn-hangzhou.aliyuncs.com/upload/image/201703/aebfefc0-baa2-4a65-881c-9a0f023a8589.png
     * width : 750
     * height : 416
     */

    private String url;
    private String width;
    private String height;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
