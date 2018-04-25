package com.geek.huixiaoer.storage.entity.recycle;

/**
 * 黑名单实体
 * Created by Administrator on 2018/4/25.
 */

public class BlackBean {

    /**
     * id : 7
     * blackTime : 1524106495000
     * nickname : test
     * name : 帮你忙商户
     * blackReason : test1
     * isBlack : 1
     */

    private int id;
    private long blackTime;
    private String nickname;
    private String name;
    private String blackReason;
    private String isBlack;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getBlackTime() {
        return blackTime;
    }

    public void setBlackTime(long blackTime) {
        this.blackTime = blackTime;
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

    public String getBlackReason() {
        return blackReason;
    }

    public void setBlackReason(String blackReason) {
        this.blackReason = blackReason;
    }

    public String getIsBlack() {
        return isBlack;
    }

    public void setIsBlack(String isBlack) {
        this.isBlack = isBlack;
    }
}
