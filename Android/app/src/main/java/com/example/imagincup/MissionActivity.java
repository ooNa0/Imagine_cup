package com.example.imagincup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imagincup.activity.mission.Camera;
import com.example.imagincup.activity.mission.Music;
import com.example.imagincup.activity.mission.Pedometer;
import com.example.imagincup.activity.mission.Record;
import com.example.imagincup.activity.survey.SurveyActivity;
import com.example.imagincup.back.DTO.DTOPerson;
import com.example.imagincup.back.task.UpdateMissionAsyncTask;
import com.example.imagincup.back.task.person.DeletePersonThread;
import com.example.imagincup.back.task.person.DeleteRecordThread;
import com.example.imagincup.back.task.person.DeleteScoreThread;
import com.example.imagincup.model.MissionState;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class MissionActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageButton imageButton;
    private Button startButton;
    private DTOPerson dtoPerson;

    private Boolean isDone;
    private Boolean isSet;
    private String isClear;
    private TextView title;

    private int missionNumber;
    private Intent intent;

    private int DAY = 86400000;
    private String TAG = "MISSION_STATE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);

        Intent intent = getIntent();
        dtoPerson = (DTOPerson)(intent.getSerializableExtra("Person"));

        init();
        Timer timer = new Timer();
        timer.schedule(initState(),0,DAY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        isDone = MissionState.getInstance().getDone();
        isSet = MissionState.getInstance().getIsSet();
        if(resultCode == RESULT_OK){
            isClear = "1";
        }
        try {
            new UpdateMissionAsyncTask().execute(new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis()), String.valueOf(dtoPerson.getPersonId())).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onActivityResult isDone : " + String.valueOf(isDone) + "isSet : " + isSet);
    }

    private void init(){
        isDone = MissionState.getInstance().getDone();
        isSet = MissionState.getInstance().getIsSet();
        isClear = "0";

        Log.d(TAG, "isDone : " + String.valueOf(isDone) + "isSet : " + isSet);
        title = findViewById(R.id.mission_title);
        toolbar = findViewById(R.id.activity_mission_toolbar);
        imageButton = findViewById(R.id.activity_mission_imageButton);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        if (!isSet) {
            MissionState.getInstance().setIsSet(true);
            Random random = new Random();
            missionNumber = random.nextInt(4);
            if(missionNumber == 0){
                title.setText("let's walk");
                imageButton.setImageResource(R.drawable.pedometer);
            }
            else if(missionNumber == 1){
                title.setText("Take a smiley face");
                imageButton.setImageResource(R.drawable.cameramission);
            }
            else if(missionNumber == 2){
                title.setText("Record your laughter");
                imageButton.setImageResource(R.drawable.recordmission);
            }
            else if(missionNumber == 3){
                title.setText("Watch the video");
                imageButton.setImageResource(R.drawable.music);
            }
        }
        if (isDone) {
            AlertDialog.Builder alert = new AlertDialog.Builder(MissionActivity.this);
            alert.setTitle("Alert");
            alert.setMessage("You've already completed your mission today.\nYou go back to the main screen.");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    alert.create().show();
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("Person", dtoPerson);
                    startActivity(intent);
                }
            });
            alert.create().show(); //보이기
        }
        else{
            startButton = findViewById(R.id.mission_start_button);
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (missionNumber) {
                        case 0:
                            intent = new Intent(getApplicationContext(), Pedometer.class);
                            intent.putExtra("is_done", isDone);
                            startActivityForResult(intent, 0000);
                            break;
                        case 1:
                            intent = new Intent(getApplicationContext(), Camera.class);
                            intent.putExtra("is_done", isDone);
                            startActivityForResult(intent, 1111);
                            break;

                        case 2:
                            intent = new Intent(getApplicationContext(), Record.class);
                            intent.putExtra("is_done", isDone);
                            startActivityForResult(intent, 2222);
                            break;

                        case 3:
                            intent = new Intent(getApplicationContext(), Music.class);
                            intent.putExtra("is_done", isDone);
                            startActivityForResult(intent, 3333);
                            break;
                    }
                }
            });
        }
    }

    private TimerTask initState(){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                MissionState.getInstance().setDone(false);
                MissionState.getInstance().setIsSet(false);
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