package com.geek.huixiaoer.storage.entity.recycle;

import java.util.List;

/**
 * 文章
 * Created by Administrator on 2018/2/12.
 */

public class ArticleBean {

    /**
     * isAttention : 0
     * isReview : 0
     * countFavorite : 0
     * member_username : sara
     * isMore : 0
     * countLike : 2
     * id : 333
     * countReview : 1
     * title :
     * isFavorite : 0
     * member_nickname : Sara
     * countShare : 0
     * longitude :
     * assistantContents : [{"replyId":null,"reviewId":"149","content":"不会太","memberId":"255","headUrl":"http://wx.qlogo.cn/mmopen/Q3auHgzwzM4PHOEcBv9tDannQm4QlRIsmK6qhVxaqkGkQibJ6TDj60icgn6N7zHhU3qWbh6W4aAtfq1ict9FQ9fZg/0","nickName":"筱辉","commentByUserId":null,"commentByUserNickName":null,"createDate":1492483433000}]
     * createDate : 1492478362000
     * addressName :
     * storeName :
     * member_headUrl : http://yanfumall.oss-cn-hangzhou.aliyuncs.com/upload/image/201703/70af28c2-f9e5-4403-ad40-12ac198feed1.png
     * member_id : 91
     * content : 为了减肥，早起晚睡，我也是拼了?
     * hits : 9
     * isLike : 0
     * images : ["http://yanfumall.oss-cn-hangzhou.aliyuncs.com/upload/image/201704/888077ef-c478-4735-9eec-8d4cfa544b3e.png","http://yanfumall.oss-cn-hangzhou.aliyuncs.com/upload/image/201704/eb210b1b-b9a7-4331-9220-979315a25276.png"]
     * latitude :
     */

    private String isAttention;
    private String isReview;
    private String countFavorite;
    private String member_username;
    private String isMore;
    private String countLike;
    private String id;
    private String countReview;
    private String title;
    private String isFavorite;
    private String member_nickname;
    private String countShare;
    private String longitude;
    private long createDate;
    private String addressName;
    private String storeName;
    private String member_headUrl;
    private String member_id;
    private String content;
    private String hits;
    private String isLike;
    private String latitude;
    private List<ReviewBean> assistantContents;
    private List<String> images;

    public String getIsAttention() {
        return isAttention;
    }

    public void setIsAttention(String isAttention) {
        this.isAttention = isAttention;
    }

    public String getIsReview() {
        return isReview;
    }

    public void setIsReview(String isReview) {
        this.isReview = isReview;
    }

    public String getCountFavorite() {
        return countFavorite;
    }

    public void setCountFavorite(String countFavorite) {
        this.countFavorite = countFavorite;
    }

    public String getMember_username() {
        return member_username;
    }

    public void setMember_username(String member_username) {
        this.member_username = member_username;
    }

    public String getIsMore() {
        return isMore;
    }

    public void setIsMore(String isMore) {
        this.isMore = isMore;
    }

    public String getCountLike() {
        return countLike;
    }

    public void setCountLike(String countLike) {
        this.countLike = countLike;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountReview() {
        return countReview;
    }

    public void setCountReview(String countReview) {
        this.countReview = countReview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getMember_nickname() {
        return member_nickname;
    }

    public void setMember_nickname(String member_nickname) {
        this.member_nickname = member_nickname;
    }

    public String getCountShare() {
        return countShare;
    }

    public void setCountShare(String countShare) {
        this.countShare = countShare;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getMember_headUrl() {
        return member_headUrl;
    }

    public void setMember_headUrl(String member_headUrl) {
        this.member_headUrl = member_headUrl;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public List<ReviewBean> getAssistantContents() {
        return assistantContents;
    }

    public void setAssistantContents(List<ReviewBean> assistantContents) {
        this.assistantContents = assistantContents;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
