package com.example.user.yakhae_demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayAdapter adapter;
    Spinner spinner;
    FirebaseAuth firebaseAuth;
    Button registerButton;
    ProgressDialog progressDialog;
    EditText idText;
    EditText textPassword;
    EditText nicknameText;
    RadioGroup genderGroup, usertypeGroup;
    RadioButton selectedGender, selectedType;

    private String Uid, userID, userPassword, userNickname, userGender, userAge, userType;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        if (user != null){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        idText = (EditText)findViewById(R.id.idText);
        textPassword = (EditText)findViewById(R.id.passwordText);
        nicknameText = (EditText)findViewById(R.id.nicknameText);

        genderGroup = (RadioGroup)findViewById(R.id.genderGroup);
        usertypeGroup = (RadioGroup)findViewById(R.id.usertypeGroup);

        registerButton = (Button)findViewById(R.id.submitButton);
        registerButton.setOnClickListener(this);

        spinner = (Spinner)findViewById(R.id.AgeSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.Age,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        progressDialog = new ProgressDialog(this);
    }

    private void createNewUser(String userID, String userGender, String userAge, String userNickname, String userType, String Uid){
        User user = new User(userID, userGender, userNickname, userAge, userType);
        DatabaseManager.databaseReference.child("users").child(Uid).setValue(user);
    }

    private void registerUser() {
        userID = idText.getText().toString().trim();
        userPassword = textPassword.getText().toString().trim();
        userNickname = nicknameText.getText().toString().trim();

        if(TextUtils.isEmpty(userID)){
            Toast.makeText(this,"ID를 입력해주세요.",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(this,"비밀번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(userNickname)){
            Toast.makeText(this,"닉네임을 입력해주세요.",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("등록 중 입니다. 기다려 주세요...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(userID, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            int gender = genderGroup.getCheckedRadioButtonId();
                            selectedGender = (RadioButton)findViewById(gender);
                            int type = usertypeGroup.getCheckedRadioButtonId();
                            selectedType = (RadioButton)findViewById(type);

                            userID = idText.getText().toString().trim();
                            userNickname = nicknameText.getText().toString().trim();
                            userAge = spinner.getSelectedItem().toString().trim();
                            userGender = selectedGender.getText().toString().trim();
                            userType = selectedType.getText().toString().trim();

                            Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            createNewUser(userID, userGender, userAge, userNickname, userType, Uid);

                            Toast.makeText(RegisterActivity.this, "등록이 완료되었습니다!", Toast.LENGTH_SHORT).show();
                            finish();

                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        } else {
                            Toast.makeText(RegisterActivity.this, "6자리 이상 비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
                            Toast.makeText(RegisterActivity.this, "등록 에러!", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        registerUser(); // FirebaseAuth 인증 회원가입 등록
    }

}
