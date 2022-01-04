package com.example.imagincup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.imagincup.activity.mission.Camera;
import com.example.imagincup.activity.mission.Pedometer;
import com.example.imagincup.activity.mission.Record;

public class MissionActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageButton imageButton;
    private Button startButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);

        init();

    }

    private void init(){
        toolbar = findViewById(R.id.activity_mission_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        imageButton = findViewById(R.id.activity_mission_imageButton);
        imageButton.setImageResource(R.drawable.hamburger);

        startButton = findViewById(R.id.mission_start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), Pedometer.class);
//                Intent intent = new Intent(getApplicationContext(),Camera.class);
                Intent intent = new Intent(getApplicationContext(), Record.class);
                startActivity(intent);
            }
        });
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