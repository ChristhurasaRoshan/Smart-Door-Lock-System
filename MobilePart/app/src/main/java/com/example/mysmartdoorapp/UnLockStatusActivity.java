package com.example.mysmartdoorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mysmartdoorapp.R;

public class UnLockStatusActivity extends AppCompatActivity {

    Button unlockbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_lock_status);

        unlockbutton=findViewById(R.id.unlockbtn);

        unlockbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(UnLockStatusActivity.this, AuthnticationActivity.class);
                startActivity(intent);
            }
        });
    }
}