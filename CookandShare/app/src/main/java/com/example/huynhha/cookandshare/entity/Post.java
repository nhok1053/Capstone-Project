package com.example.huynhha.cookandshare.entity;


import java.util.ArrayList;
import java.util.List;

public class Post {
    private String postID;
    private String userID;
    private String userImgUrl;
    private String time;
    private String imgUrl;
    private String title;
    private String description;
private int likeNumber;
private int commentNumber;
    private ArrayList<Like> like;
    private ArrayList<Comment> comment;


    public Post(String postID, String userID, String time, String imgUrl, String title,
                String description, String userImgUrl, int like, int comment) {
        this.postID = postID;
        this.userID = userID;
        this.time = time;
        this.imgUrl = imgUrl;
        this.title = title;
        this.description = description;
        this.likeNumber = like;
        this.commentNumber = comment;
        this.userImgUrl = userImgUrl;
    }
    public Post(String postID, String userID, String time, String imgUrl, String title,
                String description, String userImgUrl) {
        this.postID = postID;
        this.userID = userID;
        this.time = time;
        this.imgUrl = imgUrl;
        this.title = title;
        this.description = description;

        this.userImgUrl = userImgUrl;
    }

    public Post() {
    }
    public static List<Post> posts = new ArrayList<>();
    public static List<Post> initData() {
        posts.add(new Post("Post0001","MiideviL","6-9-2018","https://i.pinimg.com/564x/5b/7f/d9/5b7fd93532746ec5918937a2d9134457.jpg","Recipe 001",
                "Mon dau tien","https://i.pinimg.com/564x/a8/d4/a3/a8d4a3d0225f5e3def397f257b07a155.jpg",10,10));
        posts.add(new Post("Post0001","MiideviL","6-9-2018","http://media-cache-ec0.pinimg.com/1200x/0e/80/07/0e8007ccbb7441c8058573e80b2efd1e.jpg","Recipe 002",
                "Mon thu hai","https://i.pinimg.com/564x/05/35/9d/05359d0c37e375e650f30c9798313696.jpg",10,10));
        return  posts;
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

    public ArrayList<Comment> getComment() {
        return comment;
    }

    public void setComment(ArrayList<Comment> comment) {
        this.comment = comment;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public int getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(int commentNumber) {
        this.commentNumber = commentNumber;
    }

}
