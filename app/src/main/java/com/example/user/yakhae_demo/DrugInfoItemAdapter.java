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

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class DrugInfoItemAdapter extends BaseAdapter{

    private ArrayList<DrugInfoItem> drugInfoItemsList = new ArrayList<DrugInfoItem>();
    private String htmlPageUrl = "http://terms.naver.com/medicineSearch.nhn?mode=nameSearch&query=";
    private ArrayList<String> drugURL = new ArrayList<String>();
    private ArrayList<String> drugtitle = new ArrayList<String>();


    ImageView drugImageView;
    String encodeResult;
    Bitmap bm;

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
        drugImageView = (ImageView) convertView.findViewById(R.id.drug_image);
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
        if(drugInfoItem.getDrug_image()!="NA")
            Glide.with(context).load(drugInfoItem.getDrug_image()).into(drugImageView);


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

    public void addItem(String drug_index, String img, String com, String name,String type,String category, String ingre, String taboo, Float rating,String search) {
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
        item.setSearch(search);

        drugInfoItemsList.add(item);
    }

    public void setTaboo(String taboo,String ingr){
        for(int i=0;i<drugInfoItemsList.size();i++){
            if(drugInfoItemsList.get(i).getMain_ingredient().contains(ingr)){
                drugInfoItemsList.get(i).setTaboo(taboo);
            }
        }
    }

    private class SetImage extends AsyncTask<Void, Void, Void> {
        ArrayList<Integer> integerArrayList = new ArrayList<Integer>();
        int item_index;
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                for(int i=0;i<drugInfoItemsList.size();i++){
                    for(int j=0;j<drugtitle.size();i++){
                            Log.e("setimage::",drugInfoItemsList.get(i).getDrug_name().toString()+", "+drugtitle.get(j).toString());
                            item_index = i;
                            drugInfoItemsList.get(i).setDrug_image(drugURL.get(i).toString());
                            URL url = new URL(drugURL.get(i).toString());
                            URLConnection conn = url.openConnection();
                            conn.setDoInput(true);
                            conn.connect();
                            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                            bm = BitmapFactory.decodeStream(bis);
                            bis.close();
                            drugInfoItemsList.get(i).setBitmap(bm);
                            integerArrayList.add(item_index);
                        }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            for(int i=0;i<integerArrayList.size();i++){
                drugImageView.setImageBitmap(drugInfoItemsList.get(integerArrayList.get(i).intValue()).getBitmap());
            }
        }
    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                for(int i=0;i<drugInfoItemsList.size();i++) {
                    try {
                        encodeResult = URLEncoder.encode(drugInfoItemsList.get(i).getSearch(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    htmlPageUrl = htmlPageUrl + encodeResult;

                    Document doc = Jsoup.connect(htmlPageUrl).get();
                    Elements links = doc.select("ul.content_list div.thumb_area div.image_area img");
                    Elements titles = doc.select("div.info_area a strong");

                    String tit, url;

                    for (Element link : links) {
                        url = link.attr("data-src");
                        url = url.replace("null", "");
                        url = url.replaceAll("\\p{Z}", "");
                        drugURL.add(url);
                    }

                    for (Element title : titles) {
                        tit = title.text();
                        tit = tit.replace("null", "");
                        tit = tit.replace("mg", "");
                        tit = tit.replaceAll("\\[(.*?)\\]", "");
                        tit = tit.replaceAll("\\p{Z}", "");
                        drugtitle.add(tit);
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            SetImage setImage = new SetImage();
            setImage.execute();
        }
    }

}
