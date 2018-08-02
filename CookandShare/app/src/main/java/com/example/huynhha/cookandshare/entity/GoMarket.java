package com.example.huynhha.cookandshare.entity;

import java.util.List;

public class GoMarket {
    private int id;
    private String imgUrl;
    private String time;
    private String nameOfRecipe;

    public String getNameOfRecipe() {
        return nameOfRecipe;
    }

    public void setNameOfRecipe(String nameOfRecipe) {
        this.nameOfRecipe = nameOfRecipe;
    }

    private List<Material> materials;

    public GoMarket() {
    }

    public GoMarket(int id, String imgUrl, String time,String nameOfRecipe, List<Material> materials) {
        this.nameOfRecipe = nameOfRecipe;
        this.id = id;
        this.imgUrl = imgUrl;
        this.time = time;
        this.materials = materials;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {

        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }
}
