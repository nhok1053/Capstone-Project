package com.example.huynhha.cookandshare.entity;


import java.util.ArrayList;

public class Post {
    private String postID;
    private String userID;
    private String userImgUrl;
    private String time;
    private String imgUrl;
    private String title;
    private String description;

    private ArrayList<Like> like;
    private ArrayList<Like> comment;


    public Post(String postID, String userID, String time, String imgUrl, String title, String description, String userImgUrl, ArrayList<Like> like, ArrayList<Like> comment) {
        this.postID = postID;
        this.userID = userID;
        this.time = time;
        this.imgUrl = imgUrl;
        this.title = title;
        this.description = description;
        this.like = like;
        this.comment = comment;
        this.userImgUrl = userImgUrl;
    }

    public Post() {
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTime() {
        return time;
    }

    public String getUserImgUrl() {
        return userImgUrl;
    }

    public void setUserImgUrl(String userImgUrl) {
        this.userImgUrl = userImgUrl;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Like> getLike() {
        return like;
    }

    public void setLike(ArrayList<Like> like) {
        this.like = like;
    }

    public ArrayList<Like> getComment() {
        return comment;
    }

    public void setComment(ArrayList<Like> comment) {
        this.comment = comment;
    }
}
