package com.example.user.yakhae_demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ReviewInfoItemAdapter extends BaseAdapter{

    ArrayList<ReviewInfoItem> reviewInfoItemsList = new ArrayList<ReviewInfoItem>();
    String drugURL;
    ImageView drugImageView;

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
        drugImageView = (ImageView) convertView.findViewById(R.id.drug_image);
        TextView drugCompanyTextView = (TextView) convertView.findViewById(R.id.drug_company);
        TextView drugNameTextView = (TextView) convertView.findViewById(R.id.drug_name);
        TextView userNameTextView = (TextView) convertView.findViewById(R.id.review_writer);
        TextView drugAdvantageTextView = (TextView) convertView.findViewById(R.id.review_advantage_detail);
        TextView drugDisadvantageTextView = (TextView) convertView.findViewById(R.id.review_disadvantage_detail);
        RatingBar drugRatingBar = (RatingBar) convertView.findViewById(R.id.ratingbar);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ReviewInfoItem reviewInfoItem = reviewInfoItemsList.get(position);

        drugURL = reviewInfoItem.getDrug_image();

        SetImage setImage = new SetImage();
        setImage.execute();

        // 아이템 내 각 위젯에 데이터 반영
        drugCompanyTextView.setText(reviewInfoItem.getDrug_company());
        drugNameTextView.setText(reviewInfoItem.getDrug_name());
        drugRatingBar.setRating(reviewInfoItem.getRating());
        drugAdvantageTextView.setText(reviewInfoItem.getAdvantage());
        drugDisadvantageTextView.setText(reviewInfoItem.getDisadvantage());
        userNameTextView.setText(reviewInfoItem.getUser_name());

        return convertView;
    }

    public void addItem(String drug_index, String img, String com, String name, String username, String adv, String dis, String Uid, Float rating) {
        ReviewInfoItem item = new ReviewInfoItem();

        item.setDrug_index(drug_index);
        item.setDrug_image(img);
        item.setDrug_company(com);
        item.setDrug_name(name);
        item.setUser_name(username);
        item.setAdvantage(adv);
        item.setDisadvantage(dis);
        item.setUid(Uid);
        item.setRating(rating);

        reviewInfoItemsList.add(item);
    }

    private class SetImage extends AsyncTask<Void, Void, Void> {
        Bitmap bm;
        @Override
        protected Void doInBackground(Void... voids) {
            try {

                    Log.e("detail::",drugURL);

                    URL url = new URL(drugURL);
                    URLConnection conn = url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                    bm = BitmapFactory.decodeStream(bis);
                    bis.close();


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            drugImageView.setImageBitmap(bm);
        }
    }
}

