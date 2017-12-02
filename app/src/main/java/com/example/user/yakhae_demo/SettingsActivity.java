package com.example.user.yakhae_demo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsActivity extends AppCompatActivity {

    Button log_out_button,leave_button;

    String Uid;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Uid = user.getUid();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_icon);

        log_out_button = (Button) findViewById(R.id.log_out_button);
        leave_button = (Button)findViewById(R.id.leave_button);

        log_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
            }
        });

        leave_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertdialog = new AlertDialog.Builder(SettingsActivity.this);
                //다이얼로그의 내용을 설정합니다.
                alertdialog.setTitle("약회 탈퇴");
                alertdialog.setMessage("탈퇴 하시겠습니까?");

                //확인 버튼
                alertdialog.setPositiveButton("탈퇴", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LeaveAsynkTask leaveAsynkTask = new LeaveAsynkTask();
                        leaveAsynkTask.execute();
                    }
                });

                //취소 버튼
                alertdialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //취소 버튼이 눌렸을 때 토스트를 띄워줍니다.
                        Toast.makeText(SettingsActivity.this, "취소", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alert = alertdialog.create();
                alert.show();

            }
        });

    }

    class LeaveAsynkTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            //mUserDatabase.child(Uid).getRef().removeValue();
            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.e("Delete::", "User account deleted.");
                            }
                        }
                    });
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent intent  = new Intent(SettingsActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }
    }
}
