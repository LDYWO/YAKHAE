package com.example.user.yakhae_demo;

/**
 * Created by LDY on 2017-11-26.
 */

public class MyReviewItem {
    private String drug_index;
    private String drug_image;
    private String drug_company;
    private String drug_name;
    private String user_name;
    private Float rating;
    private String advantage;
    private String disadvantage;
    private String Uid;

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getDrug_index() {
        return drug_index;
    }

    public void setDrug_index(String drugindex) {
        drug_index = drugindex;
    }

    public String getDrug_image() {
        return drug_image;
    }

    public void setDrug_image(String drugimage) {
        drug_image = drugimage;
    }

    public String getDrug_company() {
        return drug_company;
    }

    public void setDrug_company(String drugcompany) {
        drug_company = drugcompany;
    }

    public String getDrug_name() {
        return drug_name;
    }

    public void setDrug_name(String drugname) {
        drug_name = drugname;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String username) {
        user_name = username;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float Rating) {
        rating = Rating;
    }

    public String getAdvantage() {
        return advantage;
    }

    public void setAdvantage(String Advantage) {
        advantage = Advantage;
    }

    public String getDisadvantage() {
        return disadvantage;
    }

    public void setDisadvantage(String Disadvantage) {
        disadvantage = Disadvantage;
    }
}
