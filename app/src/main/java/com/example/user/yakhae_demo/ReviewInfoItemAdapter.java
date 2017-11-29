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

public class ReviewInfoItemAdapter extends BaseAdapter{

    private ArrayList<ReviewInfoItem> reviewInfoItemsList = new ArrayList<ReviewInfoItem>();

    public ReviewInfoItemAdapter(){}

    @Override
    public int getCount() {
        return reviewInfoItemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return reviewInfoItemsList.get(position);
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
            convertView = inflater.inflate(R.layout.review_info, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView drugImageView = (ImageView) convertView.findViewById(R.id.drug_image);
        TextView drugCompanyTextView = (TextView) convertView.findViewById(R.id.drug_company);
        TextView drugNameTextView = (TextView) convertView.findViewById(R.id.drug_name);
        TextView userNameTextView = (TextView) convertView.findViewById(R.id.review_writer);
        TextView drugAdvantageTextView = (TextView) convertView.findViewById(R.id.review_advantage_detail);
        TextView drugDisadvantageTextView = (TextView) convertView.findViewById(R.id.review_disadvantage_detail);
        RatingBar drugRatingBar = (RatingBar) convertView.findViewById(R.id.ratingbar);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ReviewInfoItem reviewInfoItem = reviewInfoItemsList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        drugCompanyTextView.setText(reviewInfoItem.getDrug_company());
        drugNameTextView.setText(reviewInfoItem.getDrug_name());
        drugRatingBar.setRating(reviewInfoItem.getRating());
        drugAdvantageTextView.setText(reviewInfoItem.getAdvantage());
        drugDisadvantageTextView.setText(reviewInfoItem.getDisadvantage());
        userNameTextView.setText(reviewInfoItem.getUser_name());

        return convertView;
    }

    public void addItem(String drug_index, String img, String com, String name, String username, String adv, String dis, Float rating) {
        ReviewInfoItem item = new ReviewInfoItem();

        item.setDrug_index(drug_index);
        item.setDrug_image(img);
        item.setDrug_company(com);
        item.setDrug_name(name);
        item.setUser_name(username);
        item.setAdvantage(adv);
        item.setDisadvantage(dis);
        item.setRating(rating);

        reviewInfoItemsList.add(item);
    }

    }

