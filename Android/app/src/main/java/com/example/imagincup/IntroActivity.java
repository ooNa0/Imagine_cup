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
    private AsyncTask<String, Void, String> asyncTask;
    private String DeviceID;
    private int result;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        DeviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        IntroThread introThread = new IntroThread(handler, DeviceID);
        introThread.start();

        try {
            introThread.join();
            if(result != Constants.DATABASE_EXIST) {
                // 인트로 실행 후 바로 MainActivity로 넘어감.
                intent = new Intent(getApplicationContext(), MainActivity.class);
                Thread.currentThread().interrupt();
            }
            else {
                // 회원가입 페이지로 이동
                intent = new Intent(getApplicationContext(), SignupActivity.class);
                Thread.currentThread().interrupt();
            }
            startActivity(intent);
            finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            result = message.what;
//            if (message.what != Constants.RUNNING) {
//                Log.d("-----------------------------------", String.valueOf(message.what));
//                Log.d("-----------------------------------", String.valueOf(Constants.DATABASE_EXIST));
//                Log.d("-----------------------------------", String.valueOf(message.what != Constants.DATABASE_EXIST));
//                if(message.what != Constants.DATABASE_EXIST) {
//                    // 인트로 실행 후 바로 MainActivity로 넘어감.
//                    intent = new Intent(getApplicationContext(), MainActivity.class);
//                    Thread.currentThread().interrupt();
//                }
//                else {
//                    // 회원가입 페이지로 이동
//                    //intent = new Intent(getApplicationContext(), SignupActivity.class);
//                    Thread.currentThread().interrupt();
//                }
//                startActivity(intent);
//                finish();
//            }
            }
        };

    @Override
    protected void onStart() {
        super.onStart();
        //DeviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
//        asyncTask = new PersonTableAsyncTask().execute(DeviceID);
//        while(true){
//            Log.d("aa", String.valueOf(asyncTask.getStatus()));
//            if(asyncTask.getStatus()== AsyncTask.Status.FINISHED) {
//                try {
//                    result = asyncTask.get();
//                    // 처음 입장시
//                    if (!result.equals(Constants.DATABASE_EXIST)) {
//                        // 회원가입 페이지로 이동
//                        intent = new Intent(getApplicationContext(), SignupActivity.class);
//                    } else {
//                        // 인트로 실행 후 바로 MainActivity로 넘어감.
//                        intent = new Intent(getApplicationContext(), MainActivity.class);
//                    }
//                    startActivity(intent);
//                    finish();
//                    break;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
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
