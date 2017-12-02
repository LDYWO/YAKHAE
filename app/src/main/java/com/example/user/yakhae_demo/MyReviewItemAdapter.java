package com.example.user.yakhae_demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyReviewItemAdapter extends BaseAdapter{

    ArrayList<MyReviewItem> myReviewItems = new ArrayList<MyReviewItem>();
    public MyReviewItemAdapter(){}

    @Override
    public int getCount() {
        return myReviewItems.size();
    }

    @Override
    public Object getItem(int position) {
        return myReviewItems.get(position);
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
            convertView = inflater.inflate(R.layout.my_review, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView drugImageView = (ImageView) convertView.findViewById(R.id.drug_image);
        TextView drugCompanyTextView = (TextView) convertView.findViewById(R.id.drug_company);
        TextView drugNameTextView = (TextView) convertView.findViewById(R.id.drug_name);
        TextView drugCategoryTextView = (TextView) convertView.findViewById(R.id.drug_category);

        Button UsingDateButton = (Button) convertView.findViewById(R.id.using_date_button);

        TextView drugAdvantageTextView = (TextView) convertView.findViewById(R.id.good_review);
        TextView drugDisadvantageTextView = (TextView) convertView.findViewById(R.id.bad_review);
        RatingBar drugRatingBar = (RatingBar) convertView.findViewById(R.id.ratingbar);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        MyReviewItem myReviewItem = myReviewItems.get(position);

       // Log.e("drugURL:;",reviewInfoItem.getDrug_image());
        Glide.with(context).load(myReviewItem.getDrug_image()).into(drugImageView);

        // 아이템 내 각 위젯에 데이터 반영
        drugCategoryTextView.setText(myReviewItem.getDrug_type());
        drugCompanyTextView.setText(myReviewItem.getDrug_company());
        drugNameTextView.setText(myReviewItem.getDrug_name());

        drugRatingBar.setRating(myReviewItem.getRating());

        drugAdvantageTextView.setText(myReviewItem.getAdvantage());
        drugDisadvantageTextView.setText(myReviewItem.getDisadvantage());

        UsingDateButton.setText(myReviewItem.getUsing_date());



        return convertView;
    }

    public void addItem(String drug_index, String img,
                        String com, String drug_name,
                        String drug_type, String adv,
                        String dis, String Uid, Float rating, String date) {

        MyReviewItem item = new MyReviewItem();

        item.setDrug_index(drug_index);
        item.setDrug_image(img);
        item.setDrug_company(com);
        item.setDrug_name(drug_name);
        item.setDrug_type(drug_type);
        item.setAdvantage(adv);
        item.setDisadvantage(dis);
        item.setUid(Uid);
        item.setRating(rating);
        item.setUsing_date(date);

        myReviewItems.add(item);
    }

}

