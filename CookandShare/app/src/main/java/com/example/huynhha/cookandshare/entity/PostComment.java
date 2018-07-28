package com.example.huynhha.cookandshare.entity;

import java.util.ArrayList;

public class PostComment {
    private String postID;
    private ArrayList<Comment> comments;

    public PostComment() {
    }

    public PostComment(String postID, ArrayList<Comment> comments) {
        this.postID = postID;
        this.comments = comments;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
