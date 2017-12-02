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

public class FragmentReviewAdapter extends RecyclerView.Adapter<FragmentReviewAdapter.ViewHolder> {
    Context context;
    ArrayList<HashMap<String,String>> noticeList; //공지사항 정보 담겨있음

    public FragmentReviewAdapter(Context context, ArrayList<HashMap<String,String>> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //recycler view에 반복될 아이템 레이아웃 연결
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review,null);
        return new ViewHolder(v);
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    /** 정보 및 이벤트 처리는 이 메소드에서 구현 **/
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final HashMap<String,String> noticeItem = noticeList.get(position);

        Glide.with(context).load(noticeItem.get("drug_image")).into(holder.tv_drug_image);

        holder.tv_writer.setText(noticeItem.get("writer")); //작성자
        holder.tv_drug_title.setText(noticeItem.get("drug_title")); //약 이름
        holder.tv_drug_category.setText(noticeItem.get("drug_category"));//의약품 종류
        holder.tv_good_content.setText(noticeItem.get("good_content")); //좋았던 점
        holder.tv_bad_content.setText(noticeItem.get("bad_content")); //아쉬웠던 점
        holder.tv_drug_company.setText(noticeItem.get("drug_company")); //제약회사
        holder.ratingBar.setRating(Float.valueOf(noticeItem.get("rating")));
        holder.tv_date.setText(noticeItem.get("date"));
        holder.tv_ratingnum.setText(noticeItem.get("rating"));

        holder.comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog dialog = new CustomDialog(context,noticeItem.get("postID"),noticeItem.get("drug_index"),"review");
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.noticeList.size();
    }
    /** item layout 불러오기 **/
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView tv_drug_image;

        TextView tv_writer;
        TextView tv_drug_category;
        TextView tv_drug_title;
        TextView tv_drug_company;
        TextView tv_good_content;
        TextView tv_bad_content;
        TextView tv_date;
        TextView tv_ratingnum;

        RatingBar ratingBar;

        Button comment_button;

        CardView cv;

        public ViewHolder(View v) {
            super(v);
            tv_drug_image = (ImageView)v.findViewById(R.id.drug_image);
            tv_writer = (TextView) v.findViewById(R.id.tv_writer);
            tv_drug_category = (TextView)v.findViewById(R.id.tv_drug_category);
            tv_drug_title = (TextView) v.findViewById(R.id.tv_drug_title);
            tv_drug_company = (TextView) v.findViewById(R.id.tv_drug_company);
            tv_good_content = (TextView) v.findViewById(R.id.tv_good_content);
            tv_bad_content = (TextView) v.findViewById(R.id.tv_bad_content);
            tv_date = (TextView)v.findViewById(R.id.tv_date);
            ratingBar = (RatingBar) v.findViewById(R.id.ratingbar);
            tv_ratingnum =(TextView)v.findViewById(R.id.rating_num);

            comment_button =(Button)v.findViewById(R.id.comment_button);

            cv = (CardView) v.findViewById(R.id.cv);
        }

    }
}
