package com.example.huynhha.cookandshare.entity;

import java.util.ArrayList;

public class Notification {
    private String userID;
    private ArrayList<NotificationDetails> notificationDetails;

    public Notification() {
    }

    public Notification(String userID, ArrayList<NotificationDetails> notificationDetails) {
        this.userID = userID;
        this.notificationDetails = notificationDetails;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public ArrayList<NotificationDetails> getNotificationDetails() {
        return notificationDetails;
    }

    public void setNotificationDetails(ArrayList<NotificationDetails> notificationDetails) {
        this.notificationDetails = notificationDetails;
    }
}
