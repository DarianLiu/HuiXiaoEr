package com.geek.huixiaoer.storage.entity;

import java.io.Serializable;

/**
 * 用户详细信息
 * Created by Administrator on 2018/2/11.
 */

public class UserInfoBean implements Serializable {
    private int id;
    private String username;
    private String nickname;
    private String usersign;
    private int usersex;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsersign() {
        return usersign;
    }

    public void setUsersign(String usersign) {
        this.usersign = usersign;
    }

    public int getUsersex() {
        return usersex;
    }

    public void setUsersex(int usersex) {
        this.usersex = usersex;
    }
}
