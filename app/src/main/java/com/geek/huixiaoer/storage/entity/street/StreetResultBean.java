package com.geek.huixiaoer.storage.entity.street;

import java.util.List;

/**
 * 街道信息解析结果
 * Created by Administrator on 2018/4/24.
 */

public class StreetResultBean {

    private List<RegionBean> Area;

    public void setArea(List<RegionBean> Area) {
        this.Area = Area;
    }

    public List<RegionBean> getArea() {
        return Area;
    }
}
