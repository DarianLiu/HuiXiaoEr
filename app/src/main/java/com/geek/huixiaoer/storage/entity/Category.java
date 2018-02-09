package com.geek.huixiaoer.storage.entity;

/**
 * 分类
 * Created by Administrator on 2018/2/9.
 */

public class Category {
    private int id;
    private String name;
    private String grade;//级别

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }
}
