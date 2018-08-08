package com.example.huynhha.cookandshare.entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String userID;
    private String imgUrl;
    private String phone;
    private String sex;
    private String firstName;
    private String firstNameLower;
    private String secondName;
    private String dateOfBirth;
    private List<String> postID;

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

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
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

    public User(String userID, String secondName, String imgUrl) {
        this.userID = userID;
        this.imgUrl = imgUrl;
        this.secondName = secondName;
    }

    public User(String userID, String imgUrl, String phone, String sex, String firstName, String firstNameLower, String secondName, String dateOfBirth) {
        this.userID = userID;
        this.imgUrl = imgUrl;
        this.phone = phone;
        this.sex = sex;
        this.firstName = firstName;
        this.firstNameLower = firstNameLower;
        this.secondName = secondName;
        this.dateOfBirth = dateOfBirth;
    }

    public User(String userID, String imgUrl) {
        this.userID = userID;
        this.imgUrl = imgUrl;
    }

    public static List<User> users = new ArrayList<>();

    public User() {
    }

    public static List<User> initDataToTopAttribute() {
        users.add(new User("Ha san", "https://scontent.fhan2-2.fna.fbcdn.net/v/t1.0-9/33605805_1656496591130362_9070517127202471936_n.jpg?_nc_cat=0&_nc_eui2=AeFR81MC2UMzwqUqn0Oi6xoHRAtG-eaQ4k7f53sfzSb_1qRH9FDWaoPd1qOtutOwwI7w8ePGjDlulh5pZhIA_6w7QmfygGA1cr-FNvJMgqF4CQ&oh=715f268ec8e23259b96e90ac79cd4a8d&oe=5BAF23ED"));
        users.add(new User("Hien san", "https://scontent.fhan2-2.fna.fbcdn.net/v/t1.0-9/35026631_1982375735217473_4818617744612130816_n.jpg?_nc_cat=0&_nc_eui2=AeFOgwwd7JL2waACVWVaSGedsCyVne8vIfd-lTbORTV0LOI9sqrUOeB5SXlLf36mwYvEIJe7Et3Y7P2CE0ni86n6FP-6Jb4nLWHzOuN7L9Iuow&oh=07943ffbf47d212aba921861eaeec918&oe=5BB86A05"));
        users.add(new User("Thuan san", "https://scontent.fhan2-2.fna.fbcdn.net/v/t1.0-9/31486618_1213204975479071_7469007352690663294_n.jpg?_nc_cat=0&_nc_eui2=AeEbVPtxsbl2Oac1cIfYktK4wAqn9o_TxklJ-MGJhCZspsD6vphDFRTkkxonDUddl18JBs7aNDT_19DmRg-OA1gf_qdpxuBX3JNfX1TJq9i1fQ&oh=208bd87562aa9806e53562b8f690af42&oe=5BBA9E30"));
        users.add(new User("Luc san", "https://scontent.fhan2-2.fna.fbcdn.net/v/t1.0-9/32169280_910586202434585_5853118161825562624_n.jpg?_nc_cat=0&_nc_eui2=AeGutCY284MEYuGC9jSqKbMnf4qIpeRSeOcs9XTNvY6SfT5DtMeMUOSE3hm1LkKxeC6lc1dqIMQB9QOiklGMLOYjCfcKNTIhvWW4Ok_dc9S-_w&oh=f5b3e8b253e31a717a6a38c12d5b3842&oe=5BB9F3F0"));
        users.add(new User("Minh aoe", "https://scontent.fhan2-2.fna.fbcdn.net/v/t1.0-9/32169494_1174142319394083_2599229145711902720_n.jpg?_nc_cat=0&_nc_eui2=AeHYHI9Z1StUES44whCzJ9k1RjP_VMywHGWz9VgnCYeFdNXJLOsUvPk5r3x4qkaLUP9KCD4LFrmQcRbInVPGXFlmabu8owQ9dl1bYy2AiLvblw&oh=ce646fb994ed5349e4e774b89999b986&oe=5BBC6FDC"));
        users.add(new User("Ragnaros", "https://i.pinimg.com/564x/2b/e6/7d/2be67d3083c4e6a33aaf7c5b814fd3ac.jpg"));
        users.add(new User("Guldan", "https://i.pinimg.com/564x/ec/35/77/ec3577c22217c5fcd0726be0cdc68283.jpg"));
        users.add(new User("Tyrande", "https://i.pinimg.com/564x/e6/53/cd/e653cdf3b829e9741eac2b2b876e8681.jpg"));
        users.add(new User("Edwin", "https://i.pinimg.com/564x/d7/7a/20/d77a2077c0230dbafc62669d368d7863.jpg"));
        return users;
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

