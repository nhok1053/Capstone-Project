package com.example.huynhha.cookandshare.entity;


import java.util.ArrayList;
import java.util.List;

public class Post {
    private int numberOfPeople;

    private String postID;
    private String userID;
    private String userImgUrl;
    private String title;
    private String description;
    private String time;
    private String imgUrl;
    private int likeNumber;
    private int commentNumber;
    private List<Material> materials;
    private List<PostStep> postSteps;



    public Post(int numberOfPeople, String postID, String userID, String userImgUrl, String title, String description, String time, String imgUrl, int likeNumber, int commentNumber, List<Material> materials, List<PostStep> postSteps) {
        this.numberOfPeople = numberOfPeople;
        this.postID = postID;
        this.userID = userID;
        this.userImgUrl = userImgUrl;
        this.title = title;
        this.description = description;
        this.time = time;
        this.imgUrl = imgUrl;
        this.likeNumber = likeNumber;
        this.commentNumber = commentNumber;
        this.materials = materials;
        this.postSteps = postSteps;
    }

    public Post(String postID, String userID, String time, String imgUrl, String title,
                String description, String userImgUrl, int like, int comment) {
        this.postID = postID;
        this.userID = userID;
        this.time = time;
        this.imgUrl = imgUrl;
        this.title = title;
        this.description = description;
        this.likeNumber = like;
        this.commentNumber = comment;
        this.userImgUrl = userImgUrl;
    }

    public Post() {
    }

    public Post(String title, String imgUrl) {
        this.imgUrl = imgUrl;
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

    public static List<Post> initData() {
        posts.add(new Post("Post0001", "MiideviL", "6-9-2018", "https://i.pinimg.com/564x/5b/7f/d9/5b7fd93532746ec5918937a2d9134457.jpg", "Recipe 001",
                "Mon dau tien", "https://i.pinimg.com/564x/a8/d4/a3/a8d4a3d0225f5e3def397f257b07a155.jpg", 10, 10));
        posts.add(new Post("Post0001", "MiideviL", "6-9-2018", "http://media-cache-ec0.pinimg.com/1200x/0e/80/07/0e8007ccbb7441c8058573e80b2efd1e.jpg", "Recipe 002",
                "Mon thu hai", "https://i.pinimg.com/564x/05/35/9d/05359d0c37e375e650f30c9798313696.jpg", 10, 10));
        return posts;
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

    public String getUserImgUrl() {
        return userImgUrl;
    }

    public void setUserImgUrl(String userImgUrl) {
        this.userImgUrl = userImgUrl;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public int getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(int commentNumber) {
        this.commentNumber = commentNumber;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }
    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
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
}
