package com.example.imagincup.activity.survey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.imagincup.IntroActivity;
import com.example.imagincup.R;
import com.example.imagincup.back.task.InsertSurveyThread;

import java.util.ArrayList;
import java.util.List;

public class SurveyActivity extends AppCompatActivity {

    private ArrayList<Survey> surveyArrayList;
    private SurveyAdapter surveyAdapter;
    private int count = -1;
    private Button nextButton;
    private Intent intent;
    private String personID;

    private ProgressDialog progressDialog;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        intent = getIntent();
        personID = intent.getStringExtra("PersonID");

        RecyclerView recyclerView =(RecyclerView) findViewById(R.id.survey_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        surveyArrayList = new ArrayList<>();

        surveyAdapter = new SurveyAdapter(surveyArrayList);
        recyclerView.setAdapter(surveyAdapter);

        addList();

        nextButton = findViewById(R.id.survey_next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //progressDialog = new ProgressDialog(SurveyActivity.this);
                //progressDialog.show();
                thread = new InsertSurveyThread(personID, surveyArrayList);
                thread.start();
                try {
                    thread.join();
                    //progressDialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(), CompleteActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void addList(){
        Survey survey1 = new Survey("I was bothered by things that usually don't bother me.");
        Survey survey2 = new Survey("I did not feel like eating; my appetite was poor.");
        Survey survey3 = new Survey("I felt that I could not shake off the blues even with help from my family or friends.");
        Survey survey4 = new Survey("I felt I was just as good as other people.");
        Survey survey5 = new Survey("I had trouble keeping my mind on what I was doing.");
        Survey survey6 = new Survey("I felt depressed.");
        Survey survey7 = new Survey("I felt that everything I did was an effort. ");
        Survey survey8 = new Survey("I felt hopeful about the future. ");
        Survey survey9 = new Survey("I thought my life had been a failure.");
        Survey survey10 = new Survey("I felt fearful.");
        Survey survey11 = new Survey("My sleep was restless.");
        Survey survey12 = new Survey("I was happy.");
        Survey survey13 = new Survey("I talked less than usual.");
        Survey survey14 = new Survey("I felt lonely.");
        Survey survey15 = new Survey("People were unfriendly");
        Survey survey16 = new Survey("I enjoyed life.");
        Survey survey17 = new Survey("I had crying spells.");
        Survey survey18 = new Survey("I felt sad.");
        Survey survey19 = new Survey("I felt that people disliked me.");
        Survey survey20 = new Survey("I could not get going. ");

        surveyArrayList.add(survey1);
        surveyArrayList.add(survey2);
        surveyArrayList.add(survey3);
        surveyArrayList.add(survey4);
        surveyArrayList.add(survey5);
        surveyArrayList.add(survey6);
        surveyArrayList.add(survey7);
        surveyArrayList.add(survey8);
        surveyArrayList.add(survey9);
        //surveyArrayList.add(new Survey("I thought my life had been a failure."));
        surveyArrayList.add(survey10);
        surveyArrayList.add(survey11);
        surveyArrayList.add(survey12);
        surveyArrayList.add(survey13);
        surveyArrayList.add(survey14);
        surveyArrayList.add(survey15);
        surveyArrayList.add(survey16);
        surveyArrayList.add(survey17);
        surveyArrayList.add(survey18);
        surveyArrayList.add(survey19);
        surveyArrayList.add(survey20);

        surveyAdapter.notifyDataSetChanged();
    }
}