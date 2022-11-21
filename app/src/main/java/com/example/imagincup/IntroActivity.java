package com.example.imagincup;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.imagincup.back.DTO.DTOPerson;
import com.example.imagincup.back.IntroThread;

public class IntroActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private String DeviceID;
    private int result;
    private Intent intent;
    private DTOPerson dtoPerson;

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
                        dtoPerson = introThread.getResultDataSet();
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("Person", dtoPerson);
                        intent.putExtra("fragment", R.id.tab_home);
                    }
                    else {
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
