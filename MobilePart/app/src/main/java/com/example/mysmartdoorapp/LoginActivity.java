package com.example.mysmartdoorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mysmartdoorapp.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    Button signup;
    EditText email, password, name;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.name_login);
        signup = findViewById(R.id.login_btn);
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        database = FirebaseDatabase.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
    }

    private void createUser() {
        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "Name empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, "Email is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userPassword.length() < 8) {
            Toast.makeText(this, "Password length must be greater than 8 charachters", Toast.LENGTH_SHORT).show();
            return;
        }

        //create user
        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    UserModel userModel = new UserModel(userName, userEmail, userPassword, "Close");
                    String id = task.getResult().getUser().getUid();
                    database.getReference().child("Users").child(id).setValue(userModel);
                    finish();
                    Intent intent = new Intent(LoginActivity.this, CreatePinCodeActivity.class);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Login Sucessfull", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(LoginActivity.this, "Error:" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}