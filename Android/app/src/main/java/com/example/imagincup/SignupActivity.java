package com.example.imagincup;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.imagincup.activity.survey.SurveyActivity;
import com.example.imagincup.back.IntroThread;
import com.example.imagincup.back.task.CreatePersonDataAsyncTask;

import java.util.concurrent.ExecutionException;

public class SignupActivity extends AppCompatActivity {

    private EditText editText;
    private String name;
    private String DeviceID;
    private String entryTime;
    private Intent intent;
    private String resultPersonID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void onclick(View view){

        DeviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        editText = (EditText)findViewById(R.id.input_name);
        name = editText.getText().toString();

        try {
            resultPersonID = new CreatePersonDataAsyncTask().execute(name, DeviceID).get();
            Log.d("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb", resultPersonID);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        //intent = new Intent(getApplicationContext(), IntroActivity.class);
        intent = new Intent(getApplicationContext(), SurveyActivity.class);
        intent.putExtra("PersonID", resultPersonID);
        startActivity(intent);
        finish();

        // 검사지 넣기!!!!!!!!!!!!!!!

    }
}
