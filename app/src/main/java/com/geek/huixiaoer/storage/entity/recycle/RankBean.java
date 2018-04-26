package com.geek.huixiaoer.storage.entity.recycle;

/**
 * 积分排行榜实体
 * Created by Administrator on 2018/4/26.
 */

public class RankBean {

    /**
     * id : 10
     * point : 100
     * weight : 8
     * nickname : merchant3
     * name : 招牌菜商户
     */

    private String id;
    private String point;
    private String weight;
    private String nickname;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
