package com.example.huynhha.cookandshare.entity;

import android.net.Uri;

public class PostStep {
    private String uri;
    private String imgURL;
    private String numberOfStep;
    private String description;
    private String time_duration;
    private String tips;
    private String secret_material;
    private String temp;


    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public PostStep(String uri, String imgURL, String numberOfStep, String description, String tips, String secret_material, String temp, String time_duration) {
        this.uri = uri;
        this.imgURL = imgURL;
        this.numberOfStep = numberOfStep;
        this.description = description;
        this.tips = tips;
        this.secret_material = secret_material;
        this.temp = temp;
        this.time_duration = time_duration;

    }

    public PostStep() {
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
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
