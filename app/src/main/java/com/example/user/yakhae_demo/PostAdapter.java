package com.example.user.yakhae_demo;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    Context context;
    ArrayList<HashMap<String,String>> noticeList; //공지사항 정보 담겨있음

    public PostAdapter(Context context, ArrayList<HashMap<String,String>> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //recycler view에 반복될 아이템 레이아웃 연결
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,null);
        return new ViewHolder(v);
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    /** 정보 및 이벤트 처리는 이 메소드에서 구현 **/
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final HashMap<String,String> noticeItem = noticeList.get(position);

        holder.tv_writer.setText(noticeItem.get("writer")); //작성자
        Log.e("[writer]", noticeItem.get("writer"));
        holder.tv_user_type.setText(noticeItem.get("user_type"));//유저 타입
        holder.tv_title.setText(noticeItem.get("title")); //제목
        holder.tv_drug_type.setText(noticeItem.get("drug_type"));//의약품 타입
        holder.tv_content.setText(noticeItem.get("content")); //내용 일부
        holder.tv_date.setText(noticeItem.get("date")); //작성일

        holder.comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog dialog = new CustomDialog(context,noticeItem.get("postID"),"community");
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
        TextView tv_writer;
        TextView tv_user_type;
        TextView tv_title;
        TextView tv_drug_type;
        TextView tv_content;
        TextView tv_date;

        Button comment_button;

        CardView cv;

        public ViewHolder(View v) {
            super(v);
            tv_writer = (TextView) v.findViewById(R.id.tv_writer);
            tv_user_type = (TextView)v.findViewById(R.id.tv_user_type);
            tv_title = (TextView) v.findViewById(R.id.tv_title);
            tv_drug_type = (TextView) v.findViewById(R.id.tv_drug_type);
            tv_content = (TextView) v.findViewById(R.id.tv_content);
            tv_date = (TextView) v.findViewById(R.id.tv_date);

            comment_button =(Button)v.findViewById(R.id.comment_button);

            cv = (CardView) v.findViewById(R.id.cv);
        }

    }
}
