package com.example.mysmartdoorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SplashActivity extends AppCompatActivity {

    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseUser currentUser = mAuth.getCurrentUser();


                if (currentUser != null) {
                    String userId = currentUser.getUid();
                    dbref = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("currentStatus");
                    dbref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            String status = snapshot.getValue(String.class);

                            Intent intent;
                            if(status.equals("Open")){
                                intent = new Intent(SplashActivity.this, UnLockStatusActivity.class);
                            }else{
                                intent = new Intent(SplashActivity.this, LockStatusActivity.class);
                            }
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                else {
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    finish();
                }
            }
        }, 2000);
    }


}