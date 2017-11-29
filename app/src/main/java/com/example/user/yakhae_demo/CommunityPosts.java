package com.example.user.yakhae_demo;

public class CommunityPosts {

    private String UserID;
    private String UserNickname;
    private String UserType;
    private String Posts_title;
    private String Using_drug_type;
    private String Posts;
    private String Posted_date;

    public CommunityPosts(String userID,String userNickname,String userType, String posts_title,String using_drug_type, String posts,String posted_date) {
        UserID = userID;
        UserNickname = userNickname;
        UserType = userType;
        Posts_title = posts_title;
        Using_drug_type = using_drug_type;
        Posts = posts;
        Posted_date=posted_date;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserNickname() {
        return UserNickname;
    }

    public void setUserNickname(String userNickname) {
        UserNickname = userNickname;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getPosts_title() {
        return Posts_title;
    }

    public void setPosts_title(String posts_title) {Posts_title = posts_title;}

    public String getUsing_drug_type() {
        return Using_drug_type;
    }

    public void setUsing_drug_type(String using_drug_type) {Using_drug_type= using_drug_type;}

    public String getPosts() {
        return Posts;
    }

    public void setPosts(String posts) {Posts = posts;}

    public String getPosted_date() {
        return Posted_date;
    }

    public void setPosted_date(String posted_date) {Posted_date = posted_date;}
}
