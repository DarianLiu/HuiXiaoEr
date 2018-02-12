package com.geek.huixiaoer.storage.entity.article;

public class ReviewBean {
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

        private Object replyId;
        private String reviewId;
        private String content;
        private String memberId;
        private String headUrl;
        private String nickName;
        private Object commentByUserId;
        private Object commentByUserNickName;
        private long createDate;

        public Object getReplyId() {
            return replyId;
        }

        public void setReplyId(Object replyId) {
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

        public Object getCommentByUserId() {
            return commentByUserId;
        }

        public void setCommentByUserId(Object commentByUserId) {
            this.commentByUserId = commentByUserId;
        }

        public Object getCommentByUserNickName() {
            return commentByUserNickName;
        }

        public void setCommentByUserNickName(Object commentByUserNickName) {
            this.commentByUserNickName = commentByUserNickName;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }
    }