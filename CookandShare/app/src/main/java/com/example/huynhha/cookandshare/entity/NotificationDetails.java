package com.example.huynhha.cookandshare.entity;

public class NotificationDetails {
    private String content;
    private String postID;
    private String time;
    private String type;
    private String userID;
    private String userUrlImage;
    private String userName;

    public NotificationDetails() {

    }


    public NotificationDetails(String userName, String content, String postID, String time, String type, String userID, String userUrlImage) {
        this.content = content;
        this.postID = postID;
        this.time = time;
        this.type = type;
        this.userID = userID;
        this.userUrlImage = userUrlImage;
        this.userName = userName;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserUrlImage() {
        return userUrlImage;
    }

    public void setUserUrlImage(String userUrlImage) {
        this.userUrlImage = userUrlImage;
    }
}
