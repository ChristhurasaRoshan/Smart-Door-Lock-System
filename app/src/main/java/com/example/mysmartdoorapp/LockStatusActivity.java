package com.example.mysmartdoorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LockStatusActivity extends AppCompatActivity {

    Button lockbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_status);

        lockbutton=findViewById(R.id.lockbtn);

        lockbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LockStatusActivity.this, AuthnticationActivity.class);
                startActivity(intent);
            }
        });
    }
}