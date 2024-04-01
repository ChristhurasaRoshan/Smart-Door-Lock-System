
package com.example.mysmartdoorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    SwitchCompat switchButton;
    SimpleDateFormat simpleDateFormat;
    Button viewHistory;
    TextView textopen , textclose;

    ImageView profileimg;


    DatabaseReference databaseReference;

    ProgressDialog progressDialog;

    //private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkRequest.Builder builder = new NetworkRequest.Builder();
//        builder.addTransportType (NetworkCapabilities. TRANSPORT_WIFI);
//        builder.addCapability (NetworkCapabilities.NET_CAPABILITY_INTERNET);
//        NetworkRequest request = builder.build();
//        connManager.requestNetwork(request, new ConnectivityManager.NetworkCallback() {
//            @Override
//            public void onAvailable(@NonNull Network network) {
//                connManager.bindProcessToNetwork(network);
//            }
//        });


        viewHistory= findViewById(R.id.historybtn);
        textopen = findViewById(R.id.textopen);
        textclose= findViewById(R.id.textclose);
        profileimg = findViewById(R.id.main_profile);
        switchButton = findViewById(R.id.switchbutton);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        progressDialog.show();

        String userId = FirebaseAuth.getInstance().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        databaseReference.child("currentStatus").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String status = snapshot.getValue(String.class);
                if(status.equals("Close")){
                    switchButton.setChecked(true);
                    textopen.setVisibility(View.GONE);
                    textclose.setVisibility(View.VISIBLE);
                }
                else {
                    switchButton.setChecked(false);
                    textopen.setVisibility(View.VISIBLE);
                    textclose.setVisibility(View.GONE);
                }
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentDate = simpleDateFormat.format(calendar.getTime());

                progressDialog.show();

                if(textopen.getVisibility() == View.VISIBLE){
                    databaseReference.child("currentStatus").setValue("Close");
                    databaseReference.child("History").child(currentDate).setValue("Close").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();

                            }
                        }
                    });
                    textopen.setVisibility(View.GONE);
                    textclose.setVisibility(View.VISIBLE);
                    switchButton.setChecked(true);
                }  else{
                    databaseReference.child("currentStatus").setValue("Open");
                    databaseReference.child("History").child(currentDate).setValue("Open").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                            }
                        }
                    });
                    textopen.setVisibility(View.VISIBLE);
                    textclose.setVisibility(View.GONE);
                    switchButton.setChecked(false);
                }
            }
        });


        viewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
        profileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }


}