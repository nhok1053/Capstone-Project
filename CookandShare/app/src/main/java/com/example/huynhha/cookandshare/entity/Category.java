package com.example.huynhha.cookandshare.entity;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String categoryID;
    private String categoryName;
    private String categoryImgUrl;

    public Category(String categoryID, String categoryName, String categoryImgUrl) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.categoryImgUrl = categoryImgUrl;
    }

    static ArrayList<Category> lst=new ArrayList<>();
    public static ArrayList<Category> addListCategory(){
        lst.add(new Category("","Legend","https://i.pinimg.com/564x/31/1e/68/311e68c6bd86f051f9d116ee90c51941.jpg"));
        lst.add(new Category("","Power Core","https://i.pinimg.com/564x/8a/10/8a/8a108a478f3f25158750cdedc4001370.jpg"));
        lst.add(new Category("","Tyrande","https://i.pinimg.com/564x/5b/7f/d9/5b7fd93532746ec5918937a2d9134457.jpg"));
        lst.add(new Category("","Golden Celebration","https://i.pinimg.com/564x/43/a5/6e/43a56ed9097f3f42bf5c691ba20b0d2d.jpg"));
        lst.add(new Category("","Over Watch","https://i.pinimg.com/564x/b2/9e/d3/b29ed3eeee6d30841196ebfec1977b1c.jpg"));
        lst.add(new Category("","Black Rock Mountain","https://i.pinimg.com/564x/10/af/ca/10afca31965f152993d499275b5f39e7.jpg"));
        lst.add(new Category("","Yogg Saron","https://i.pinimg.com/564x/23/73/85/237385758a0035a9ce5cab3486a70a8a.jpg"));
        lst.add(new Category("","The Grand Tournament","https://i.pinimg.com/564x/58/c5/58/58c558fe4d6847359225c32425f804b5.jpg"));
        lst.add(new Category("","Classic","https://i.pinimg.com/564x/ed/b4/41/edb441805dd7ac2a4436a8478de0f95a.jpg"));
        lst.add(new Category("","Valentine","https://i.pinimg.com/564x/31/3a/7d/313a7dd6857ecba8823bb0e430945b97.jpg"));
        lst.add(new Category("","Blizzard Entertainment","https://i.pinimg.com/564x/b0/ed/1c/b0ed1ca94a5e83d2ed79e72b156df6fb.jpg"));
        lst.add(new Category("","HOTS","https://i.pinimg.com/564x/c4/45/a2/c445a287529df55a4c17c6523140d491.jpg"));
        return lst;
    }
    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
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
}
