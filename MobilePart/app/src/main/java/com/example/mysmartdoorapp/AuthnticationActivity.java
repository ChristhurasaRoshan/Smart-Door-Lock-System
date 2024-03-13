package com.example.mysmartdoorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AuthnticationActivity extends AppCompatActivity {

    Button passcodebtn,fingerbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authntication);

        passcodebtn = findViewById(R.id.btn_fppin);
        fingerbtn = findViewById(R.id.btn_fp);

        passcodebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthnticationActivity.this, PinAuthenticationActivity.class);
                startActivity(intent);
                Toast.makeText(AuthnticationActivity.this, "Using Your Passcode", Toast.LENGTH_SHORT).show();
            }
        });

        fingerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthnticationActivity.this, FingerPrintAuthActivity.class);
                startActivity(intent);
                Toast.makeText(AuthnticationActivity.this, "Using Your Finger Print", Toast.LENGTH_SHORT).show();
            }
        });
    }
}