package com.example.huynhha.cookandshare.entity;

import android.support.annotation.NonNull;

import com.example.huynhha.cookandshare.fragment.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class YouTube {
    private String title;
    private String youtubeUrl;
    private int tipID;

    public YouTube() {
    }

    public YouTube(int tipID,String title, String youtubeUrl) {
        this.title = title;
        this.youtubeUrl = youtubeUrl;
        this.tipID = tipID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYoutubeUrl() {
        return   youtubeUrl  ;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public int getTipID() {
        return tipID;
    }

    public void setTipID(int tipID) {
        this.tipID = tipID;
    }

    public YouTube(String title, String youtubeUrl) {
        this.title = title;
        this.youtubeUrl = youtubeUrl;
    }

    public static YouTube youTubeFix=new YouTube();

    static ArrayList<YouTube> lst = new ArrayList<>();

    public static ArrayList<YouTube> addListYoutube() {
        lst.add(new YouTube("Legend", "P_xFh7XFC_w"));
        lst.add(new YouTube("Power Core", "P_xFh7XFC_w"));
        lst.add(new YouTube("Tyrande", "P_xFh7XFC_w"));
        lst.add(new YouTube("Golden Celebration", "P_xFh7XFC_w"));
        lst.add(new YouTube("Over Watch", "P_xFh7XFC_w"));
        lst.add(new YouTube("Black Rock Mountain", "P_xFh7XFC_w"));
        lst.add(new YouTube("Yogg Saron", "P_xFh7XFC_w"));
        lst.add(new YouTube("The Grand Tournament", "P_xFh7XFC_w"));
        lst.add(new YouTube("Classic", "P_xFh7XFC_w"));
        lst.add(new YouTube("Valentine", "P_xFh7XFC_w"));
        lst.add(new YouTube("Blizzard Entertainment", "P_xFh7XFC_w"));
        lst.add(new YouTube("HOTS", "P_xFh7XFC_w"));
        lst.add(new YouTube("Legacy of the void", "P_xFh7XFC_w"));
        return lst;
    }
}
