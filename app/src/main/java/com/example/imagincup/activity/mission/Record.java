package com.example.imagincup.activity.mission;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imagincup.MainActivity;
import com.example.imagincup.R;
import com.example.imagincup.activity.mission.Adapter.Audio;
import com.example.imagincup.model.MissionState;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Record extends AppCompatActivity {

    private TextView timeText;
    private TextView missionTitle;
    private TextView noticeText;
    private Button clearButton;
    private ImageButton recordButton;
    private Button playButton;

    // 안드로이드 타이머 기능
    private Chronometer chronometer;
    private long pauseOffset;

    /**오디오 파일 관련 변수*/
    // 오디오 권한
    private String recordPermission = Manifest.permission.RECORD_AUDIO;
    private int PERMISSION_CODE = 21;

    // 오디오 파일 녹음 관련 변수
    private MediaRecorder mediaRecorder;
    private String audioFileName; // 오디오 녹음 생성 파일 이름
    private boolean isRecording = false;    // 현재 녹음 상태를 확인하기 위함.
    private Uri audioUri = null; // 오디오 파일 uri

    // 오디오 파일 재생 관련 변수
    private MediaPlayer mediaPlayer = null;
    private Boolean isPlaying = false;
    ImageView playIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        init();
    }

    private void init(){
        pauseOffset = 0;
        chronometer =  findViewById(R.id.record_chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        missionTitle = findViewById(R.id.record_title);
        noticeText = findViewById(R.id.record_text_button_notice);
        clearButton = findViewById(R.id.record_button_clear);
        clearButton.setVisibility(View.GONE);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                MissionState.getInstance().setDone(true);
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        playButton = findViewById(R.id.record_button_play);
        playButton.setVisibility(View.GONE);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uriName = String.valueOf(audioUri);
                File file = new File(uriName);
                if(isPlaying){
                    stopAudio();
                    playButton.setCompoundDrawablesRelative(getDrawable(R.drawable.play),null,null,null);
                } else{
                    playAudio(file);
                    playButton.setCompoundDrawablesRelative(getDrawable(R.drawable.stop),null,null,null);
                }
            }
        });
        recordButton = findViewById(R.id.audioRecordImageBtn);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRecording){
                    chronometer.stop();
                    isRecording = false;
                    recordButton.setImageDrawable(getDrawable(R.drawable.mic));
                    noticeText.setVisibility(View.GONE);
                    clearButton.setVisibility(View.VISIBLE);
                    playButton.setVisibility(View.VISIBLE);
                    stopRecording();
                }
                else{
                    chronometer.start();
                    isRecording = true;
                    recordButton.setImageDrawable(getDrawable(R.drawable.stop));
                    startRecording();
                }
            }
        });
        checkAudioPermission();

    }

    // 오디오 파일 권한 체크
    private boolean checkAudioPermission() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), recordPermission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{recordPermission}, PERMISSION_CODE);
            return false;
        }
    }

    // 녹음 시작
    private void startRecording() {
        //파일의 외부 경로 확인
        String recordPath = getExternalFilesDir("/").getAbsolutePath();
        // 파일 이름 변수를 현재 날짜가 들어가도록 초기화. 그 이유는 중복된 이름으로 기존에 있던 파일이 덮어 쓰여지는 것을 방지하고자 함.
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        audioFileName = recordPath + "/" +"RecordExample_" + timeStamp + "_"+"audio.mp4";

        //Media Recorder 생성 및 설정
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(audioFileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //녹음 시작
        mediaRecorder.start();
    }

    // 녹음 종료
    private void stopRecording() {
        // 녹음 종료 종료
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;

        // 파일 경로(String) 값을 Uri로 변환해서 저장
        //      - Why? : 리사이클러뷰에 들어가는 ArrayList가 Uri를 가지기 때문
        //      - File Path를 알면 File을  인스턴스를 만들어 사용할 수 있기 때문
        audioUri = Uri.parse(audioFileName);

    }

    // 녹음 파일 재생
    private void playAudio(File file) {
        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(file.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        isPlaying = true;

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopAudio();
            }
        });

    }

    // 녹음 파일 중지
    private void stopAudio() {
        isPlaying = false;
        mediaPlayer.stop();
    }

}