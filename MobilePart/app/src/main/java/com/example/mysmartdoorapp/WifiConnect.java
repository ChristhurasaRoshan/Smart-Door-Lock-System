package com.example.mysmartdoorapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class WifiConnect extends AppCompatActivity {

    private WifiManager wifiManager;

    private ArrayAdapter<String> adapter;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 123;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_connect);

        progressDialog = new ProgressDialog(WifiConnect.this);
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);


        //this code for list the available wifi list
        ListView wifiList = findViewById(R.id.wifiList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        wifiList.setAdapter(adapter);


        Button buttonScan = findViewById(R.id.scanBtn);

        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(WifiConnect.this, "Turn on your wifi and Location", Toast.LENGTH_LONG).show();
            wifiManager.setWifiEnabled(true);
        }

        registerReceiver(wifiScanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));



        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Scanning...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                checkLocationPermission();
            }
        });
    }



    // BroadcastReceiver to receive WiFi scan results
    private final BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {

                List<ScanResult> scanResults = wifiManager.getScanResults();

                // Clear previous results
                adapter.clear();

                // Display the WiFi networks in the ListView
                for (ScanResult result : scanResults) {

//                    String wifiInfo = result.SSID + " (" + result.capabilities + ")";

                    String wifiInfo = result.SSID;

                    //Toast.makeText(MainActivity.this, wifiInfo, Toast.LENGTH_LONG).show();

                    adapter.add(wifiInfo);
                }

                // Notify the adapter that the data set has changed
                adapter.notifyDataSetChanged();

                progressDialog.dismiss();
                // Start a new scan for continuous updates
                wifiManager.startScan();
            }
        }
    };


    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            progressDialog.dismiss();
        } else {
            // Permission already granted, proceed with WiFi scanning
            wifiManager.startScan();
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(wifiScanReceiver);
    }
}