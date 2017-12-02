package com.example.user.yakhae_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReviewActivity extends AppCompatActivity {

    String company_name, medicine_name, userID, user_gender, user_age, using_date, good_review, bad_review, drug_index, drug_image, Uid, drug_type, drug_category, drug_ingredient;
    float rating;
    TextView User_name, User_Age, User_Gender, Company_name, Drug_name, Drug_type, Drug_category, Drug_ingredient, Good_review, Bad_review;
    Button Using_date;
    RatingBar RatingBar;
    DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference("users");
    DatabaseReference mReviewDatabase = FirebaseDatabase.getInstance().getReference("reviews");
    DatabaseReference mDrugDatabase = FirebaseDatabase.getInstance().getReference("0").child("medicine");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_icon);
        toolbar.setTitle("리뷰");

        Company_name = (TextView)findViewById(R.id.drug_company);
        Drug_name = (TextView)findViewById(R.id.drug_name);
        User_name = (TextView)findViewById(R.id.writer_nickname);
        User_Gender = (TextView)findViewById(R.id.user_gender);
        User_Age = (TextView)findViewById(R.id.user_age);
        Drug_type = (TextView)findViewById(R.id.drug_type);
        Drug_category = (TextView)findViewById(R.id.drug_category);
        Drug_ingredient = (TextView)findViewById(R.id.main_ingredient);
        Good_review = (TextView)findViewById(R.id.good_review_edittext);
        Bad_review = (TextView)findViewById(R.id.bad_review_edittext);
        Using_date = (Button)findViewById(R.id.using_date_button);
        RatingBar = (RatingBar)findViewById(R.id.ratingbar);

        Intent intent = getIntent();

        Log.e("review_intent::",intent.getExtras().getString("drug_company").toString());

        drug_image = intent.getStringExtra("drug_image");
        drug_index = intent.getStringExtra("drug_index").toString();
        company_name = intent.getStringExtra("drug_company").toString();
        medicine_name = intent.getStringExtra("drug_name").toString();
        Uid = intent.getStringExtra("Uid").toString();
        userID = intent.getStringExtra("user_name").toString();
        rating = Float.parseFloat(intent.getStringExtra("rating").toString());
        good_review = intent.getStringExtra("advantage").toString();
        bad_review = intent.getStringExtra("disadvantage").toString();

        Company_name.setText(intent.getExtras().getString("drug_company").toString());
        Drug_name.setText(intent.getExtras().getString("drug_name").toString());
        User_name.setText(intent.getExtras().getString("user_name").toString());
        Good_review.setText(intent.getExtras().getString("advantage").toString());
        Bad_review.setText(intent.getExtras().getString("disadvantage").toString());
        RatingBar.setRating(Float.valueOf(intent.getExtras().getString("rating").toString()));

        mUserDatabase.child(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user_gender = dataSnapshot.child("gender").getValue().toString();
                user_age = dataSnapshot.child("age").getValue().toString();

                User_Gender.setText(user_gender);
                User_Age.setText(user_age);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDrugDatabase.child(drug_index).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                drug_type = dataSnapshot.child("spclty_pblc").getValue().toString();
                drug_category = dataSnapshot.child("prduct_type").getValue().toString();
                drug_ingredient = dataSnapshot.child("item_ingr_name").getValue().toString();

                Drug_type.setText(drug_type);
                Drug_category.setText(drug_category);
                Drug_ingredient.setText(drug_ingredient);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mReviewDatabase.child(drug_index).child(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                using_date = dataSnapshot.child("using_date").getValue().toString();
                Using_date.setText(using_date);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






    }

}
