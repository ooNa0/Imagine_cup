package com.example.imagincup.activity.mission;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.imagincup.R;
import com.example.imagincup.model.MissionState;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.Random;


public class Music extends YouTubeBaseActivity {

    //객체 선언
    private static String API_KEY = "AIzaSyC6FF8AVtTVOo1P2SaLMUM79zIGH5pvsJg";
    private static String VIDEO_ID = "Gqfk5sr9fpw";
    private String[] VIDEO_IDs = new String[]{"Gqfk5sr9fpw","MZUR479P2Cw","BR4WG1XlLZw"};
    private static String TAG = "YOUTUBE";
    private String videoId;
    private Random random;

    YouTubePlayerView youTubePlayerView;
    YouTubePlayer player;
    Button playButton;
    Button clearButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        initPlayer();
        random = new Random();
        videoId = VIDEO_IDs[random.nextInt(3)];
        playButton = findViewById(R.id.youtubeBtn);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideo(videoId);
            }
        });
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youTubePlayerView);
        clearButton = findViewById(R.id.music_button_clear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MissionState.getInstance().setDone(true);
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

    private void playVideo(String VIDEO_ID){
        if(player != null){
            if(player.isPlaying()){
                player.pause();
            }
            player.cueVideo(VIDEO_ID);
        }
    }

    private void initPlayer(){
        youTubePlayerView = findViewById(R.id.youTubePlayerView);
        youTubePlayerView.initialize(API_KEY,new YouTubePlayer.OnInitializedListener(){

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                player = youTubePlayer;

                player.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {

                    }

                    @Override
                    public void onLoaded(String s) {
                        Log.d(TAG,"onLoaded: " + s);
                        player.play();
                    }

                    @Override
                    public void onAdStarted() {

                    }

                    @Override
                    public void onVideoStarted() {

                    }

                    @Override
                    public void onVideoEnded() {

                    }

                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {
                        Log.d(TAG, "onError : " + errorReason);
                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(TAG,"onFailure : " +  youTubeInitializationResult);
            }
        });
    }


}