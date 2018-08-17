package com.example.huynhha.cookandshare.entity;

public class Comment {
    private String userID;
    private String userName;
    private String userImgUrl;
    private String commentContent;

    public Comment(String userID, String userName, String userImgUrl, String commentContent) {
        this.userID = userID;
        this.userName = userName;
        this.userImgUrl = userImgUrl;
        this.commentContent = commentContent;
    }

    public Comment(String userID, String userImgUrl, String commentContent) {
        this.userID = userID;
        this.userImgUrl = userImgUrl;
        this.commentContent = commentContent;
    }

    public Comment() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImgUrl() {
        return userImgUrl;
    }

    public void setUserImgUrl(String userImgUrl) {
        this.userImgUrl = userImgUrl;
    }



    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
