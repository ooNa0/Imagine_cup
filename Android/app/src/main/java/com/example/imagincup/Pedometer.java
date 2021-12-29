package com.example.imagincup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Pedometer extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private Sensor stepCountSensor;

    private TextView pedometerText;
    private Button pedometerResetButton;
    private int currentSteps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);

        checkPermission();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        pedometerResetButton = findViewById(R.id.pedometer_reset_button);
        pedometerResetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // 현재 걸음 수 초기화
                currentSteps = 0;
                pedometerText.setText(String.valueOf(currentSteps));
            }
        });
        pedometerText = findViewById(R.id.pedometer_count);
    }

    @Override
    protected void onStart() {
        super.onStart();
        pedometerText.setText(String.valueOf(currentSteps));
        if(stepCountSensor != null){
            sensorManager.registerListener((SensorEventListener) this,stepCountSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    // 권한 체크
    private void checkPermission(){
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION},0);
        }
    }

    // 센서 유무 확인
    private void checkSensor(){
        if(stepCountSensor == null){
            Toast.makeText(this,"No Step Sensor",Toast.LENGTH_LONG).show();
        }
    }

    // 만보계 기능
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
            if(event.values[0] == 1.0f){
                currentSteps++;
                pedometerText.setText(String.valueOf(currentSteps));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}