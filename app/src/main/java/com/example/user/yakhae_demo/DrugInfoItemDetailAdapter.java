package com.example.user.yakhae_demo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class DrugInfoItemDetailAdapter extends BaseAdapter{

    private ArrayList<DrugInfoItem> drugInfoItemsList = new ArrayList<DrugInfoItem>();

    public DrugInfoItemDetailAdapter(){}

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
            convertView = inflater.inflate(R.layout.drug_info_detail, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        //ImageView drugImageView = (ImageView) convertView.findViewById(R.id.drug_image);
        TextView drugTypeTextView = (TextView) convertView.findViewById(R.id.drug_type_textview);
        TextView drugCategoryTextView = (TextView) convertView.findViewById(R.id.drug_category_textview);
        TextView drugMainIngredientTextView = (TextView) convertView.findViewById(R.id.ingredient_textview);
        TextView drugTabooTextView = (TextView) convertView.findViewById(R.id.taboo_textview);


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        DrugInfoItem drugInfoItem = drugInfoItemsList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        //drugImageView.setImageDrawable(drugInfoItem.getDrug_image());
        drugTypeTextView.setText("일반의약품/전문의약품: "+drugInfoItem.getDrug_type());
        drugCategoryTextView.setText("약품 분류: "+drugInfoItem.getDrug_category());
        drugMainIngredientTextView.setText("성분 구성: "+drugInfoItem.getMain_ingredient());
        drugTabooTextView.setText("금기 사항: "+drugInfoItem.getTaboo());

        return convertView;
    }

    public void addItem(String type, String category, String ingre, String taboo) {
        DrugInfoItem item = new DrugInfoItem();

        item.setDrug_type(type);
        item.setDrug_category(category);
        item.setMain_ingredient(ingre);
        item.setTaboo(taboo);

        drugInfoItemsList.add(item);
    }

}
