package com.example.imagincup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.imagincup.activity.mission.Camera;
import com.example.imagincup.activity.mission.Music;
import com.example.imagincup.activity.mission.Pedometer;
import com.example.imagincup.activity.mission.Record;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MissionActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageButton imageButton;
    private Button startButton;

    private boolean isDone;
    private boolean isSet;
    private int missionNumber;
    private Intent intent;

    private int DAY = 86400000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);

        isDone = false;
        isSet = false;

        init();
        Timer timer = new Timer();
        timer.schedule(initState(),0,DAY);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 1:
                Intent i = getIntent();
                isDone = i.getBooleanExtra("is_done",true);
                break;
        }
        Log.d("MISSION", String.valueOf(isDone));
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
                    if(!isSet){
                        Random random = new Random();
                        missionNumber = random.nextInt(4);
                    }
                    if(isDone){
                        intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        return;
                    }
                    missionNumber = 1;
                    switch (missionNumber){
                        case 0 :
                            intent = new Intent(getApplicationContext(), Pedometer.class);
                            intent.putExtra("is_done",isDone);
                            startActivityForResult(intent,0000);
                            break;
                        case 1:
                            intent = new Intent(getApplicationContext(),Camera.class);
                            intent.putExtra("is_done",isDone);
                            startActivityForResult(intent,1111);
                            break;

                        case 2:
                            intent = new Intent(getApplicationContext(), Record.class);
                            intent.putExtra("is_done",isDone);
                            startActivityForResult(intent,2222);
                            break;

                        case 3:
                            intent = new Intent(getApplicationContext(), Music.class);
                            intent.putExtra("is_done",isDone);
                            startActivityForResult(intent,3333);
                            break;
                    }
            }
        });
    }

    private TimerTask initState(){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                isDone = false;
                isSet = false;
            }
        };

        return timerTask;
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