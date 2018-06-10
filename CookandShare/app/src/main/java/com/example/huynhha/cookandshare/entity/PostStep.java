package com.example.huynhha.cookandshare.entity;

public class PostStep {
    private String imgURL;
    private String numberOfStep;
    private String description;
    private String time_duration;
    private String tips;
    private String secret_material;

    public PostStep(String numberOfStep, String description, String time_duration, String tips, String secret_material,String imgURL) {
        this.numberOfStep = numberOfStep;
        this.description = description;
        this.time_duration = time_duration;
        this.tips = tips;
        this.secret_material = secret_material;
        this.imgURL = imgURL;
    }

    public PostStep() {
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getNumberOfStep() {
        return numberOfStep;
    }

    public void setNumberOfStep(String numberOfStep) {
        this.numberOfStep = numberOfStep;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime_duration() {
        return time_duration;
    }

    public void setTime_duration(String time_duration) {
        this.time_duration = time_duration;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getSecret_material() {
        return secret_material;
    }

    public void setSecret_material(String secret_material) {
        this.secret_material = secret_material;
    }
}