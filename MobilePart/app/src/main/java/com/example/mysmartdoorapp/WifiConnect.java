package com.example.mysmartdoorapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        // Initialize ListView to display Wi-Fi networks
        ListView wifiList = findViewById(R.id.wifiList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        wifiList.setAdapter(adapter);

        // Check if Wi-Fi is enabled, if not, prompt user to enable it
        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(WifiConnect.this, "Turn on your Wi-Fi", Toast.LENGTH_LONG).show();
            wifiManager.setWifiEnabled(true);
        }

        // Register BroadcastReceiver to receive Wi-Fi scan results
        registerReceiver(wifiScanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        // Button click listener to initiate Wi-Fi scan
        Button buttonScan = findViewById(R.id.scanBtn);
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Scanning...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                checkLocationPermission();
            }
        });

        // ListView item click listener to connect to selected Wi-Fi network
        wifiList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ssid = adapter.getItem(position);
                showPasswordDialog(ssid);
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
                    String wifiInfo = result.SSID;
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

    // Method to check location permission for Wi-Fi scanning
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            progressDialog.dismiss();
        } else {
            // Permission already granted, proceed with WiFi scanning
            wifiManager.startScan();
        }
    }

    // Method to show password dialog when connecting to a Wi-Fi network
    private void showPasswordDialog(final String ssid) {
        final EditText passwordInput = new EditText(this);
        passwordInput.setHint("Enter your Wi-Fi Password");

        // Create dialog for password input
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Connect to " + ssid);
        builder.setView(passwordInput);
        builder.setPositiveButton("Connect", (dialog, which) -> {
            String password = passwordInput.getText().toString();
            connectToWifi(ssid, password);
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    // Method to connect to a Wi-Fi network
    public void connectToWifi(String ssid, String password) {
        WifiConfiguration wifiConf = new WifiConfiguration();
        wifiConf.SSID = "\"" + ssid + "\"";
        wifiConf.preSharedKey = "\"" + password + "\"";

        int netId = wifiManager.addNetwork(wifiConf);
        if (netId != -1) {
            wifiManager.disconnect();
            wifiManager.enableNetwork(netId, true);
            wifiManager.reconnect();
            Toast.makeText(this, "Connecting to " + ssid, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to connect to " + ssid, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(wifiScanReceiver);
    }
}
