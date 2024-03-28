package com.example.mysmartdoorapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PinAuthenticationActivity extends AppCompatActivity implements View.OnClickListener{

    View view_1,view_2,view_3,view_4;
    Button btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,btn_0,btn_clear,getinbtn;

    ArrayList<String> numbers_list = new ArrayList<>();

    DatabaseReference dbref;
    String getinPin= "";


    String num_1,num_2,num_3,num_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_authentication);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();


        if (currentUser != null) {
            String userId = currentUser.getUid();
            dbref = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        } else {
            // Handle the case where the user is not signed in
        }


        initializeComponents();

    }

    private void initializeComponents() {
        view_1 = findViewById(R.id.view_1);
        view_2 = findViewById(R.id.view_2);
        view_3 = findViewById(R.id.view_3);
        view_4 = findViewById(R.id.view_4);

        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        btn_6 = findViewById(R.id.btn_6);
        btn_7 = findViewById(R.id.btn_7);
        btn_8 = findViewById(R.id.btn_8);
        btn_9 = findViewById(R.id.btn_9);
        btn_0 = findViewById(R.id.btn_0);
        btn_clear = findViewById(R.id.btn_clear);
        getinbtn= findViewById(R.id.getinbtn);

        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_0.setOnClickListener(this);
        btn_clear.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btn_1) {
            numbers_list.add("1");
            passNumber(numbers_list);
        } else if (viewId == R.id.btn_2) {
            numbers_list.add("2");
            passNumber(numbers_list);
        } else if (viewId == R.id.btn_3) {
            numbers_list.add("3");
            passNumber(numbers_list);
        } else if (viewId == R.id.btn_4) {
            numbers_list.add("4");
            passNumber(numbers_list);
        } else if (viewId == R.id.btn_5) {
            numbers_list.add("5");
            passNumber(numbers_list);
        } else if (viewId == R.id.btn_6) {
            numbers_list.add("6");
            passNumber(numbers_list);
        } else if (viewId == R.id.btn_7) {
            numbers_list.add("7");
            passNumber(numbers_list);
        } else if (viewId == R.id.btn_8) {
            numbers_list.add("8");
            passNumber(numbers_list);
        } else if (viewId == R.id.btn_9) {
            numbers_list.add("9");
            passNumber(numbers_list);
        } else if (viewId == R.id.btn_0) {
            numbers_list.add("0");
            passNumber(numbers_list);
        } else if (viewId == R.id.btn_clear) {
            numbers_list.clear();
            passNumber(numbers_list);
        }
    }

    private void passNumber(ArrayList<String> numbersList) {
        if (numbers_list.size() == 0) {
            view_1.setBackgroundResource(R.drawable.bg_view_grey_oval);
            view_2.setBackgroundResource(R.drawable.bg_view_grey_oval);
            view_3.setBackgroundResource(R.drawable.bg_view_grey_oval);
            view_4.setBackgroundResource(R.drawable.bg_view_grey_oval);

        } else {
            switch (numbers_list.size()) {
                case 1:
                    num_1 = numbers_list.get(0);
                    view_1.setBackgroundResource(R.drawable.bg_circle_filled_secondary);
                    break;
                case 2:
                    num_2 = numbers_list.get(1);
                    view_2.setBackgroundResource(R.drawable.bg_circle_filled_secondary);
                    break;
                case 3:
                    num_3 = numbers_list.get(2);
                    view_3.setBackgroundResource(R.drawable.bg_circle_filled_secondary);
                    break;
                case 4:
                    num_4 = numbers_list.get(3);
                    view_4.setBackgroundResource(R.drawable.bg_circle_filled_secondary);
                    getinbtn.setVisibility(View.VISIBLE);
                    getinbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getinPin = num_1 + num_2 + num_3 + num_4;

                            dbref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String passcode=snapshot.child("pincode").getValue().toString();
                                    if(getinPin.equals(passcode)){
                                        //enter the app
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        Toast.makeText(PinAuthenticationActivity.this, "Wrong Pincode", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                        }
                    });
                    break;
            }
        }


    }


}