package com.example.user.yakhae_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MyPageActivity extends AppCompatActivity {
    String UserID, user_gender,userNickname, user_age, user_type,drug_index, drug_image, drug_type, drug_category;
    TextView User_name, User_Age, User_Gender, User_Type;
    Button settings_button,setting_user_info_button;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference("users");
    DatabaseReference mReviewDatabase = FirebaseDatabase.getInstance().getReference("reviews");

    MyReviewItemAdapter adapter;
    ArrayList<HashMap<String, String>> reviewList;

    private static final String TAG_POSTID = "postID";
    private static final String TAG_WRITER = "writer";
    private static final String TAG_DATE="date";
    private static final String TAG_RATING ="rating";
    private static final String TAG_DRUG_TITLE="drug_title";
    private static final String TAG_DRUG_CATEGORY="drug_category";
    private static final String TAG_GOOD_CONTENT="good_content";
    private static final String TAG_BAD_CONTENT="bad_content";
    private static final String TAG_DRUG_COMPANY="drug_company";
    private static final String TAG_IMAGE="drug_image";
    private static final String TAG_DRUG_INDEX="drug_index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_icon);

        User_name = (TextView)findViewById(R.id.writer_nickname);
        User_Gender = (TextView)findViewById(R.id.user_gender);
        User_Age = (TextView)findViewById(R.id.user_age);
        User_Type = (TextView)findViewById(R.id.user_type);

        //My_review_List = (ListView)findViewById(R.id.my_reviews);

        UserID = user.getUid();
        mUserDatabase.child(UserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userNickname = dataSnapshot.child("nickname").getValue().toString();
                user_gender = dataSnapshot.child("gender").getValue().toString();
                user_age = dataSnapshot.child("age").getValue().toString();
                user_type = dataSnapshot.child("type").getValue().toString();

                User_name.setText(userNickname);
                User_Gender.setText(user_gender);
                User_Age.setText(user_age);
                User_Type.setText(user_type);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.review_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        reviewList = new ArrayList<HashMap<String, String>>();

       mReviewDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> childcontact = dataSnapshot.getChildren();
                for (DataSnapshot contact:childcontact){
                    Log.i("reviews_key:",contact.getKey());
                    Iterable<DataSnapshot> childchildcontact= contact.getChildren();
                    for(DataSnapshot contact2 :childchildcontact){

                        if(contact2.getKey().toString().contains(UserID)){
                            String image;
                            if(contact2.child("drug_image").getValue().toString().trim().contains("NA"))
                                image ="http://drug.mfds.go.kr/html/images/noimages.png";
                            else {
                                image = contact2.child("drug_image").getValue().toString();
                            }
                            String drug_index = contact.getKey();
                            String userID = contact2.getKey();
                            String writer = contact2.child("userID").getValue().toString();
                            String company = contact2.child("company_name").getValue().toString();
                            String title = contact2.child("medicine_name").getValue().toString();
                            String drug_category =contact2.child("drug_type").getValue().toString();
                            String good_content = contact2.child("good_review").getValue().toString();
                            String bad_content = contact2.child("bad_review").getValue().toString();
                            String rating = contact2.child("rating").getValue().toString();
                            String date = contact2.child("using_date").getValue().toString();

                            HashMap<String,String> reviews = new HashMap<String,String>();
                            reviews.put(TAG_DRUG_INDEX,drug_index);
                            reviews.put(TAG_POSTID,userID);
                            reviews.put(TAG_IMAGE,image);
                            reviews.put(TAG_WRITER,writer);
                            reviews.put(TAG_DRUG_COMPANY,company);
                            reviews.put(TAG_DRUG_TITLE,title);
                            reviews.put(TAG_GOOD_CONTENT,good_content);
                            reviews.put(TAG_BAD_CONTENT, bad_content);
                            reviews.put(TAG_RATING,rating);
                            reviews.put(TAG_DRUG_CATEGORY,drug_category);
                            reviews.put(TAG_DATE,date);

                            reviewList.add(reviews);

                            adapter = new MyReviewItemAdapter(getApplicationContext(),reviewList);
                            Log.e("onCreate[reviewList]", "" + reviewList.size());
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        settings_button  = (Button)findViewById(R.id.settings_button);
        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyPageActivity.this, SettingsActivity.class));
            }
        });

        setting_user_info_button=(Button)findViewById(R.id.setting_user_info_button);
        setting_user_info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyPageActivity.this, EditMyInfoActivity.class));
            }
        });
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
