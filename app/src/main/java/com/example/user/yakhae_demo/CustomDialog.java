package com.example.user.yakhae_demo;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

public class CustomDialog extends Dialog implements View.OnClickListener{

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userID,userNickname,comment,comment_date;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("community");

    CommentAdapter adapter = new CommentAdapter();

    private void createComment(String userID,String userNickname, String content,String comment_date){
        Comment comment_content = new Comment(userID,userNickname, content, comment_date);
        DatabaseManager.databaseReference.child("community").child(postID).child("comment").push().setValue(comment_content);
    }

    private static final int LAYOUT = R.layout.dialog_custom;

    private Context context;
    private EditText comment_textinput;
    private Button comment_send_button;
    private ListView comment_listview;
    String postID;

    public CustomDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }
    public CustomDialog(@NonNull Context context,String postID) {
        super(context);
        this.context = context;
        this.postID = postID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        comment_textinput = (EditText) findViewById(R.id.comment_textinput);
        comment_send_button = (Button) findViewById(R.id.comment_send_button);
        comment_listview = (ListView) findViewById(R.id.comment_listview);

        int color = Color.parseColor("#b8babc");
        comment_textinput.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        comment_send_button.setOnClickListener(this);

        FirebaseDatabase.getInstance().getReference("community").child(this.postID).child("comment").addValueEventListener(new ValueEventListener() {
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
                    comment_listview.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comment_send_button:{
                userID = user.getUid();

                mDatabase = FirebaseDatabase.getInstance().getReference("users").child(userID);

                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        userNickname = dataSnapshot.child("nickname").getValue().toString();
                        comment = comment_textinput.getText().toString().trim();

                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd   aa hh:mm:ss");
                        comment_date = sdf.format(date);
                        Toast.makeText(context, "댓글이 등록 되었습니다.", Toast.LENGTH_SHORT).show();
                        createComment(userID,userNickname,comment,comment_date);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                comment_textinput.clearComposingText();
                adapter.removeItem();
            }
                break;
        }
    }
}

