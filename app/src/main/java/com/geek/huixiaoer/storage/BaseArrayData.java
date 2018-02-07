package com.geek.huixiaoer.storage;

import java.util.List;

/**
 * /**
 * 服务器返回数据封装
 * Created by Administrator on 2018/2/1.
 */
public class BaseArrayData<T> {

    private List<T> pageData;

    public void setPageData(List<T> pageData) {
        this.pageData = pageData;
    }

    public List<T> getPageData() {
        return pageData;
    }
}
