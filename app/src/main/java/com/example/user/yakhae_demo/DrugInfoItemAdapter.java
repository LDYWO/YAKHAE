package com.example.user.yakhae_demo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DrugInfoItemAdapter extends BaseAdapter{

    ArrayList<DrugInfoItem> drugInfoItemsList = new ArrayList<DrugInfoItem>();


    public DrugInfoItemAdapter(){}

    @Override
    public int getCount() {
        return drugInfoItemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return drugInfoItemsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.drug_info, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView drugImageView = (ImageView) convertView.findViewById(R.id.drug_image);
        TextView drugCompanyTextView = (TextView) convertView.findViewById(R.id.drug_company);
        TextView drugNameTextView = (TextView) convertView.findViewById(R.id.drug_name);
        TextView drugTypeTextView = (TextView) convertView.findViewById(R.id.drug_type);
        TextView drugCategoryTextView = (TextView) convertView.findViewById(R.id.drug_category);
        TextView drugMainIngredientTextView = (TextView) convertView.findViewById(R.id.main_ingredient);
        TextView drugTabooTextView = (TextView) convertView.findViewById(R.id.taboo);
        RatingBar drugRatingBar = (RatingBar) convertView.findViewById(R.id.ratingbar);
        TextView drugRatingNum = (TextView) convertView.findViewById(R.id.rating_num);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        DrugInfoItem drugInfoItem = drugInfoItemsList.get(position);

        Log.e("drugURL:;",drugInfoItem.getDrug_image());
        Glide.with(context).load(drugInfoItem.getDrug_image()).into(drugImageView);

        String drug_company = drugInfoItem.getDrug_company();
        String drug_name = drugInfoItem.getDrug_name();
        String drug_type = drugInfoItem.getDrug_type();
        String drug_category = drugInfoItem.getDrug_category();
        String drug_ingr = drugInfoItem.getMain_ingredient();
        String drug_taboo = drugInfoItem.getTaboo();

        drug_taboo=drug_taboo.trim().replaceAll(",","");
        drug_taboo=drug_taboo.trim().replaceAll("NA","");

        if(drug_name.contains("(") && drug_name.length() > 25) {
            int index = drug_name.indexOf("(");
            drug_name = drug_name.trim().substring(0,index)+"...";
        }
        else if(drug_name.length() > 15){
            drug_name = drug_name.trim().substring(0,15)+"...";
        }
        if(drug_name.contains("\n")){
            int index = drug_name.indexOf("\n");
            drug_name = drug_name.trim().substring(0,index)+"...";
        }

        if(drug_ingr.length() > 13 ) {
            drug_ingr = drug_ingr.substring(0,13) + "...";
        }
        if(drug_category.trim().contains("]")) {
            int index = drug_category.indexOf("]");
            drug_category = drug_category.substring(index+1,drug_category.length());
        }

        // 아이템 내 각 위젯에 데이터 반영
        drugCompanyTextView.setText(drug_company);
        drugNameTextView.setText(drug_name.trim());
        drugTypeTextView.setText(drug_type);
        drugCategoryTextView.setText(drug_category);
        drugMainIngredientTextView.setText(drug_ingr);
        drugTabooTextView.setText(drug_taboo);

        drugRatingBar.setRating(drugInfoItem.getRating());
        drugRatingNum.setText(drugInfoItem.getRating_number());

        return convertView;
    }

    public void addItem(String drug_index, String img, String com, String name,String type,String category, String ingre, String taboo, Float rating) {
        DrugInfoItem item = new DrugInfoItem();

        item.setDrug_index(drug_index);
        item.setDrug_image(img);
        item.setDrug_company(com);
        item.setDrug_name(name);
        item.setDrug_type(type);
        item.setDrug_category(category);
        item.setMain_ingredient(ingre);
        item.setTaboo(taboo);
        item.setRating(rating);
        item.setRating_number(rating);

        drugInfoItemsList.add(item);
    }

    public void initTaboo(){
        for(int i=0;i<drugInfoItemsList.size();i++){
            drugInfoItemsList.get(i).setTaboo("없음");
            drugInfoItemsList.get(i).setProhibited_content("없음");
        }
    }
    public void setTaboo(String taboo,String ingr,String prohibit){
        for(int i=0;i<drugInfoItemsList.size();i++){
            if(drugInfoItemsList.get(i).getMain_ingredient().contains(ingr)){
                drugInfoItemsList.get(i).setTaboo(taboo);
                drugInfoItemsList.get(i).setProhibited_content(prohibit);
            }
        }
    }

}
