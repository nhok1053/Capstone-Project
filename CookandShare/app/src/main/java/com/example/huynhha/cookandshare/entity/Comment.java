package com.example.huynhha.cookandshare.entity;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Comment {
    private String userID;
    private String userName;
    private String userImgUrl;
    private String commentContent;
    @ServerTimestamp
    Date time;


    public Comment(String userID, String userName, String userImgUrl, String commentContent, Date time) {
        this.userID = userID;
        this.userName = userName;
        this.userImgUrl = userImgUrl;
        this.commentContent = commentContent;
        this.time = time;
    }

    public Date getTime() {

        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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
