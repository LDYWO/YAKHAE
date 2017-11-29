package com.example.user.yakhae_demo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteCommunityActivity extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userID,userNickname,userType, post_title, using_drug_type, posts,posted_date;
    EditText community_title, community_content;
    Button Using_drug_type;

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("community");

    private void createCommuintyPosts(String userID,String userNickname,String userType, String posts_title,String using_drug_type, String posts,String posted_date){
        CommunityPosts communityposts = new CommunityPosts(userID,userNickname,userType, posts_title,using_drug_type, posts,posted_date);
        DatabaseManager.databaseReference.child("community").push().setValue(communityposts);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writecommunity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_icon);

        //actionbar 객체 가져오기
        ActionBar actionBar = getSupportActionBar();

        community_title = (EditText)findViewById(R.id.community_title);
        community_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
;        //good review text
        community_content = (EditText)findViewById(R.id.community_content);
        community_content.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });


        Using_drug_type = (Button)findViewById(R.id.using_drug_type);
        Using_drug_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"일반의약품", "전문의약품"};

                AlertDialog.Builder builder = new AlertDialog.Builder(WriteCommunityActivity.this);

                // 여기서 부터는 알림창의 속성 설정
                builder.setTitle("의약품 종류를 선택하세요")        // 제목 설정
                        .setItems(items, new DialogInterface.OnClickListener(){    // 목록 클릭시 설정
                            public void onClick(DialogInterface dialog, int index){
                                Button using_date_button2 = (Button)findViewById(R.id.using_drug_type);
                                using_date_button2.setText(items[index]);
                                Toast.makeText(getApplicationContext(), items[index], Toast.LENGTH_SHORT).show();
                            }
                        });

                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                dialog.show();    // 알림창 띄우기

            }
        });


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> Childcontact = dataSnapshot.getChildren();
                for(DataSnapshot contact : Childcontact){
                    Log.i("community_count: ",Long.toString(contact.getChildrenCount()));
                }
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
                NavUtils.navigateUpFromSameTask(this);
                return true;
            }
            case R.id.newPost:{
                userID = user.getUid();

                mDatabase = FirebaseDatabase.getInstance().getReference("users").child(userID);
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        userNickname = dataSnapshot.child("nickname").getValue().toString();
                        userType= dataSnapshot.child("type").getValue().toString();
                        post_title = community_title.getText().toString().trim();
                        using_drug_type = Using_drug_type.getText().toString().trim();
                        posts = community_content.getText().toString().trim();

                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        posted_date = sdf.format(date);

                        createCommuintyPosts(userID,userNickname,userType,post_title, using_drug_type,posts,posted_date);
                        Toast.makeText(WriteCommunityActivity.this, "게시글이 등록 되었습니다.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplication(), MainActivity.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
