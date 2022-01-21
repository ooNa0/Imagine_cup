package com.example.imagincup;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.example.imagincup.back.DTO.DTORecord;
import com.example.imagincup.back.task.EmotionAsyncTask;
import com.example.imagincup.back.task.record.InsertRecordDataAsyncTask;
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

    private String question;
    private String answer;
    private String emotionIcon;
    private String emotionParcent;
    private DTORecord dtoRecord;
    private DTOPerson dtoPerson;
    private Integer dtoPersonID;

    private String emotionState;
    private JSONObject dataJSON;

    private Boolean isVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_result);

        Intent intent = getIntent();
        dtoPerson = (DTOPerson)(intent.getSerializableExtra("Person"));
        isVisible = intent.getBooleanExtra("isVisible", true);
        dtoPersonID = dtoPerson.getPersonId();

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


        dtoRecord = (DTORecord) intent.getSerializableExtra(("Record"));
        if(dtoRecord != null){
            setPieElement(dtoRecord.getQuestion(), dtoRecord.getAnswer(), dtoRecord.getMission(), dtoRecord.getEmotion(), Double.valueOf(dtoRecord.getEmotionScore()), isVisible);
            dayTextView.setText(dtoRecord.getRecordDay());
        }

        SwitchVisibility(isVisible);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.save_answer:
            {
                //progressDialog.show();
                ManageEmotionState();
                //progressDialog.dismiss();
                break;
            }
            case R.id.go_misson_button:
            {
                Intent intent = new Intent(this, MissionActivity.class);
                startActivity(intent);
                finish();
               break;
            }
        }
    }

    private void ManageEmotionState(){
        dataJSON = null;
        try {
            question = (String) questionTextView.getText();
            answer = String.valueOf(answerEditText.getText());

            dataJSON = new EmotionAsyncTask().execute(answer).get();
            emotionParcent =  String.format("%.0f", dataJSON.getJSONObject("confidenceScores").getDouble(dataJSON.getString("sentiment"))*100);
            emotionState = dataJSON.getString("sentiment");

            // 해당 감정에 대한 우울증 측정 알고리즘
            // 해당 질문에 대한 우울증 값 넣기
            // personDTO에 우울증 값 반영(저장된 우울증 값에 따라)


            new InsertRecordDataAsyncTask().execute(dtoPerson.getPersonId().toString(), question, "1", answer, emotionState, emotionParcent, dtoPerson.getPersonDepressionScore().toString()).get();

            setPieElement(question, answer, Constants.MISSION_DEFAULT, emotionState, Double.valueOf(emotionParcent), true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setPieElement(String elementQuestion, String elementAnswer, String elementMission, String elementEmotionState, Double elementEmotionParcent, Boolean elementIsVisiable){
        questionTextView.setText(elementQuestion);
        answerTextView.setText(elementAnswer);

        PieCharManagement(elementMission, elementEmotionParcent, getEmotionStateIcon(elementEmotionState));
        SwitchVisibility(elementIsVisiable);
    }

    String getEmotionStateIcon(String emotionStateString){
        if(emotionStateString.equals("positive")){
            return "😀";
        }
        else if(emotionStateString.equals("negative")){
            return "😢";
        }
        else if(emotionStateString.equals("neutral")){
            return "😶";
        }
        return "";
    }

    public void SwitchVisibility(boolean isExistText){
        if(isExistText){
            // 만약에 텍스트가 존재한다면, editText가 보이지 말아야 한다면
            answerEditText.setVisibility(View.GONE);
            saveButton.setVisibility(View.INVISIBLE);
            answerTextView.setVisibility(View.VISIBLE);
            resultLinearLayout.setVisibility(View.VISIBLE);
            if(dtoRecord == null){
                goMissionButton.setVisibility(View.VISIBLE);
            }
            else{
                goMissionButton.setVisibility(View.GONE);
            }
        }
        else{
            answerEditText.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.VISIBLE);
            answerTextView.setVisibility(View.GONE);
            resultLinearLayout.setVisibility(View.GONE);
            goMissionButton.setVisibility(View.GONE);
        }
    }

    public void PieCharManagement(String isMissionComplete, Double parcent, String icon){

        Integer parcentInt = parcent.intValue();
        // data set

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();


        yValues.add(new PieEntry(parcentInt,""));
        yValues.add(new PieEntry(100 - parcentInt,""));

        PieDataSet dataSet = new PieDataSet(yValues,"emotions");
        emotionIconTextView.setText(icon);
        emtionParcentTextView.setText(String.valueOf(parcent) + '%');
        //ataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        //dataSet.setColor(getResources().getColor(R.color.black));
        //dataSet.setGradientColor(Color.parseColor("88BEB7"), Color.parseColor("26746C"));
        dataSet.setColors(getResources().getColor(R.color.mission_button), getResources().getColor(R.color.white_gray));
        PieData data = new PieData(dataSet);


        ArrayList<PieEntry> yValueMission = new ArrayList<PieEntry>();
        yValueMission.add(new PieEntry(100,""));

        PieDataSet dataSetMission = new PieDataSet(yValueMission,"mission");
        if(isMissionComplete.equals(Constants.MISSION_DEFAULT))
        {
            dataSetMission.setColor(getResources().getColor(R.color.lite_blue));
            checkMissonTextView.setText("✔");
        }
        else{
            dataSetMission.setColor(getResources().getColor(R.color.white_gray));
            checkMissonTextView.setText("-");
        }
        PieData dataMission = new PieData(dataSetMission);

        // 퍼센트 지우기
        data.setDrawValues(false);
        dataMission.setDrawValues(false);

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

        pieEmotionChart.setData(dataMission);
        pieChart.setData(data);
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
