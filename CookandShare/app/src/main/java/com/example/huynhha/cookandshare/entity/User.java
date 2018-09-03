package com.example.huynhha.cookandshare.entity;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    private String userID;
    private String imgUrl;
    private String phone;
    private String sex;
    private String firstName;
    private String firstNameLower;
    private String mail;
    private String dateOfBirth;
    private int postRemoved;

    public int getPostRemoved() {
        return postRemoved;
    }

    public void setPostRemoved(int postRemoved) {
        this.postRemoved = postRemoved;
    }

    public User(String userID, String imgUrl, String phone, String sex, String firstName, String firstNameLower, String mail, String dateOfBirth, int postRemoved, List<String> postID, int countFollow, boolean status, int deletePostNum, Date time) {
        this.userID = userID;
        this.imgUrl = imgUrl;
        this.phone = phone;
        this.sex = sex;
        this.firstName = firstName;
        this.firstNameLower = firstNameLower;
        this.mail = mail;
        this.dateOfBirth = dateOfBirth;
        this.postRemoved = postRemoved;
        this.postID = postID;
        this.countFollow = countFollow;
        this.status = status;
        this.deletePostNum = deletePostNum;
        this.time = time;
    }

    private List<String> postID;
    private int countFollow;
    private boolean status;
    private int deletePostNum;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getDeletePostNum() {
        return deletePostNum;
    }

    public void setDeletePostNum(int deletePostNum) {
        this.deletePostNum = deletePostNum;
    }

    public User(String userID, String imgUrl, String phone, String sex, String firstName, String firstNameLower, String mail, String dateOfBirth, List<String> postID, boolean status, int deletePostNum, Date time) {
        this.userID = userID;
        this.imgUrl = imgUrl;
        this.phone = phone;
        this.sex = sex;
        this.firstName = firstName;
        this.firstNameLower = firstNameLower;
        this.mail = mail;
        this.dateOfBirth = dateOfBirth;
        this.postID = postID;
        this.status = status;
        this.deletePostNum = deletePostNum;
        this.time = time;
    }

    @ServerTimestamp
    Date time;


    public User(String userID, String imgUrl, String phone, String sex, String firstName, String firstNameLower, String mail, String dateOfBirth, List<String> postID, Date time) {
        this.userID = userID;
        this.imgUrl = imgUrl;
        this.phone = phone;
        this.sex = sex;
        this.firstName = firstName;
        this.firstNameLower = firstNameLower;
        this.dateOfBirth = dateOfBirth;
        this.postID = postID;
        this.time = time;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public static List<User> getUsers() {
        return users;
    }

    public static void setUsers(List<User> users) {
        User.users = users;
    }

    public List<String> getPostID() {
        return postID;
    }

    public void setPostID(List<String> postID) {
        this.postID = postID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstNameLower() {
        return firstNameLower;
    }

    public void setFirstNameLower(String firstNameLower) {
        this.firstNameLower = firstNameLower;
    }

    public User(String userID, String firstName, String imgUrl) {
        this.userID = userID;
        this.imgUrl = imgUrl;
        this.firstName = firstName;
    }

    public User(String userID, String imgUrl, String phone, String sex, String firstName, String firstNameLower, String mail, String dateOfBirth) {
        this.userID = userID;
        this.imgUrl = imgUrl;
        this.phone = phone;
        this.sex = sex;
        this.firstName = firstName;
        this.firstNameLower = firstNameLower;
        this.mail = mail;
        this.dateOfBirth = dateOfBirth;
    }

    public User(String userID, String imgUrl,int countFollow) {
        this.userID = userID;
        this.imgUrl = imgUrl;
        this.countFollow=countFollow;
    }

    public int getCountFollow() {
        return countFollow;
    }

    public void setCountFollow(int countFollow) {
        this.countFollow = countFollow;
    }

    public static List<User> users = new ArrayList<>();

    public User() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}

