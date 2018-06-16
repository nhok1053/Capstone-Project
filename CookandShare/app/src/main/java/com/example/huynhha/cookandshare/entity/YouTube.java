package com.example.huynhha.cookandshare.entity;

import java.util.ArrayList;
import java.util.List;

public class YouTube {
    private String title;
    private String youtubeUrl;
    private String userID;
    private String tipID;
    private List<Comment> comments;

    public YouTube(String title, String youtubeUrl, String userID, String tipID, List<Comment> comments) {
        this.title = title;
        this.youtubeUrl = youtubeUrl;
        this.userID = userID;
        this.tipID = tipID;
        this.comments = comments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYoutubeUrl() {
        return "https://img.youtube.com/vi/"+youtubeUrl+"/0.jpg";
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTipID() {
        return tipID;
    }

    public void setTipID(String tipID) {
        this.tipID = tipID;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public YouTube(String title, String youtubeUrl) {
        this.title = title;
        this.youtubeUrl = youtubeUrl;
    }

    static List<YouTube> lst=new ArrayList<>();
    public static List<YouTube> addListYoutube(){
        lst.add(new YouTube("Legend","P_xFh7XFC_w"));
        lst.add(new YouTube("Power Core","P_xFh7XFC_w"));
        lst.add(new YouTube("Tyrande","P_xFh7XFC_w"));
        lst.add(new YouTube("Golden Celebration","P_xFh7XFC_w"));
        lst.add(new YouTube("Over Watch","P_xFh7XFC_w"));
        lst.add(new YouTube("Black Rock Mountain","P_xFh7XFC_w"));
        lst.add(new YouTube("Yogg Saron","P_xFh7XFC_w"));
        lst.add(new YouTube("The Grand Tournament","P_xFh7XFC_w"));
        lst.add(new YouTube("Classic","P_xFh7XFC_w"));
        lst.add(new YouTube("Valentine","P_xFh7XFC_w"));
        lst.add(new YouTube("Blizzard Entertainment","P_xFh7XFC_w"));
        lst.add(new YouTube("HOTS","P_xFh7XFC_w"));
        lst.add(new YouTube("Legacy of the void","P_xFh7XFC_w"));
        return lst;
    }
}
