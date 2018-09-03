package com.example.huynhha.cookandshare.entity;


import java.util.ArrayList;
import java.util.List;

public class Post {
    private String numberOfRate;
    private String userName;
    private String numberOfPeople;
    private String postID;
    private String userID;
    private String userImgUrl;
    private String title;
    private String titleLower;
    private String description;
    private String time;
    private String urlImage;
    private String difficult;
    private int like;
    private int comment;
    private String postTime;
    private int countRate;
    private int countView;
    private int numberOfReported;
    private List<Integer> listCategory;
    private List<Material> materials;
    private List<PostStep> postSteps;

    public Post(String postID, String userID, String time, String imgUrl, String title, String description, String userImgUrl, int like, int comment, String userName, int countView) {
        this.userName = userName;
        this.urlImage = imgUrl;
        this.postID = postID;
        this.userID = userID;
        this.userImgUrl = userImgUrl;
        this.title = title;
        this.description = description;
        this.time = time;
        this.like = like;
        this.comment = comment;
        this.countView = countView;
    }

    public int getCountRate() {
        return countRate;
    }

    public void setCountRate(int countRate) {
        this.countRate = countRate;
    }

    public int getCountView() {
        return countView;
    }

    public void setCountView(int countView) {
        this.countView = countView;
    }

    public Post(String numberOfRate, String userName, String numberOfPeople, String postID, String userID, String userImgUrl, String title, String titleLower, String description, String time, String urlImage, String difficult, int like, int comment, String postTime, int countRate, int countView, List<Material> materials, List<PostStep> postSteps) {
        this.numberOfRate = numberOfRate;
        this.userName = userName;
        this.numberOfPeople = numberOfPeople;
        this.postID = postID;
        this.userID = userID;
        this.userImgUrl = userImgUrl;
        this.title = title;
        this.titleLower = titleLower;
        this.description = description;
        this.time = time;
        this.urlImage = urlImage;
        this.difficult = difficult;
        this.like = like;
        this.comment = comment;
        this.postTime = postTime;
        this.countRate = countRate;
        this.countView = countView;
        this.materials = materials;
        this.postSteps = postSteps;
    }

    public Post(String numberOfRate, String userName, String numberOfPeople, String postID, String userID, String userImgUrl, String title, String titleLower, String description, String time, String urlImage, String difficult, int like, int comment, String postTime, int countRate, int countView, int numberOfReported, List<Integer> listCategory, List<Material> materials, List<PostStep> postSteps) {
        this.numberOfRate = numberOfRate;
        this.userName = userName;
        this.numberOfPeople = numberOfPeople;
        this.postID = postID;
        this.userID = userID;
        this.userImgUrl = userImgUrl;
        this.title = title;
        this.titleLower = titleLower;
        this.description = description;
        this.time = time;
        this.urlImage = urlImage;
        this.difficult = difficult;
        this.like = like;
        this.comment = comment;
        this.postTime = postTime;
        this.countRate = countRate;
        this.countView = countView;
        this.numberOfReported = numberOfReported;
        this.listCategory = listCategory;
        this.materials = materials;
        this.postSteps = postSteps;
    }

    public int getNumberOfReported() {
        return numberOfReported;
    }

    public void setNumberOfReported(int numberOfReported) {
        this.numberOfReported = numberOfReported;
    }

    public Post(String numberOfRate, String userName, String postID, String title, String urlImage) {
        this.numberOfRate = numberOfRate;
        this.userName = userName;
        this.postID = postID;
        this.title = title;
        this.urlImage = urlImage;
        this.userID = userID;
    }

    public Post(String postID, String userID, String title, String urlImage, int like, int comment) {
        this.postID = postID;
        this.userID = userID;
        this.title = title;
        this.urlImage = urlImage;
        this.like = like;
        this.comment = comment;
    }

    public Post(String postID, String userID, String time, String imgUrl, String title, String description, String userImgUrl, int like, int comment, String userName) {
        this.postID = postID;
        this.userID = userID;
        this.time = time;
        this.urlImage = imgUrl;
        this.title = title;
        this.description = description;
        this.userImgUrl = userImgUrl;
        this.like = like;
        this.comment = comment;
        this.userName = userName;
    }

    public String getNumberOfRate() {
        return numberOfRate;
    }

    public void setNumberOfRate(String numberOfRate) {
        this.numberOfRate = numberOfRate;
    }

    public String getDifficult() {
        return difficult;
    }

    public void setDifficult(String difficult) {
        this.difficult = difficult;
    }

    public List<Integer> getListCategory() {
        return listCategory;
    }

    public void setListCategory(List<Integer> listCategory) {
        this.listCategory = listCategory;
    }

    public Post(String numberOfRate, String userName, String numberOfPeople, String postID, String userID, String userImgUrl, String title, String titleLower, String description, String time, String urlImage, String difficult, int like, int comment, String postTime, int countRate, int countView, List<Integer> listCategory, List<Material> materials, List<PostStep> postSteps) {
        this.numberOfRate = numberOfRate;
        this.userName = userName;
        this.numberOfPeople = numberOfPeople;
        this.postID = postID;
        this.userID = userID;
        this.userImgUrl = userImgUrl;
        this.title = title;
        this.titleLower = titleLower;
        this.description = description;
        this.time = time;
        this.urlImage = urlImage;
        this.difficult = difficult;
        this.like = like;
        this.comment = comment;
        this.postTime = postTime;
        this.countRate = countRate;
        this.countView = countView;
        this.listCategory = listCategory;
        this.materials = materials;
        this.postSteps = postSteps;
    }

    public Post(String difficult, String numberOfRate, String userName, String postTime, String numberOfPeople, String postID, String userID, String userImgUrl, String title, String description, String time, String imgUrl, int like, int commentNumber, List<Material> materials, List<PostStep> postSteps) {
        this.numberOfRate = numberOfRate;
        this.difficult = difficult;
        this.userName = userName;
        this.numberOfPeople = numberOfPeople;
        this.postID = postID;
        this.userID = userID;
        this.userImgUrl = userImgUrl;
        this.title = title;
        this.description = description;
        this.time = time;
        this.urlImage = imgUrl;
        this.like = like;
        this.comment = commentNumber;
        this.materials = materials;
        this.postSteps = postSteps;
        this.postTime = postTime;
    }

    public Post(String postID, String userID, String urlImage) {
        this.postID = postID;
        this.userID = userID;
        this.urlImage = urlImage;
    }

    public Post(String postID, String userID, String time, String imgUrl, String title, String titleLower,
                String description, String userImgUrl, int like, int comment) {

        this.postID = postID;
        this.userID = userID;
        this.time = time;
        this.urlImage = imgUrl;
        this.title = title;
        this.titleLower = titleLower;
        this.description = description;
        this.like = like;
        this.comment = comment;
        this.userImgUrl = userImgUrl;
    }

    public Post() {
    }

    public Post(String title, String imgUrl) {
        this.urlImage = imgUrl;
        this.title = title;
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

    public String getTitleLower() {
        return titleLower;
    }

    public void setTitleLower(String titleLower) {
        this.titleLower = titleLower;
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

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
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

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }


    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public List<PostStep> getPostSteps() {
        return postSteps;
    }

    public void setPostSteps(List<PostStep> postSteps) {
        this.postSteps = postSteps;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(String numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
