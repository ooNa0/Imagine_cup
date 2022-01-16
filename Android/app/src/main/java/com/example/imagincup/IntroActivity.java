package com.example.imagincup;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.imagincup.back.IntroThread;
import com.example.imagincup.back.PersonTableAsyncTask;

import java.util.concurrent.ExecutionException;

public class IntroActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private String DeviceID;
    private int result;
    private Intent intent;

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
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
                result = introThread.getResult();
                if(result == Constants.DATABASE_EXIST) {
                    // 인트로 실행 후 바로 MainActivity로 넘어감.
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                }
                else {
                    // 회원가입 페이지로 이동
                    intent = new Intent(getApplicationContext(), SignupActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },1000);

//        IntroThread introThread = new IntroThread(DeviceID);
//        introThread.start();
//        try {
//            introThread.join(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        result = introThread.getResult();
//            if(result == Constants.DATABASE_EXIST) {
//                // 인트로 실행 후 바로 MainActivity로 넘어감.
//                intent = new Intent(getApplicationContext(), MainActivity.class);
//            }
//            else {
//                // 회원가입 페이지로 이동
//                intent = new Intent(getApplicationContext(), SignupActivity.class);
//            }
//            startActivity(intent);
//            finish();
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
