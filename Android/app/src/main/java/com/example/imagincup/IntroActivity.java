package com.example.imagincup;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.imagincup.back.DTO.DTOPerson;
import com.example.imagincup.back.IntroThread;

import java.sql.ResultSet;

public class IntroActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private String DeviceID;
    private int result;
    private Intent intent;
    private DTOPerson dtoPerson;
    private ResultSet resultSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        DeviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                IntroThread introThread = new IntroThread(DeviceID);
                introThread.start();
                try {
                    introThread.join();
                    result = introThread.getResult();
                    if(result == Constants.DATABASE_EXIST) {
                        // 인트로 실행 후 바로 MainActivity로 넘어감.
                        dtoPerson = introThread.getResultDataSet();
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("Person", dtoPerson);
                    }
                    else {
                        // 회원가입 페이지로 이동
                        intent = new Intent(getApplicationContext(), SignupActivity.class);
                    }
                    startActivity(intent);
                    finish();
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        },1000);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
