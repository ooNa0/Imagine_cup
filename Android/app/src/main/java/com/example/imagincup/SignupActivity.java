package com.example.imagincup;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.imagincup.back.task.CreatePersonDataAsyncTask;

public class SignupActivity extends AppCompatActivity {

    private EditText editText;
    private String name;
    private String DeviceID;
    private String entryTime;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void onclick(View view){

        DeviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        editText = (EditText)findViewById(R.id.input_name);
        name = editText.getText().toString();

        // 나중에 가입날짜 만들기
//        Calendar cal = Calendar.getInstance();
//        Date nowDate = cal.getTime();
//        SimpleDateFormat dataformat = new SimpleDateFormat("yyyy/MM/dd");
//        String now = dataformat.format(nowDate);
//        entryTime=now;

        new CreatePersonDataAsyncTask().execute(name, DeviceID);

        // 검사지 넣기!!!!!!!!!!!!!!!

        intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
