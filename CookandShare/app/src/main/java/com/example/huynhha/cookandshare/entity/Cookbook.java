package com.example.huynhha.cookandshare.entity;

import java.util.ArrayList;

public class Cookbook {
    private String cookbookID;
    private String cookbookName;
    private String cookbookDescription;
    private String cookbookMainImage;
    private String numberRecipe;
    private String userID;

    public Cookbook(String cookbookID, String cookbookName) {
        this.cookbookID = cookbookID;
        this.cookbookName = cookbookName;
    }

    public Cookbook(String cookbookID, String cookbookName, String cookbookMainImage, String numberRecipe) {
        this.cookbookID = cookbookID;
        this.cookbookName = cookbookName;
        this.cookbookMainImage = cookbookMainImage;
        this.numberRecipe = numberRecipe;
    }

    public String getCookbookID() {
        return cookbookID;
    }

    public void setCookbookID(String cookbookID) {
        this.cookbookID = cookbookID;
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
