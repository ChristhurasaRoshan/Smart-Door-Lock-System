package com.example.mysmartdoorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class UserProfileActivity extends AppCompatActivity {

    TextView username, useremail, userpw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        username = findViewById(R.id.usertextname);
        useremail = findViewById(R.id.usertextemail);
        userpw = findViewById(R.id.usertextpw);
    }
}