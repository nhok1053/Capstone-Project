package com.example.huynhha.cookandshare.entity;

import java.util.ArrayList;

public class Category {
    private int categoryID;
    private String categoryName;
    private String categoryImgUrl;
    private ArrayList<String> postCategory;

    public Category(int categoryID, String categoryName, String categoryImgUrl, ArrayList<String> postCategory) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.categoryImgUrl = categoryImgUrl;
        this.postCategory = postCategory;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImgUrl() {
        return categoryImgUrl;
    }

    public void setCategoryImgUrl(String categoryImgUrl) {
        this.categoryImgUrl = categoryImgUrl;
    }

    public ArrayList<String> getPostCategory() {
        return postCategory;
    }

    public void setPostCategory(ArrayList<String> postCategory) {
        this.postCategory = postCategory;
    }

    public Category(int categoryID, String categoryName, String categoryImgUrl) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.categoryImgUrl = categoryImgUrl;
    }

}
