package com.example.wifi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;

public class Afficher extends AppCompatActivity {
    TextView infoTX;
    TextView spdd; // Add this line to declare spdd TextView
    WifiManager wifiManager;
    WifiInfo wifiInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher);

        // Initialize WifiManager and WifiInfo inside onCreate
        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        wifiInfo = wifiManager.getConnectionInfo();

        infoTX = findViewById(R.id.info);
        spdd = findViewById(R.id.spdd); // Initialize spdd TextView

        if (wifiManager.isWifiEnabled()) {
            String info = "Link Speed: " + wifiInfo.getLinkSpeed() + "ms\n"
                    + "IP Address: " + wifiInfo.getIpAddress() + "\n";

            infoTX.setText(info);

            spdd.setText("Wifi State: " + wifiState() + "\n");

        } else {
            infoTX.setText("WIFI NOT CONNECTED !!!!");
        }
    }

    String wifiState() {
        int linkSpeed = wifiInfo.getLinkSpeed();

        if (linkSpeed >= 200) {
            spdd.setTextColor(ContextCompat.getColor(this, android.R.color.holo_blue_light));
            return "Excellent Connection\n👌";
        } else if (linkSpeed >= 100 && linkSpeed < 200) {
            // Set the color for the 'Good Connection'
            spdd.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_light));
            return "Good Connection\n😊";
        } else if (linkSpeed >= 50 && linkSpeed < 100) {
            // Set the color for the 'Fair Connection'
            spdd.setTextColor(ContextCompat.getColor(this, android.R.color.holo_orange_light));
            return "Fair Connection\n😐";
        } else {
            // Set the color for the 'Poor Connection'
            spdd.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_light));
            return "Poor Connection\n😓";
        }
    }
}
