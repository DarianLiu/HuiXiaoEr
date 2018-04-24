package com.geek.huixiaoer.storage.entity.street;

import java.util.List;

/**
 * 街道实体
 * Created by Administrator on 2018/4/24.
 */

public class StreetBean {
    private String id;
    private String Name;
    private List<CommunityBean> community;
    private String grade;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setCommunity(List<CommunityBean> community) {
        this.community = community;
    }

    public List<CommunityBean> getCommunity() {
        return community;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }
}
