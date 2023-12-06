package com.example.wifi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Scanner extends AppCompatActivity {

    private WifiManager wifiManager;
    private ListView listView;
    private List<ScanResult> results;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter adapter;

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        listView = findViewById(R.id.wifiList);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        // Check and request location permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
        } else {
            // Location permission is already granted, proceed with scanning Wi-Fi
            scanWifi();
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
    }

    private void scanWifi() {
        arrayList.clear();
        registerReceiver(wifiReciever, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
        Toast.makeText(this, "Scanning for Wi-Fi networks...", Toast.LENGTH_SHORT).show();
    }

    final BroadcastReceiver wifiReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ActivityCompat.checkSelfPermission(Scanner.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            results = wifiManager.getScanResults();
            unregisterReceiver(this);
            for (ScanResult scanResult : results) {
                String wifiInfo = "SSID: " + scanResult.SSID + "\nBSSID: " + scanResult.BSSID;
                arrayList.add(wifiInfo);
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION) {
            // Check if the location permission was granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permission granted, proceed with scanning Wi-Fi
                scanWifi();
            } else {
                // Location permission denied, show a message or take appropriate action
                // For example, display a Toast message
                Toast.makeText(this, "Location permission denied. Cannot scan Wi-Fi networks.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
