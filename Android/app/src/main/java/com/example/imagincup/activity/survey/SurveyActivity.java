package com.example.imagincup.activity.survey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.imagincup.R;

import java.util.ArrayList;

public class SurveyActivity extends AppCompatActivity {

    private ArrayList<Survey> surveyArrayList;
    private SurveyAdapter surveyAdapter;
    private int count = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        RecyclerView recyclerView =(RecyclerView) findViewById(R.id.survey_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        surveyArrayList = new ArrayList<>();

        surveyAdapter = new SurveyAdapter(surveyArrayList);
        recyclerView.setAdapter(surveyAdapter);

        addList();
    }

    private void addList(){
        Survey survey1 = new Survey("What did you not plan to do today and why?");
        Survey survey2 = new Survey("What did you not plan to do today and why?");
        Survey survey3 = new Survey("What did you not plan to do today and why?");

        surveyArrayList.add(survey1);
        surveyArrayList.add(survey2);
        surveyArrayList.add(survey3);

        surveyAdapter.notifyDataSetChanged();
    }
}