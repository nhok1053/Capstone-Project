package com.example.huynhha.cookandshare.entity;

public class Report {
    private String time;
    private String content;
    private String userID;
    private String userName;
    private int status;

    public Report() {
    }

    public Report(String time, String content, String userID, String userName, int status) {
        this.time = time;
        this.content = content;
        this.userID = userID;
        this.userName = userName;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
