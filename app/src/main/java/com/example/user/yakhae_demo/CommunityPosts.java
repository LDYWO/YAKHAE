package com.example.user.yakhae_demo;

public class CommunityPosts {

    private String UserID;
    private String Posts_title;
    private String Using_drug_type;
    private String Posts;

    public CommunityPosts(String userID, String posts_title,String using_drug_type, String posts) {
        UserID = userID;
        Posts_title = posts_title;
        Using_drug_type = using_drug_type;
        Posts = posts;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
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
}
