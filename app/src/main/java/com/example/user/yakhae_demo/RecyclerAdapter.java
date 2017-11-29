package com.example.user.yakhae_demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends BaseAdapter {

    ArrayList<Item> items =new ArrayList<Item>();

    public void addItem(String title, String drug_type, String posts) {
        Item item = new Item();

        item.setTitle(title);
        item.setDrugtype(drug_type);
        item.setReview(posts);

        items.add(item);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
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
            convertView = inflater.inflate(R.layout.item_cardview, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView drug_type_cardview = (TextView) convertView.findViewById(R.id.drug_type_cardview);
        TextView review_cardview = (TextView) convertView.findViewById(R.id.review_cardview);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        Item item = items.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        title.setText(item.getTitle());
        drug_type_cardview.setText(item.getDrugType());
        review_cardview.setText(item.getReview());

        return convertView;
    }
}
