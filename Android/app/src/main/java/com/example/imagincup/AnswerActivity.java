package com.example.imagincup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.imagincup.back.DTO.DTOPerson;
import com.example.imagincup.back.task.EmotionAsyncTask;
import com.example.imagincup.back.task.InsertRecordDataAsyncTask;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.json.JSONObject;

import java.util.ArrayList;

public class AnswerActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;

    // progressdialog
    private ProgressDialog progressDialog;

    // 텍스트
    private TextView dayTextView;
    private TextView questionTextView;
    private TextView answerTextView;
    private EditText answerEditText;
    private Button saveButton;
    private LinearLayout resultLinearLayout;

    // 차트
    private PieChart pieChart;
    private PieChart pieEmotionChart;

    // 체크
    private TextView checkMissonTextView;
    private TextView emotionIconTextView;
    private TextView emtionParcentTextView;

    private Button goMissionButton;

    private String answer;

    private DTOPerson dtoPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_result);

        Intent intent = getIntent();
        dtoPerson = (DTOPerson)intent.getSerializableExtra("Person");

        dayTextView = findViewById(R.id.question_day);
        questionTextView = findViewById(R.id.question_);
        answerTextView = findViewById(R.id.question_answer);
        answerEditText = findViewById(R.id.question_answer_edit);
        saveButton = findViewById(R.id.save_answer);
        resultLinearLayout = findViewById(R.id.result_layout);

        pieChart = (PieChart)findViewById(R.id.daily_emotions_chart);
        pieEmotionChart = (PieChart)findViewById(R.id.daily_mission_achievement_chart);

        checkMissonTextView = findViewById(R.id.check_misson);
        emotionIconTextView = findViewById(R.id.emotion_icon);
        emtionParcentTextView = findViewById(R.id.emotion_parcent);

        goMissionButton = findViewById(R.id.go_misson_button);

        saveButton.setOnClickListener(this);
        goMissionButton.setOnClickListener(this);

        // progressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);

        // back
        toolbar = findViewById(R.id.activity_answer_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        SwitchVisibility(false);



        // data set

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();
        yValues.add(new PieEntry(90,""));
        yValues.add(new PieEntry(10,""));

        PieDataSet dataSet = new PieDataSet(yValues,"emotions");
        //ataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        //dataSet.setColor(getResources().getColor(R.color.black));
        //dataSet.setGradientColor(Color.parseColor("88BEB7"), Color.parseColor("26746C"));
        dataSet.setColors(getResources().getColor(R.color.mission_button), getResources().getColor(R.color.white_gray));
        PieData data = new PieData(dataSet);

        // 퍼센트 지우기
        data.setDrawValues(false);

        //////////// design

        // 안에 구멍
        pieChart.setDrawHoleEnabled(true);

        // 안에 구멍 투명하게
        pieEmotionChart.setHoleColor(getColor(R.color.transparent));
        pieChart.setHoleColor(getColor(R.color.transparent));

        // 하단 x-Values List 안보이게
        pieEmotionChart.getLegend().setEnabled(false);
        pieChart.getLegend().setEnabled(false);

        // 설명 안보이게
        pieEmotionChart.getDescription().setEnabled(false);
        pieChart.getDescription().setEnabled(false);

        // 터치 지켜
        pieEmotionChart.setTouchEnabled(false);
        //pieChart.setTouchEnabled(false);

        // 끝이 동그랗게?
        //pieEmotionChart.setDrawRoundedSlices(true);
        pieChart.setDrawRoundedSlices(true);

        // 구멍 크기
        pieEmotionChart.setHoleRadius(93);
        pieChart.setHoleRadius(93);

        pieEmotionChart.setDrawEntryLabels(false);

        //애니메이션
        pieEmotionChart.animateY(1000, Easing.EaseInOutCubic);
        pieChart.animateY(1000, Easing.EaseInOutCubic);

        pieEmotionChart.setData(data);
        pieChart.setData(data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.save_answer:
            {
                progressDialog.show();

                SwitchVisibility(true);
                answerTextView.setText(answerEditText.getText());
                JSONObject data = null;
                try {

                    String task = new InsertRecordDataAsyncTask().execute(dtoPerson.getPersonId(), dtoPerson.getRecordId()).get();

                    data = new EmotionAsyncTask().execute().get();
                    JSONObject parcent = data.getJSONObject("confidenceScores");
                    if(data.getString("sentiment").equals("positive")){
                        emotionIconTextView.setText("😀");
                    }
                    else if(data.getString("sentiment").equals("negative")){
                        emotionIconTextView.setText("😢");
                    }
                    else{
                        emotionIconTextView.setText("😶");
                    }
                    emtionParcentTextView.setText(String.valueOf(parcent.getDouble(data.getString("sentiment"))*100) + '%');

                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
                break;
            }
            case R.id.go_misson_button:
            {
                //getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, fragment).commit();
                Intent intent = new Intent(this, MissionActivity.class);

                //Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                //getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new MissionFragment()).commit();
                finish();
               break;
            }
        }
    }

    public void SwitchVisibility(boolean isExistText){
        if(isExistText){
            // 만약에 텍스트가 존재한다면, editText가 보이지 말아야 한다면
            answerEditText.setVisibility(View.GONE);
            saveButton.setVisibility(View.INVISIBLE);
            answerTextView.setVisibility(View.VISIBLE);
            resultLinearLayout.setVisibility(View.VISIBLE);
            goMissionButton.setVisibility(View.VISIBLE);

        }
        else{
            answerEditText.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.VISIBLE);
            answerTextView.setVisibility(View.GONE);
            resultLinearLayout.setVisibility(View.GONE);
            goMissionButton.setVisibility(View.GONE);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
