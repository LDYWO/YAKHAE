package com.example.user.yakhae_demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class DrugInfoItemAdapter extends BaseAdapter{

    private ArrayList<DrugInfoItem> drugInfoItemsList = new ArrayList<DrugInfoItem>();

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

        // 아이템 내 각 위젯에 데이터 반영
        drugCompanyTextView.setText(drugInfoItem.getDrug_company());
        drugNameTextView.setText(drugInfoItem.getDrug_name());
        drugTypeTextView.setText(drugInfoItem.getDrug_type());
        drugCategoryTextView.setText(drugInfoItem.getDrug_category());
        drugMainIngredientTextView.setText(drugInfoItem.getMain_ingredient());
        drugTabooTextView.setText(drugInfoItem.getTaboo());
        drugRatingBar.setRating(drugInfoItem.getRating());
        drugRatingNum.setText(drugInfoItem.getRating_number());

        return convertView;
    }

    public void addItem(String img, String com, String name,String type,String category, String ingre, String taboo, Float rating) {
        DrugInfoItem item = new DrugInfoItem();

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

    public void setTaboo(String taboo,String ingr){
        for(int i=0;i<drugInfoItemsList.size();i++){
            if(drugInfoItemsList.get(i).getMain_ingredient().contains(ingr)){
                drugInfoItemsList.get(i).setTaboo(taboo);
            }
        }
    }

}
