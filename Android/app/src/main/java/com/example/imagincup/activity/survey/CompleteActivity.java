package com.example.imagincup.activity.survey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.imagincup.IntroActivity;
import com.example.imagincup.MainActivity;
import com.example.imagincup.R;

public class CompleteActivity extends AppCompatActivity {

    Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);

        doneButton = findViewById(R.id.complete_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}