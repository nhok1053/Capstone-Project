package com.example.huynhha.cookandshare.entity;

import java.util.ArrayList;

public class Cookbook {
    private String cookbookID;
    private String cookbookName;
    private String cookbookDescription;
    ArrayList<Post> arrayListPost;
    private String cookbookMainImage;
    private String numberRecipe;
    private String userID;
    private String userName;
    private String userUrlImage;

    public String getCookbookID() {
        return cookbookID;
    }

    public void setCookbookID(String cookbookID) {
        this.cookbookID = cookbookID;
    }

    public Cookbook(String cookbookName, String cookbookDescription, ArrayList<Post> arrayListPost, String cookbookMainImage, String numberRecipe, String userID, String userName, String userUrlImage) {
        this.cookbookName = cookbookName;
        this.cookbookDescription = cookbookDescription;
        this.arrayListPost = arrayListPost;
        this.cookbookMainImage = cookbookMainImage;
        this.numberRecipe = numberRecipe;
        this.userID = userID;
        this.userName = userName;
        this.userUrlImage = userUrlImage;
    }

    public Cookbook(String cookbookID, String cookbookName, String cookbookMainImage, String numberRecipe, String userID, String userName) {
        this.cookbookName = cookbookName;
        this.cookbookMainImage = cookbookMainImage;
        this.numberRecipe = numberRecipe;
        this.userID = userID;
        this.userName = userName;
        this.cookbookID = cookbookID;
    }

    public String getCookbookMainImage() {
        return cookbookMainImage;
    }

    public void setCookbookMainImage(String cookbookMainImage) {
        this.cookbookMainImage = cookbookMainImage;
    }

    public String getNumberRecipe() {
        return numberRecipe;
    }

    public void setNumberRecipe(String numberRecipe) {
        this.numberRecipe = numberRecipe;
    }

    public String getCookbookName() {
        return cookbookName;
    }

    public void setCookbookName(String cookbookName) {
        this.cookbookName = cookbookName;
    }

    public String getCookbookDescription() {
        return cookbookDescription;
    }

    public void setCookbookDescription(String cookbookDescription) {
        this.cookbookDescription = cookbookDescription;
    }

    public ArrayList<Post> getArrayListPost() {
        return arrayListPost;
    }

    public void setArrayListPost(ArrayList<Post> arrayListPost) {
        this.arrayListPost = arrayListPost;
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
