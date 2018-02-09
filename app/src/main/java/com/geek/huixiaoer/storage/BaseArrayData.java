package com.geek.huixiaoer.storage;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * /**
 * 服务器返回数据封装
 * Created by Administrator on 2018/2/1.
 */
public class BaseArrayData<T> {

    @SerializedName(value = "pageData", alternate = {"productCategories"})
    private List<T> pageData;

    public void setPageData(List<T> pageData) {
        this.pageData = pageData;
    }

    public List<T> getPageData() {
        return pageData;
    }
}
