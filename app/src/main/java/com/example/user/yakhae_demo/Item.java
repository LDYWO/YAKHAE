package com.example.user.yakhae_demo;


public class Item {
    int image;
    String title;
    String drugtype;
    String review;

    int getImage() {
        return this.image;
    }
    String getTitle() {
        return this.title;
    }
    String getDrugType() {
        return this.drugtype;
    }
    String getReview() {
        return this.review;
    }

    Item(int image, String title,String drugtype,String review) {
        this.image = image;
        this.title = title;
        this.drugtype = drugtype;
        this.review = review;
    }
}
