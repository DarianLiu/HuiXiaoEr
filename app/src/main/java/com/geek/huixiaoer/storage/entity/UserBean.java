package com.geek.huixiaoer.storage.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 用户
 * Created by Administrator on 2018/2/1.
 */

public class UserBean implements Serializable {

    /**
     * id : 122
     * isStore : 0
     * token : 2aa6db12bc524c6bbff9c36557f82388
     * net.shopxx.interceptor.MemberInterceptor.PRINCIPAL : {"id":122,"username":"大王派我来巡山","nickname":"大王派我来巡山","usersign":"这个人很懒，暂无签名","usersex":0}
     * referer :
     * headimgurl : http://yanfumall.oss-cn-hangzhou.aliyuncs.com/upload/image/201703/4ba006ad-5f03-403e-9806-09130913aac6.png
     * mobile : 13918175465
     */

    private String id;
    private String isStore;
    private String token;
    @SerializedName("net.shopxx.interceptor.MemberInterceptor.PRINCIPAL")
    private UserInfoBean userInfo; // FIXME check this code
    private String referer;
    private String headimgurl;
    private String mobile;
    private String ryId;
    private String ryToken;

    public void setRyToken(String ryToken) {
        this.ryToken = ryToken;
    }

    public String getRyToken() {
        return ryToken;
    }

    public void setRyId(String ryId) {
        this.ryId = ryId;
    }

    public String getRyId() {
        return ryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsStore() {
        return isStore;
    }

    public void setIsStore(String isStore) {
        this.isStore = isStore;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
