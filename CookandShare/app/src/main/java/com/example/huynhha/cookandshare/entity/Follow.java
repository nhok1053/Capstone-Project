package com.example.huynhha.cookandshare.entity;

import java.util.List;

public class Follow {
    private String userID;
    private String userName;
    private String userUrlImage;
    private List<Follow> follows;

    public List<Follow> getFollows() {
        return follows;
    }

    public void setFollows(List<Follow> follows) {
        this.follows = follows;
    }

    public Follow() {
    }

    public Follow(String userID, String userName, String userUrlImage) {
        this.userID = userID;
        this.userName = userName;
        this.userUrlImage = userUrlImage;
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

    public String getUserUrlImage() {
        return userUrlImage;
    }

    public void setUserUrlImage(String userUrlImage) {
        this.userUrlImage = userUrlImage;
    }
}
