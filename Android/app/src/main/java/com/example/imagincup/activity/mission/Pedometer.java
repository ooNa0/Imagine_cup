package com.example.imagincup.activity.mission;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;


import com.example.imagincup.R;

public class Pedometer extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private Sensor stepCountSensor;

    private TextView pedometerText;
    private Button pedometerResetButton;
    private TextView completion;
    private Toolbar toolbar;
    private int currentSteps = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);

        checkPermission();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        pedometerResetButton = findViewById(R.id.pedometer_button);
        pedometerResetButton.setEnabled(false);
        pedometerText = findViewById(R.id.pedometer_count);

        toolbar = findViewById(R.id.pedometer_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");


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
        pedometerResetButton.setEnabled(setButtonEnabled(pedometerText));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private boolean setButtonEnabled(TextView text){
            int pedometerCount = Integer.parseInt(text.getText().toString());
            if(pedometerCount >= 50){
                return true;
            }
            return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                // 액티비티 이동
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}