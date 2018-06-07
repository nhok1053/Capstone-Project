package com.example.huynhha.cookandshare.entity;

public class User {
    private String userID;
    private String imgUrl;

    public User(String userID, String imgUrl) {
        this.userID = userID;
        this.imgUrl = imgUrl;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}

