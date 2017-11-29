package com.example.user.yakhae_demo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WriteReviewActivity extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String company_name, medicine_name, userID, using_date, good_review, bad_review, drug_index, drug_image;
    float rating;
    TextView Company_name, Medicine_name;
    Button Using_date_button;
    EditText Good_review_edittext, Bad_review_edittext;
    RatingBar RatingBar;
    DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference("users");
    String Uid,userNickname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writereview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
    }


    private void createReview(String company_name, String medicine_name, String userID, String using_date, String good_review, String bad_review, String drug_id, String drug_image, float rating){
        Review review = new Review(company_name, medicine_name, userID, using_date, good_review, bad_review, drug_id, drug_image, rating);
        DatabaseManager.databaseReference.child("reviews").child(drug_id).child(userID).setValue(review);
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
                NavUtils.navigateUpFromSameTask(this);
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
                createReview(company_name, medicine_name, userID, using_date, good_review, bad_review, drug_index, drug_image, rating);
                Toast.makeText(WriteReviewActivity.this, "리뷰가 등록 되었습니다.", Toast.LENGTH_SHORT).show();

                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
