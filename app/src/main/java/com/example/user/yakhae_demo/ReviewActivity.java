package com.example.user.yakhae_demo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReviewActivity extends AppCompatActivity implements View.OnClickListener {

    String company_name, medicine_name, userID, user_gender, user_age, user_type, using_date, good_review, bad_review, drug_index, drug_image, Uid, drug_type, drug_category, drug_ingredient;
    float rating;
    TextView User_name, User_Age, User_Gender, User_Type, Company_name, Drug_name, Drug_type, Drug_category, Drug_ingredient, Good_review, Bad_review;
    EditText Comment_Text;
    ListView  Comment_List;
    Button Using_date, Send_button;
    RatingBar RatingBar;
    ImageView DrugImageView;
    DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference("users");
    DatabaseReference mReviewDatabase = FirebaseDatabase.getInstance().getReference("reviews");
    DatabaseReference mDrugDatabase = FirebaseDatabase.getInstance().getReference("0").child("medicine");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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
        User_Type = (TextView)findViewById(R.id.user_type);
        Drug_type = (TextView)findViewById(R.id.drug_type);
        Drug_category = (TextView)findViewById(R.id.drug_category);
        Drug_ingredient = (TextView)findViewById(R.id.main_ingredient);
        Good_review = (TextView)findViewById(R.id.good_review_edittext);
        Bad_review = (TextView)findViewById(R.id.bad_review_edittext);
        Comment_Text = (EditText)findViewById(R.id.send_message);
        Comment_List = (ListView)findViewById(R.id.comment_list);
        Using_date = (Button)findViewById(R.id.using_date_button);
        Send_button = (Button)findViewById(R.id.send_button);
        RatingBar = (RatingBar)findViewById(R.id.ratingbar);
        DrugImageView = (ImageView)findViewById(R.id.drug_image);

        int color = Color.parseColor("#b8babc");
        Comment_Text.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        Send_button.setOnClickListener(this);

        Intent intent = getIntent();

        Log.e("review_intent::",intent.getExtras().getString("drug_company").toString());

        final String drug_image = intent.getStringExtra("drug_image");
        final String drug_index = intent.getStringExtra("drug_index").toString();
        final String company_name = intent.getStringExtra("drug_company").toString();
        final String medicine_name = intent.getStringExtra("drug_name").toString();
        final String Uid = intent.getStringExtra("Uid").toString();
        final String userID = intent.getStringExtra("user_name").toString();
        final float rating = Float.parseFloat(intent.getStringExtra("rating").toString());
        final String good_review = intent.getStringExtra("advantage").toString();
        final String bad_review = intent.getStringExtra("disadvantage").toString();

        Company_name.setText(intent.getExtras().getString("drug_company").toString());
        Drug_name.setText(intent.getExtras().getString("drug_name").toString());
        User_name.setText(intent.getExtras().getString("user_name").toString());
        Good_review.setText(intent.getExtras().getString("advantage").toString());
        Bad_review.setText(intent.getExtras().getString("disadvantage").toString());
        RatingBar.setRating(Float.valueOf(intent.getExtras().getString("rating").toString()));

        Glide.with(this).load(drug_image).into(DrugImageView);

        mUserDatabase.child(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user_gender = dataSnapshot.child("gender").getValue().toString();
                user_age = dataSnapshot.child("age").getValue().toString();
                user_type = dataSnapshot.child("type").getValue().toString();


                User_Gender.setText(user_gender);
                User_Age.setText(user_age);
                User_Type.setText(user_type);
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

        mReviewDatabase.child(drug_index).child(Uid).child("comment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.removeItem();

                Log.e("Dialog dataSnapshot::",dataSnapshot.getKey().toString());

                Iterable<DataSnapshot> childcontact = dataSnapshot.getChildren();
                for (DataSnapshot contact:childcontact){
                    Log.e("Dialog contact::",contact.getKey().toString());
                    String writer = contact.child("userNickname").getValue().toString();
                    String content = contact.child("content").getValue().toString();
                    String date = contact.child("comment_date").getValue().toString();
                    adapter.addItem(writer, content, date);
                    Comment_List.setAdapter(adapter);
                }
                setListViewHeightBasedOnChildren(Comment_List);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    CommentAdapter adapter = new CommentAdapter();

    private void createComment(String commentUserID,String commentUserNickname, String commentContent,String commentDate){
        Comment comment = new Comment(commentUserID,commentUserNickname, commentContent, commentDate);
        mReviewDatabase.child(drug_index).child(Uid).child("comment").push().setValue(comment);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_button:{
                final String commentUserID = user.getUid();

                mUserDatabase.child(commentUserID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String commentUserNickname = dataSnapshot.child("nickname").getValue().toString();
                        String commentContent = Comment_Text.getText().toString().trim();

                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd   aa hh:mm:ss");
                        String commentDate = sdf.format(date);
                        Toast.makeText(ReviewActivity.this,"댓글이 등록 되었습니다.", Toast.LENGTH_SHORT).show();
                        createComment(commentUserID,commentUserNickname,commentContent,commentDate);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                adapter.removeItem();
            }
            break;
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        CommentAdapter listAdapter = (CommentAdapter) listView.getAdapter();
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
            totalHeight += 180;
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight;
        listView.setLayoutParams(params);

        listView.requestLayout();
    }

}
