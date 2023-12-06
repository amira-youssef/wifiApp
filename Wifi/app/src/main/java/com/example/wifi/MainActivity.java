package com.example.wifi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    WifiManager wifiManager;
    Button btnConn;
    Button affich;
    Button scan;
    private static final int REQUEST_CODE_WIFI_PERMISSION = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);

        btnConn = findViewById(R.id.bconn);
        affich = findViewById(R.id.baff);
        scan = findViewById(R.id.bscan);

        if (wifiManager.isWifiEnabled()){
            btnConn.setText("Turn WIFI off ");
        } else if (!wifiManager.isWifiEnabled()) {
            btnConn.setText("Turn WIFI on ");
        }

        btnConn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    // Inform user about restrictions
                    Toast.makeText(MainActivity.this, "App cannot control Wi-Fi on Android 10+", Toast.LENGTH_SHORT).show();
                    btnConn.setEnabled(false); // Disable button if not usable
                } else {
                    // Update button text based on current Wi-Fi state
                    if (wifiManager.isWifiEnabled()) {
                        btnConn.setText("Turn WIFI Off");
                    } else {
                        btnConn.setText("Turn WIFI On");
                    }

                    // Set click listener for connection button
                    btnConn.setOnClickListener(view -> {
                        if (wifiManager.isWifiEnabled()) {
                            wifiManager.setWifiEnabled(false);
                            btnConn.setText("Turn WIFI On");
                        } else {
                            wifiManager.setWifiEnabled(true);
                            btnConn.setText("Turn WIFI Off");
                        }
                    });
                }
            }
        });


        affich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Afficher.class);
                startActivity(intent);
            }
        });
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Scanner.class);
                startActivity(intent);
            }
        });
    }





}
