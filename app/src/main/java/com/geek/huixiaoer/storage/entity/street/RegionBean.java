package com.geek.huixiaoer.storage.entity.street;

import java.util.List;

/**
 * 行政区实体
 * Created by Administrator on 2018/4/24.
 */

public class RegionBean {
    private String id;
    private String Name;
    private String grade;
    private List<StreetBean> street;

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStreet(List<StreetBean> street) {
        this.street = street;
    }

    public String getName() {
        return Name;
    }

    public List<StreetBean> getStreet() {
        return street;
    }

    public String getGrade() {
        return grade;
    }

    public String getId() {
        return id;
    }
}
