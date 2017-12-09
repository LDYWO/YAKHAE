package com.example.user.yakhae_demo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteReviewActivity extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String company_name, medicine_name, userID, using_date, good_review, bad_review, drug_index, drug_image, drug_type, write_date;
    float rating, Rating_average;
    TextView Company_name, Medicine_name;
    Button Using_date_button;
    EditText Good_review_edittext, Bad_review_edittext;
    RatingBar RatingBar;
    DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference("users");
    DatabaseReference mReviewDatabase = FirebaseDatabase.getInstance().getReference("reviews");
    DatabaseReference mDrugDatabase = FirebaseDatabase.getInstance().getReference("0").child("medicine");
    String Uid,userNickname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writereview);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_icon);

        //actionbar 객체 가져오기
        ActionBar actionBar = getSupportActionBar();

        Company_name = (TextView)findViewById(R.id.company_name);
        Medicine_name = (TextView)findViewById(R.id.medicine_name);

        Intent intent = getIntent();

        drug_image = intent.getStringExtra("drug_image").toString();
        drug_index = intent.getStringExtra("drug_index").toString();
        Company_name.setText(intent.getStringExtra("drug_company").toString());
        Medicine_name.setText(intent.getStringExtra("drug_name").toString());

        ImageView drugImageView = (ImageView)findViewById(R.id.medicine_image);
        Log.e("drugURL:;",drug_image);
        Glide.with(this).load(drug_image).into(drugImageView);

        RatingBar = (RatingBar)findViewById(R.id.ratingbar);
        RatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });

        //good review text
        Good_review_edittext = (EditText)findViewById(R.id.good_review_edittext);
        Good_review_edittext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        //bad review text
        Bad_review_edittext = (EditText)findViewById(R.id.bad_review_edittext);
        Bad_review_edittext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        Using_date_button = (Button)findViewById(R.id.using_date_button);
        Using_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"하루", "1주", "2주", "3주", "1개월", "2개월", "3개월","6개월","12개월 이상"};

                AlertDialog.Builder builder = new AlertDialog.Builder(WriteReviewActivity.this);

                // 여기서 부터는 알림창의 속성 설정
                builder.setTitle("기간을 선택하세요")        // 제목 설정
                        .setItems(items, new DialogInterface.OnClickListener(){    // 목록 클릭시 설정
                            public void onClick(DialogInterface dialog, int index){
                                Button using_date_button2 = (Button)findViewById(R.id.using_date_button);
                                using_date_button2.setText(items[index]);
                                Toast.makeText(getApplicationContext(), items[index], Toast.LENGTH_SHORT).show();
                            }
                        });

                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                dialog.show();    // 알림창 띄우기

            }
        });

        Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mUserDatabase.child(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userNickname = dataSnapshot.child("nickname").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDrugDatabase.child(drug_index).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                drug_type = dataSnapshot.child("prduct_type").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void createReview(String company_name, String medicine_name, String userID, String using_date, String good_review, String bad_review, String drug_id, String drug_image, String drug_type, String write_date, float rating){
        Review review = new Review(company_name, medicine_name, userID, using_date, good_review, bad_review, drug_id, drug_image, drug_type, write_date, rating);
        DatabaseManager.databaseReference.child("reviews").child(drug_index).child(Uid).setValue(review);
        updateRating();
    }

    private void updateRating(){
        mReviewDatabase.child(drug_index).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> childcontact = dataSnapshot.getChildren();
                long childrenNum = dataSnapshot.getChildrenCount();
                float Sum = 0;
                for (DataSnapshot contact:childcontact){
                    Rating_average = Float.parseFloat(contact.child("rating").getValue().toString());
                    Sum += Rating_average;
                    Log.e("Rating_average::", String.valueOf(Float.parseFloat(contact.child("rating").getValue().toString())));
                }
                Rating_average = Sum/childrenNum;
                Log.e("Rating_average::", String.valueOf(Rating_average));
                mDrugDatabase.child(drug_index).child("rating").setValue(Rating_average);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_review, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:{
                onBackPressed();
                return true;
            }
            case R.id.newPost:{
                company_name = Company_name.getText().toString().trim();
                medicine_name = Medicine_name.getText().toString().trim();
                userID = userNickname;
                using_date = Using_date_button.getText().toString().trim();
                good_review = Good_review_edittext.getText().toString().trim();
                bad_review = Bad_review_edittext.getText().toString().trim();
                rating = RatingBar.getRating();
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd   aa hh:mm:ss");
                write_date = sdf.format(date);
                createReview(company_name, medicine_name, userID, using_date, good_review, bad_review, drug_index, drug_image, drug_type, write_date, rating);
                //updateRating();
                Toast.makeText(WriteReviewActivity.this, "리뷰가 등록 되었습니다.", Toast.LENGTH_SHORT).show();
                onBackPressed();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
