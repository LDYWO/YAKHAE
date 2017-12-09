package com.example.user.yakhae_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DrugInfoDetailActivity extends AppCompatActivity {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("reviews");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_info_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_icon);

        final Intent drug_intent = getIntent();

        TextView drug_company = (TextView)findViewById(R.id.drug_company);
        TextView drug_name = (TextView)findViewById(R.id.drug_name);
        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingbar);
        TextView rating_num = (TextView)findViewById(R.id.rating_num);

        drug_company.setText("제약회사: "+drug_intent.getStringExtra("drug_company").toString());
        drug_name.setText("의약품: "+drug_intent.getStringExtra("drug_name").toString());
        ratingBar.setRating(Float.parseFloat(drug_intent.getStringExtra("drug_rating")));
        rating_num.setText("평점  "+drug_intent.getStringExtra("drug_rating_num").toString());


        drug_intent.getStringExtra("drug_category").toString();
        drug_intent.getStringExtra("drug_type").toString();
        drug_intent.getStringExtra("drug_main_ingre").toString();
        drug_intent.getStringExtra("drug_taboo").toString();

        final String medicine_name = drug_intent.getStringExtra("drug_name").toString();
        final String company_name = drug_intent.getStringExtra("drug_company").toString();
        final String drug_index = drug_intent.getStringExtra("drug_index").toString();
        final String drug_image = drug_intent.getStringExtra("drug_image").toString();

        ImageView drugImageView = (ImageView)findViewById(R.id.drug_image);
        Log.e("drugURL:;",drug_image);
        Glide.with(this).load(drug_image).into(drugImageView);

        ListView DrugInfolistView;
        DrugInfoItemDetailAdapter DrugInfoItemadapter;

        DrugInfoItemadapter = new DrugInfoItemDetailAdapter();
        DrugInfolistView = (ListView)findViewById(R.id.infor_detail);
        DrugInfoItemadapter.addItem(
                drug_intent.getStringExtra("drug_image").toString(),
                drug_intent.getStringExtra("drug_type").toString(),
                drug_intent.getStringExtra("drug_category").toString(),
                drug_intent.getStringExtra("drug_main_ingre").toString(),
                drug_intent.getStringExtra("drug_taboo").toString(),
                drug_intent.getStringExtra("drug_prohibit").toString());
        DrugInfolistView.setAdapter(DrugInfoItemadapter);

        final ListView ReviewInfolistView;
        final ReviewInfoItemAdapter ReviewInfoItemadapter;

        ReviewInfolistView = (ListView) findViewById(R.id.review_listview);
        ReviewInfoItemadapter = new ReviewInfoItemAdapter();

        mDatabase.child(drug_index).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ReviewInfoItemadapter.reviewInfoItemsList.clear();
                Iterable<DataSnapshot> childcontact = dataSnapshot.getChildren();
                for (DataSnapshot contact : childcontact){
                    if(contact.child("drug_image").getValue().toString().trim().contains("NA")){
                        ReviewInfoItemadapter.addItem(
                                contact.child("drug_id").getValue().toString(),
                                "http://drug.mfds.go.kr/html/images/noimages.png",
                                contact.child("company_name").getValue().toString(),
                                contact.child("medicine_name").getValue().toString(),
                                contact.child("userID").getValue().toString(),
                                contact.child("good_review").getValue().toString(),
                                contact.child("bad_review").getValue().toString(),
                                contact.getKey().toString(),
                                Float.parseFloat(contact.child("rating").getValue().toString()));
                    }

                    else{
                        ReviewInfoItemadapter.addItem(
                                contact.child("drug_id").getValue().toString(),
                                contact.child("drug_image").getValue().toString(),
                                contact.child("company_name").getValue().toString(),
                                contact.child("medicine_name").getValue().toString(),
                                contact.child("userID").getValue().toString(),
                                contact.child("good_review").getValue().toString(),
                                contact.child("bad_review").getValue().toString(),
                                contact.getKey().toString(),
                                Float.parseFloat(contact.child("rating").getValue().toString()));
                        }
                    }

                setListViewHeightBasedOnChildren(ReviewInfolistView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

        ReviewInfolistView.setAdapter(ReviewInfoItemadapter);
        ReviewInfolistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                ReviewInfoItem item = (ReviewInfoItem) parent.getItemAtPosition(position);

                String drug_index = item.getDrug_index();
                String drug_image = item.getDrug_image();
                String drug_company = item.getDrug_company();
                String drug_name = item.getDrug_name();
                String user_name = item.getUser_name();
                Float rating = item.getRating();
                String advantage = item.getAdvantage();
                String disadvantage = item.getDisadvantage();
                String Uid = item.getUid();

                Log.e("review_item::", drug_name);

                Intent reviewintent = new Intent(DrugInfoDetailActivity.this,ReviewActivity.class);
                reviewintent.putExtra("drug_index",drug_index.toString());
                reviewintent.putExtra("drug_image",drug_image.toString());
                reviewintent.putExtra("drug_company",drug_company.toString());
                reviewintent.putExtra("drug_name",drug_name.toString());
                reviewintent.putExtra("user_name",user_name.toString());
                reviewintent.putExtra("rating",rating.toString());
                reviewintent.putExtra("advantage",advantage.toString());
                reviewintent.putExtra("disadvantage",disadvantage.toString());
                reviewintent.putExtra("Uid",Uid.toString());
                startActivity(reviewintent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DrugInfoDetailActivity.this,WriteReviewActivity.class);
                intent.putExtra("drug_image",drug_image);
                intent.putExtra("drug_name",medicine_name);
                intent.putExtra("drug_company",company_name);
                intent.putExtra("drug_index",drug_index);
                /*intent.putExtra("durg_category",drug_intent.getStringExtra("durg_category").toString());
                intent.putExtra("durg_type",drug_intent.getStringExtra("durg_type").toString());
                intent.putExtra("durg_main_ingre",drug_intent.getStringExtra("durg_main_ingre").toString());
                intent.putExtra("durg_taboo",drug_intent.getStringExtra("durg_taboo").toString());*/
                startActivity(intent);
            }
        });
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ReviewInfoItemAdapter listAdapter = (ReviewInfoItemAdapter) listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            //listItem.measure(0, 0);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += 310;
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight;
        listView.setLayoutParams(params);

        listView.requestLayout();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:{
                onBackPressed();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
