package com.example.user.yakhae_demo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditMyInfoActivity extends AppCompatActivity {

    ArrayAdapter adapter;

    String userID, user_gender,userNickname, user_age, user_type,user_email,Uid;
    TextView User_email;
    EditText User_name;
    Spinner User_Age;
    RadioGroup gender_radio_group,user_type_group;

    Button editbutton;

    RadioButton selectedGender, selectedType;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference("users");

    private void createEdictUser(String userID, String userGender, String userAge, String userNickname, String userType, String Uid){
        User user = new User(userID, userGender, userNickname, userAge, userType);
        DatabaseManager.databaseReference.child("users").child(Uid).setValue(user);
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_icon);

        User_email = (TextView)findViewById(R.id.user_email_text);
        User_name = (EditText) findViewById(R.id.user_nickname_text);
        User_Age = (Spinner) findViewById(R.id.AgeSpinner);
        gender_radio_group = (RadioGroup)findViewById(R.id.gender_radio_group);
        user_type_group = (RadioGroup)findViewById(R.id.user_type_group);

        adapter = ArrayAdapter.createFromResource(this, R.array.Age,android.R.layout.simple_spinner_dropdown_item);
        User_Age.setAdapter(adapter);

        editbutton = (Button)findViewById(R.id.editButton);

        Uid = user.getUid();

        mUserDatabase.child(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                user_email = dataSnapshot.child("userID").getValue().toString();
                userNickname = dataSnapshot.child("nickname").getValue().toString();
                user_gender = dataSnapshot.child("gender").getValue().toString();
                user_age = dataSnapshot.child("age").getValue().toString();
                user_type = dataSnapshot.child("type").getValue().toString();

                int age_index=0;
                switch (user_age.trim()){
                    case "10대":
                        age_index=0;
                        break;
                    case "20대":
                        age_index=1;
                        break;
                    case "30대":
                        age_index=2;
                        break;
                    case "40대":
                        age_index=3;
                        break;
                    case "50대":
                        age_index=4;
                        break;
                    case "60대이상":
                        age_index=5;
                        break;
                }

                int gender=0;
                switch (user_gender.trim()){
                    case "남자":
                        gender=R.id.Man;
                        break;
                    case "여자":
                        gender=R.id.Woman;
                        break;
                }

                int type =0;
                switch (user_type.trim()){
                    case "일반인":
                        type=R.id.General;
                        break;
                    case "의사/약사":
                        type=R.id.Expert;
                        break;
                }

                User_email.setText(user_email);
                User_name.setText(userNickname);
                User_Age.setSelection(age_index);

                selectedGender = (RadioButton)findViewById(gender);
                selectedType = (RadioButton)findViewById(type);

                gender_radio_group.check(selectedGender.getId());
                user_type_group.check(selectedType.getId());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int gender = gender_radio_group.getCheckedRadioButtonId();
                selectedGender = (RadioButton)findViewById(gender);
                int type = user_type_group.getCheckedRadioButtonId();
                selectedType = (RadioButton)findViewById(type);

                userID = User_email.getText().toString().trim();
                userNickname = User_name.getText().toString().trim();
                user_age = User_Age.getSelectedItem().toString().trim();
                user_gender = selectedGender.getText().toString().trim();
                user_type = selectedType.getText().toString().trim();

                Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                createEdictUser(userID,user_gender, user_age, userNickname, user_type, Uid);

                Toast.makeText(EditMyInfoActivity.this, "수정이 완료되었습니다!", Toast.LENGTH_SHORT).show();
                finish();
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
