package com.geek.huixiaoer.storage.entity.recycle;

import com.geek.huixiaoer.storage.entity.ImageBean;

import java.io.Serializable;

/**
 * 富文本文章内容
 * Created by Administrator on 2018/5/13.
 */

public class ContentBean implements Serializable{
    private ImageBean image;
    private String text;

    public void setImage(ImageBean image) {
        this.image = image;
    }

    public ImageBean getImage() {
        return image;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
