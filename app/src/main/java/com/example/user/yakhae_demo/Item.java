package com.example.user.yakhae_demo;


public class Item {
    String title;
    String drugtype;
    String review;

    String getTitle() {
        return this.title;
    }
    String getDrugType() {
        return this.drugtype;
    }
    String getReview() {
        return this.review;
    }

    void setTitle(String title) {this.title = title;}
    void setDrugtype(String drugtype) {
        this.drugtype=drugtype;
    }
    void setReview(String review) {
        this.review=review;
    }

    Item(String title,String drugtype,String review) {
        this.title = title;
        this.drugtype = drugtype;
        this.review = review;
    }

    Item(){}
}
