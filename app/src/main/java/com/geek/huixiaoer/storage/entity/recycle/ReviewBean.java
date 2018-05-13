package com.geek.huixiaoer.storage.entity.recycle;

import java.io.Serializable;

public class ReviewBean implements Serializable{
        /**
         * replyId : null
         * reviewId : 149
         * content : 不会太
         * memberId : 255
         * headUrl : http://wx.qlogo.cn/mmopen/Q3auHgzwzM4PHOEcBv9tDannQm4QlRIsmK6qhVxaqkGkQibJ6TDj60icgn6N7zHhU3qWbh6W4aAtfq1ict9FQ9fZg/0
         * nickName : 筱辉
         * commentByUserId : null
         * commentByUserNickName : null
         * createDate : 1492483433000
         */

        private String replyId;
        private String reviewId;
        private String content;
        private String memberId;
        private String headUrl;
        private String nickName;
        private String commentByUserId;
        private String commentByUserNickName;
        private long createDate;

        public String getReplyId() {
            return replyId;
        }

        public void setReplyId(String replyId) {
            this.replyId = replyId;
        }

        public String getReviewId() {
            return reviewId;
        }

        public void setReviewId(String reviewId) {
            this.reviewId = reviewId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getCommentByUserId() {
            return commentByUserId;
        }

        public void setCommentByUserId(String commentByUserId) {
            this.commentByUserId = commentByUserId;
        }

        public String getCommentByUserNickName() {
            return commentByUserNickName;
        }

        public void setCommentByUserNickName(String commentByUserNickName) {
            this.commentByUserNickName = commentByUserNickName;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }
    }