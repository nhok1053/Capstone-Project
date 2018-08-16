package com.example.huynhha.cookandshare.entity;

import android.net.Uri;

public class PostStep {
    private String uri;
    private String imgURL;
    private String numberOfStep;
    private String description;
    private String time_duration;
    private String temp;


    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public PostStep(String uri, String imgURL, String numberOfStep, String description, String temp, String time_duration) {
        this.uri = uri;
        this.imgURL = imgURL;
        this.numberOfStep = numberOfStep;
        this.description = description;

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


}
