package com.riot.mqttGeolocationExample.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class CustomMqttService {

    private String HOST; //= "192.168.43.84";
    private String PORT; //= "1883";
    private String TOPIC = "android/gps";

    private String TAG = "MQTT";
    private MqttAndroidClient client;
    private Context context;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    private String deviceName;




    public CustomMqttService(Context context) {

        this.context = context;

        }



    public void connect() {
        if(context!=null){
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            HOST = sharedPreferences.getString("HOST", "192.168.43.84");
            PORT = sharedPreferences.getString("PORT", "1883");
            deviceName = sharedPreferences.getString("DEVICE_NAME", "appTracker");
        }

        String connectionUri = "tcp://" + HOST + ":" + PORT;
        Log.d("host",connectionUri);
        String clientId = MqttClient.generateClientId();

        client = new MqttAndroidClient(context, connectionUri, clientId);

        try {
            IMqttToken token = client.connect();

            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d(TAG, "success");
                    Toast.makeText(context, "Mqtt connected!!", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d(TAG, "failure: " + exception.getMessage());
                    Toast.makeText(context, "Mqtt connection failed", Toast.LENGTH_LONG).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        if (client != null && client.isConnected())
            try {
                client.disconnect();
            } catch (MqttException e) {
                e.printStackTrace();
            }
    }


    public void pub(String msg) {

        try {
            IMqttToken token = client.publish(TOPIC, msg.getBytes(), 0, true);
            Toast.makeText(context, "Mqtt pop!!", Toast.LENGTH_LONG).show();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d(TAG, "success");
                    Toast.makeText(context, "Mqtt pop!!", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d(TAG, "failure: " + exception.getMessage());
                    Toast.makeText(context, "Mqtt pop failed", Toast.LENGTH_LONG).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
