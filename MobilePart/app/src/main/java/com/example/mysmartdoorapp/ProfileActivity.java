package com.example.mysmartdoorapp;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageActivity;


import com.example.mysmartdoorapp.models.UserModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    CircleImageView profileImage;

    DatabaseReference dbref;

    TextView userNameText;

    private Uri imageUri;
    private String myUri = "";
    private StorageTask uploadTask;
    FirebaseStorage storageProfilePics;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userNameText=findViewById(R.id.usertextname);
        profileImage=findViewById(R.id.imageView);
        storageProfilePics = FirebaseStorage.getInstance();


        if (currentUser != null) {
            String userId = currentUser.getUid();
            dbref = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);


            dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserModel userProfile = snapshot.getValue(UserModel.class);
                    if(userProfile!=null){
                        userNameText.setText(userProfile.getName());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            //user profile section
            CardView userprofile = findViewById(R.id.carduserprofile);
            userprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ProfileActivity.this, UserProfileActivity.class);
                    startActivity(intent);
                }
            });


            //logout section
            CardView logout = findViewById(R.id.cardlogout);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbref.removeValue();
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(ProfileActivity.this, "You are Logout", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                });
                } else {
                    // Handle the case where the user is not signed in
                }



                //profile image change
                profileImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent= new Intent();
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent,33);
                        uploadProfileImage();
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData() != null){
            Uri profileUri = data.getData();
            profileImage.setImageURI(profileUri);
            StorageReference storageProfilePicsRef = storageProfilePics.getReference().child("profile_pic").child(FirebaseAuth.getInstance().getUid());
            storageProfilePicsRef.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(ProfileActivity.this, "uploaded", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void uploadProfileImage(){
    }

}