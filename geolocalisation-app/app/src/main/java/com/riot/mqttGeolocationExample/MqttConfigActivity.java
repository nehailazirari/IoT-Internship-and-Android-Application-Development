package com.riot.mqttGeolocationExample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MqttConfigActivity extends Activity {

    private EditText hostEditText;
    private EditText portEditText;
    private EditText deviceNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mqtt_config);

        hostEditText = findViewById(R.id.hostEditText);
        portEditText = findViewById(R.id.portEditText);
        deviceNameEditText = findViewById(R.id.deviceNameEditText);

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveConfigurations();
            }
        });
    }

    private void saveConfigurations() {
        String host = hostEditText.getText().toString();
        String port = portEditText.getText().toString();
        String deviceName = deviceNameEditText.getText().toString();

        // Obtenez l'instance des préférences partagées
        // Obtenez l'instance des préférences partagées
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("HOST", host);
        editor.putString("PORT", port);
        editor.putString("DEVICE_NAME", deviceName);
        editor.apply();




        finish();
    }
}
