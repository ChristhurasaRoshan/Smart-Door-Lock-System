package com.example.mysmartdoorapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CreatePinCodeActivity extends AppCompatActivity implements View.OnClickListener{

    View view_1,view_2,view_3,view_4,view_01,view_02,view_03,view_04;
    Button btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,btn_0,btn_clear,createbtn,btn_01,btn_02,btn_03,btn_04,btn_05,btn_06,btn_07,btn_08,btn_09,btn_00,btn_0clear,verifybtn;

    ArrayList<String> numbers_list = new ArrayList<>();
    ArrayList<String> numbers_list0 = new ArrayList<>(); 

    String enterPin= "";
    String verifyPin= "";
    DatabaseReference dbref;

    LinearLayout verifylayout;
    LinearLayout createlayout;
    String num_1,num_2,num_3,num_4,num_01,num_02,num_03,num_04;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pin_code);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser(); //change


        if (currentUser != null) {
            String userId = currentUser.getUid();
            dbref = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        } else {
            // Handle the case where the user is not signed in
        }

        verifylayout = findViewById(R.id.verifylayout);
        createlayout = findViewById(R.id.createlayout);
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
        createbtn = findViewById(R.id.createbtn);

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

        view_01 = findViewById(R.id.view_01);
        view_02 = findViewById(R.id.view_02);
        view_03 = findViewById(R.id.view_03);
        view_04 = findViewById(R.id.view_04);

        btn_01 = findViewById(R.id.btn_01);
        btn_02 = findViewById(R.id.btn_02);
        btn_03 = findViewById(R.id.btn_03);
        btn_04 = findViewById(R.id.btn_04);
        btn_05 = findViewById(R.id.btn_05);
        btn_06 = findViewById(R.id.btn_06);
        btn_07 = findViewById(R.id.btn_07);
        btn_08 = findViewById(R.id.btn_08);
        btn_09 = findViewById(R.id.btn_09);
        btn_00 = findViewById(R.id.btn_00);
        btn_0clear = findViewById(R.id.btn_0clear);
        verifybtn = findViewById(R.id.verifybtn);

        btn_01.setOnClickListener(this);
        btn_02.setOnClickListener(this);
        btn_03.setOnClickListener(this);
        btn_04.setOnClickListener(this);
        btn_05.setOnClickListener(this);
        btn_06.setOnClickListener(this);
        btn_07.setOnClickListener(this);
        btn_08.setOnClickListener(this);
        btn_09.setOnClickListener(this);
        btn_00.setOnClickListener(this);
        btn_0clear.setOnClickListener(this);
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
        }else if (viewId == R.id.btn_01) {
            numbers_list0.add("1");
            passNumber1(numbers_list0);
        }else if (viewId == R.id.btn_02) {
            numbers_list0.add("2");
            passNumber1(numbers_list0);
        } else if (viewId == R.id.btn_03) {
            numbers_list0.add("3");
            passNumber1(numbers_list0);
        } else if (viewId == R.id.btn_04) {
            numbers_list0.add("4");
            passNumber1(numbers_list0);
        } else if (viewId == R.id.btn_05) {
            numbers_list0.add("5");
            passNumber1(numbers_list0);
        } else if (viewId == R.id.btn_06) {
            numbers_list0.add("6");
            passNumber1(numbers_list0);
        } else if (viewId == R.id.btn_07) {
            numbers_list0.add("7");
            passNumber1(numbers_list0);
        } else if (viewId == R.id.btn_08) {
            numbers_list0.add("8");
            passNumber1(numbers_list0);
        } else if (viewId == R.id.btn_09) {
            numbers_list0.add("9");
            passNumber1(numbers_list0);
        } else if (viewId == R.id.btn_00) {
            numbers_list0.add("0");
            passNumber1(numbers_list0);
        } else if (viewId == R.id.btn_0clear) {
            numbers_list0.clear();
            passNumber1(numbers_list0);
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
                    createbtn.setVisibility(View.VISIBLE);
                    createbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            enterPin = num_1 + num_2 + num_3 + num_4;
                            Toast.makeText(CreatePinCodeActivity.this, "Create Passcode", Toast.LENGTH_SHORT).show();
                            createlayout.setVisibility(View.GONE);
                            verifylayout.setVisibility(View.VISIBLE);
                        }
                    });
                    break;
            }
        }


    }


    private void passNumber1(ArrayList<String> numbersList0) {

        if (numbers_list0.size() == 0) {
            view_01.setBackgroundResource(R.drawable.bg_view_grey_oval);
            view_02.setBackgroundResource(R.drawable.bg_view_grey_oval);
            view_03.setBackgroundResource(R.drawable.bg_view_grey_oval);
            view_04.setBackgroundResource(R.drawable.bg_view_grey_oval);

        } else {
            switch (numbers_list0.size()) {
                case 1:
                    num_01 = numbers_list0.get(0);
                    view_01.setBackgroundResource(R.drawable.bg_circle_filled_secondary);
                    break;
                case 2:
                    num_02 = numbers_list0.get(1);
                    view_02.setBackgroundResource(R.drawable.bg_circle_filled_secondary);
                    break;
                case 3:
                    num_03 = numbers_list0.get(2);
                    view_03.setBackgroundResource(R.drawable.bg_circle_filled_secondary);
                    break;
                case 4:
                    num_04 = numbers_list0.get(3);
                    view_04.setBackgroundResource(R.drawable.bg_circle_filled_secondary);
                    verifybtn.setVisibility(View.VISIBLE);
                    verifybtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            verifyPin = num_1 + num_2 + num_3 + num_4;
                            if (verifyPin.equals(enterPin)) {
                                //save pincode
                                dbref.child("pincode").setValue(verifyPin);
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                //there is no match password
                                Toast.makeText(CreatePinCodeActivity.this, "Passcode doesn't match", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    break;
            }
        }
    }

}