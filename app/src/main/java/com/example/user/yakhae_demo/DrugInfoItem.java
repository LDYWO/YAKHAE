package com.example.user.yakhae_demo;

/**
 * Created by user on 2017-11-11.
 */

public class DrugInfoItem {
    private String drug_index;
    private String drug_image;
    private String drug_company;
    private String drug_name;
    private String drug_type;
    private String drug_category;
    private String main_ingredient;
    private String taboo;
    private Float rating;
    private String rating_number;
    public void setDrug_index(String drug_index){
        this.drug_index = drug_index;
    }
    public void setDrug_image(String drugImage){
        drug_image = drugImage;
    }
    public void setDrug_company(String drugCompany){
        drug_company = drugCompany;
    }
    public void setDrug_name(String drugName){
        drug_name = drugName;
    }
    public void setDrug_type(String drugType){
        drug_type = drugType;
    }
    public void setDrug_category(String drugCategory){
        drug_category = drugCategory;
    }
    public void setMain_ingredient(String mainIngredient){
        main_ingredient = mainIngredient;
    }
    public void setTaboo(String Taboo){
        taboo = Taboo;
    }
    public void setRating(Float rate){
        rating = rate;
    }
    public void setRating_number(Float rate){rating_number=rate.toString();}

    public String getDrug_index(){return this.drug_index;}
    public String getDrug_image(){return this.drug_image;}
    public String getDrug_company(){
        return this.drug_company;
    }
    public String getDrug_name(){
        return this.drug_name;
    }
    public String getDrug_type(){
        return this.drug_type;
    }
    public String getDrug_category(){
        return this.drug_category;
    }
    public String getMain_ingredient(){
        return this.main_ingredient;
    }
    public String getTaboo(){
        return this.taboo;
    }
    public Float getRating (){
        return this.rating;
    }
    public String getRating_number(){
        return this.rating_number;
    }

}
