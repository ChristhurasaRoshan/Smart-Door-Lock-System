package com.example.mysmartdoorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.mysmartdoorapp.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {

    TextView username, useremail, userpw, status;
    DatabaseReference dbref;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        username = findViewById(R.id.usertextname);
        useremail = findViewById(R.id.usertextemail);
        userpw = findViewById(R.id.usertextpw);
        status = findViewById(R.id.usertextstatus);

        if (currentUser != null) {
            String userId = currentUser.getUid();
            dbref = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);


            dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserModel userProfile = snapshot.getValue(UserModel.class);
                    if (userProfile != null) {
                        username.setText(userProfile.getName());
                        useremail.setText(userProfile.getEmail());
                        userpw.setText(userProfile.getPassword());
                        status.setText(userProfile.getCurrentStatus());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}