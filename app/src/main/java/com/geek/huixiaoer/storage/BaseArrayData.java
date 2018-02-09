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
    private int pageSize;
    private int pageNumber;

    public void setPageData(List<T> pageData) {
        this.pageData = pageData;
    }

    public List<T> getPageData() {
        return pageData;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageNumber() {
        return pageNumber;
    }
}
