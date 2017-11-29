package com.example.user.yakhae_demo;

/**
 * Created by LDY on 2017-11-26.
 */

public class ReviewInfoItem {
    private String drug_index;
    private String drug_image;
    private String drug_company;
    private String drug_name;
    private String user_name;
    private Float rating;
    private String rating_number;
    private String advantage;
    private String disadvantage;

    public String getDrug_index() {
        return drug_index;
    }

    public void setDrug_index(String drug_index) {
        this.drug_index = drug_index;
    }

    public String getDrug_image() {
        return drug_image;
    }

    public void setDrug_image(String drug_image) {
        this.drug_image = drug_image;
    }

    public String getDrug_company() {
        return drug_company;
    }

    public void setDrug_company(String drug_company) {
        this.drug_company = drug_company;
    }

    public String getDrug_name() {
        return drug_name;
    }

    public void setDrug_name(String drug_name) {
        this.drug_name = drug_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getRating_number() {
        return rating_number;
    }

    public void setRating_number(Float rate){rating_number=rate.toString();}

    public String getAdvantage() {
        return advantage;
    }

    public void setAdvantage(String advantage) {
        this.advantage = advantage;
    }

    public String getDisadvantage() {
        return disadvantage;
    }

    public void setDisadvantage(String disadvantage) {
        this.disadvantage = disadvantage;
    }
}
