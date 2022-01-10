package com.example.imagincup;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.imagincup.back.PersonTableAsyncTask;

public class IntroActivity extends AppCompatActivity {
    private String DeviceID;
    private String result;
    private Intent intent;

    // 왜 화면이 안나오지..?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
    }

    protected void onResume(){
        super.onResume();
        DeviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        try {
            result = new PersonTableAsyncTask().execute(DeviceID).get();
            // 처음 입장시
            if(!result.equals(Constants.DATABASE_EXIST)){
                // 회원가입 페이지로 이동
                intent = new Intent(getApplicationContext(), SignupActivity.class);
            }
            else{
                // 인트로 실행 후 바로 MainActivity로 넘어감.
                intent = new Intent(getApplicationContext(), MainActivity.class);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
