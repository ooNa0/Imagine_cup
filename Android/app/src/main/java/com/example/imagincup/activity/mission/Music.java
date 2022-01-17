package com.example.imagincup.activity.mission;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.imagincup.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class Music extends YouTubeBaseActivity {

    //객체 선언
    private static String API_KEY = "AIzaSyC6FF8AVtTVOo1P2SaLMUM79zIGH5pvsJg";
    private static String VIDEO_ID = "Gqfk5sr9fpw";
    private static String TAG = "YOUTUBE";

    YouTubePlayerView youTubePlayerView;
    YouTubePlayer player;
    Button playButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        initPlayer();
        playButton = findViewById(R.id.youtubeBtn);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideo();
            }
        });
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youTubePlayerView);

    }

    private void playVideo(){
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