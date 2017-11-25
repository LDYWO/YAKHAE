package com.example.user.yakhae_demo;

public class Review {

    private String Company_name;
    private String Medicine_name;
    private String UserID;
    private String Using_date;
    private String Good_review;
    private String Bad_review;
    private String Drug_id;
    private float Rating;

    public Review(String company_name, String medicine_name, String userID, String using_date, String good_review, String bad_review, String drug_id, float rating) {
        Company_name = company_name;
        Medicine_name = medicine_name;
        UserID = userID;
        Using_date = using_date;
        Good_review = good_review;
        Bad_review = bad_review;
        Drug_id = drug_id;
        Rating = rating;
    }

    public String getCompany_name() {
        return Company_name;
    }

    public void setCompany_name(String company_name) {
        Company_name = company_name;
    }

    public String getMedicine_name() {
        return Medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        Medicine_name = medicine_name;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUsing_date() {
        return Using_date;
    }

    public void setUsing_date(String using_date) {
        Using_date = using_date;
    }

    public String getGood_review() {
        return Good_review;
    }

    public void setGood_review(String good_review) {
        Good_review = good_review;
    }

    public String getBad_review() {
        return Bad_review;
    }

    public void setBad_review(String bad_review) {
        Bad_review = bad_review;
    }

    public String getDrug_id() {
        return Drug_id;
    }

    public void setDrug_id(String drug_id) {
        Drug_id = drug_id;
    }

    public float getRating() {
        return Rating;
    }

    public void setRating(float rating) {
        Rating = rating;
    }
}
