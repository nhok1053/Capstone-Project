package com.example.huynhha.cookandshare.entity;

public class Like {
    private String userID;
    private String userName;
    private String userImg;
    private String time;

    public Like(String userID, String userName, String userImg, String time) {
        this.userID = userID;
        this.userName = userName;
        this.userImg = userImg;
        this.time = time;
    }

    public Like() {
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

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
