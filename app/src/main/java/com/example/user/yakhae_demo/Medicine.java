package com.example.user.yakhae_demo;

public class Medicine {

    public String item_name;
    public String entp_name;
    public String induty;
    public String spclty_pblc;
    public String prduct_type;
    public String item_ingr_name;
    public String cancel_name;
    public String chart;
    public String item_image;

    public Medicine() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Medicine(String item_name, String entp_name,String induty,String spclty_pblc, String prduct_type,String item_ingr_name,String cancel_name,String chart,String item_image) {
        this.item_name = item_name;
        this.entp_name = entp_name;
        this.induty = induty;
        this.spclty_pblc = spclty_pblc;
        this.prduct_type = prduct_type;
        this.item_ingr_name = item_ingr_name;
        this.cancel_name = cancel_name;
        this.chart = chart;
        this.item_image =item_image;
    }

    public void setItem_name(String item_name){
        this.item_name = item_name;
    }

    public void setEntp_name(String entp_name){
        this.entp_name = entp_name;
    }

    public void   setInduty(String induty){
        this.induty = induty;
    }

    public void   setSpclty_pblc(String spclty_pblc){
        this.spclty_pblc = spclty_pblc;
    }

    public void   setPrduct_type(String prduct_type){
        this.prduct_type = prduct_type;
    }

    public void   setItem_ingr_name(String item_ingr_name){
        this.item_ingr_name = item_ingr_name;
    }

    public void   setCancel_name(String cancel_name){
        this.cancel_name = cancel_name;
    }

    public void   setChart(String chart){
        this.chart = chart;
    }

    public void   setItem_image(String item_image){
        this.item_image = item_image;
    }

    public  String getItem_name(){
        return this.item_name;
    }
    public  String getEntp_name(){
        return this.entp_name;
    }
    public  String getInduty(){
        return this.induty;
    }
    public  String getSpclty_pblc(){
        return this.spclty_pblc;
    }
    public  String getPrduct_type(){
        return this.prduct_type;
    }
    public  String getItem_ingr_name(){
        return this.item_ingr_name;
    }
    public  String getCancel_name(){
        return this.cancel_name;
    }
    public  String getChart(){
        return this.chart;
    }
    public  String getItem_image(){
        return this.item_image;
    }
}
