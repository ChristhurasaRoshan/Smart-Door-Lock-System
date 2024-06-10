package com.example.mysmartdoorapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.mysmartdoorapp.models.HistoryItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;



    HistoryAdapter historyAdapter;
    ArrayList<HistoryItem> arr=new ArrayList<>();

    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView=findViewById(R.id.historylist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();


        if (currentUser != null) {
            String userId = currentUser.getUid();
            dbref = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("History");

            historyAdapter = new HistoryAdapter(HistoryActivity.this,arr);
            recyclerView.setAdapter(historyAdapter);

            dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    arr.clear();
                    for(DataSnapshot ds: snapshot.getChildren()){
                        String key = ds.getKey();
                        String status = ds.getValue(String.class);
                        arr.add(new HistoryItem(key, status));
                    }
                    Collections.reverse(arr);

                    historyAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else {
            // Handle the case where user is not signed in
        }


    }
    public void deletedata(View v) {

        arr.clear();
        dbref.removeValue();

    }
}