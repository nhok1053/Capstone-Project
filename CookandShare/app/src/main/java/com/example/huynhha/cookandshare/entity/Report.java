package com.example.huynhha.cookandshare.entity;

public class Report {
    private String time;
    private String content;
    private String userID;
    private String userName;

    public Report() {
    }

    public Report(String time, String content, String userID, String userName) {
        this.time = time;
        this.content = content;
        this.userID = userID;
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
