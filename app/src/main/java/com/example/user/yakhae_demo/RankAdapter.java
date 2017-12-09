package com.example.user.yakhae_demo;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {
    Context context;
    ArrayList<HashMap<String,String>> noticeList; //공지사항 정보 담겨있음

    public RankAdapter(Context context, ArrayList<HashMap<String,String>> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //recycler view에 반복될 아이템 레이아웃 연결
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking,null);
        return new ViewHolder(v);
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    /** 정보 및 이벤트 처리는 이 메소드에서 구현 **/
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final HashMap<String,String> noticeItem = noticeList.get(position);

        Glide.with(context).load(noticeItem.get("drug_image")).into(holder.tv_drug_image);

        String drug_company = noticeItem.get("drug_company");
        String drug_name = noticeItem.get("drug_title");
        String drug_category = noticeItem.get("drug_category");

        if(drug_name.length() > 10){
            drug_name = drug_name.trim().substring(0,9)+"...";
        }
        if(drug_name.contains("\n")){
            int index = drug_name.indexOf("\n");
            drug_name = drug_name.trim().substring(0,index)+"...";
        }
        if(drug_category.trim().contains("]")) {
            int index = drug_category.indexOf("]");
            drug_category = drug_category.substring(index+1,drug_category.length());
        }

        holder.Ranking.setText(position + 1 +"위");
        holder.tv_drug_title.setText(drug_name); //약 이름
        holder.tv_drug_category.setText(drug_category);//의약품 종류
        holder.tv_drug_company.setText(drug_company); //제약회사
        holder.ratingBar.setRating(Float.valueOf(noticeItem.get("rating")));
        holder.tv_ratingnum.setText(noticeItem.get("rating"));

    }

    @Override
    public int getItemCount() {
        return this.noticeList.size();
    }
    /** item layout 불러오기 **/
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView tv_drug_image;

        TextView tv_drug_category;
        TextView tv_drug_title;
        TextView tv_drug_company;
        TextView tv_ratingnum;

        RatingBar ratingBar;

        Button Ranking;

        CardView cv;

        public ViewHolder(View v) {
            super(v);
            tv_drug_image = (ImageView)v.findViewById(R.id.drug_image);
            tv_drug_category = (TextView)v.findViewById(R.id.tv_drug_category);
            tv_drug_title = (TextView) v.findViewById(R.id.tv_drug_title);
            tv_drug_company = (TextView) v.findViewById(R.id.tv_drug_company);
            ratingBar = (RatingBar) v.findViewById(R.id.ratingbar);
            tv_ratingnum =(TextView)v.findViewById(R.id.rating_num);
            Ranking = (Button)v.findViewById(R.id.ranking);

            cv = (CardView) v.findViewById(R.id.cv);
        }

    }
}
