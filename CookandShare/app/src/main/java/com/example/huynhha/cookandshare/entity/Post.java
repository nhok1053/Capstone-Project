package com.example.huynhha.cookandshare.entity;


import java.util.ArrayList;
import java.util.List;

public class Post {
    
    private int numberOfRate;
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
    private List<Material> materials;
    private List<PostStep> postSteps;

    public int getNumberOfRate() {
        return numberOfRate;
    }

    public void setNumberOfRate(int numberOfRate) {
        this.numberOfRate = numberOfRate;
    }
    public String getDifficult() {
        return difficult;
    }

    public void setDifficult(String difficult) {
        this.difficult = difficult;
    }

    public Post(String difficult,int numberOfRate, String userName, String postTime, String numberOfPeople, String postID, String userID, String userImgUrl, String title, String description, String time, String imgUrl, int like, int commentNumber, List<Material> materials, List<PostStep> postSteps) {
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

    public Post(String postID, String userID, String time, String imgUrl, String title,
                String description, String userImgUrl, int like, int comment) {
        this.postID = postID;
        this.userID = userID;
        this.time = time;
        this.urlImage = imgUrl;
        this.title = title;
        this.description = description;
        this.like = like;
        this.comment = comment;
        this.userImgUrl = userImgUrl;
    }

    public Post(String postID, String userID, String time, String imgUrl, String title,String titleLower,
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

    public static List<Post> posts = new ArrayList<>();
    public static List<Post> topRecipes = new ArrayList<>();

    public static List<Post> initDataToTopRecipe() {
        topRecipes.add(new Post("Banh Bot Loc", "https://scontent.fhan2-2.fna.fbcdn.net/v/t1.0-9/34319178_1664842336962454_673184661349859328_n.jpg?_nc_cat=0&_nc_eui2=AeG8gWu4TT7L3xcK0_xB9bDNSkprXHeC1hWBi6Dw5w13Db-0Rk_21KmPDL8APC6BI2hkV3oTKST_KjQEnZku_CyuH4_XwfLNavUa3czN1ZE1vA&oh=a9b04608c8f493b9166d0aa1059b6547&oe=5B769790"));
        topRecipes.add(new Post("Banh Chocopie", "https://scontent.fhan2-2.fna.fbcdn.net/v/t1.0-9/26907408_1833516643436717_8447313374020520690_n.jpg?_nc_cat=0&_nc_eui2=AeFzWuXvPN_RsFL9zS6cooVCR_kB1uMCAjVmtRDHVAVbqgOQJUkc4vfso83LTebvKbq4NVA5_pfl5wdJwNwsXYp42sBpz5ppIb4CZaFo6EFxvw&oh=bd3a3e799e9a323bda1f4180aed7341c&oe=5B7DD0A6"));
        topRecipes.add(new Post("Nuoc ngot 7up", "https://scontent.fhan2-2.fna.fbcdn.net/v/t1.0-9/26907408_1833516643436717_8447313374020520690_n.jpg?_nc_cat=0&_nc_eui2=AeFzWuXvPN_RsFL9zS6cooVCR_kB1uMCAjVmtRDHVAVbqgOQJUkc4vfso83LTebvKbq4NVA5_pfl5wdJwNwsXYp42sBpz5ppIb4CZaFo6EFxvw&oh=bd3a3e799e9a323bda1f4180aed7341c&oe=5B7DD0A6"));
        topRecipes.add(new Post("Com Simdo", "https://scontent.fhan2-2.fna.fbcdn.net/v/t1.0-9/32482760_1221730557959846_4061532885704769536_n.jpg?_nc_cat=0&_nc_eui2=AeGORaAcIaDue9bRk2OfKESFOQkCS2Yq6RETyMSZ2qu7XPrKDMxf0HLoqRA4d55NXjQ8SHxBPe3ThnsZra6hrI4VHRsJ6LonP5S4ZRk5PAroqA&oh=95eeec6dfb644f3cf122fb9335db5bf8&oe=5BAEE62F"));
        topRecipes.add(new Post("Com Quang Anh", "https://scontent.fhan2-2.fna.fbcdn.net/v/t1.0-9/16807703_694260300733844_7535017457684269787_n.jpg?_nc_cat=0&_nc_eui2=AeHKUH97g-Ljw8yS38xEDuCCOWEFjM-s3DEHf7UrTjJMkK9ur0_YSKyeNm2I2OUqvGK2_Swb0jiZ5vcMxRxr20-fYFEOsNvyn5mhPIcIyyw2Bg&oh=2ce5aba0511fb9681f91872b8523a495&oe=5B77E320"));
        topRecipes.add(new Post("Com Daily", "https://scontent.fhan2-2.fna.fbcdn.net/v/t1.0-9/32266817_1640435629403125_2580005151267880960_n.jpg?_nc_cat=0&_nc_eui2=AeF22n98RxaiMOBYKYVrGWKlcSjizGZttQhoQ4ZpV2p6rZ0ECKs0scIf9a7UuSJgiqW2NtNPqs0KQyZ-1GPzsP_jf_qYhtCJS6UGInl5rwaJgA&oh=c5f7e6a41c8a1cc5bb27c679bd02275e&oe=5BAAF8E2"));
        topRecipes.add(new Post("Bun HNP", "https://scontent.fhan2-2.fna.fbcdn.net/v/t1.0-9/32156189_942555652592909_4170791957452292096_n.jpg?_nc_cat=0&_nc_eui2=AeHSuJx5xxePv9IyEK5FnU3o-e2BlqDmMfnhhCVj71klbO91bHVuuqy8guwkvd2gvYaG8bTfEozj_GNJyH5jmM0W5R9rXHSCCqDL0jEkwaGMDg&oh=c1c36cadac0a43e7aeba65a0e17c27d0&oe=5BAB3EF1"));
        return topRecipes;
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

    public String getTitleLower(){ return titleLower; }

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
