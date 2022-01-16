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
    private static String TAG = "Music";

    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener listener;
    Button playButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        playButton = findViewById(R.id.youtubeBtn);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youTubePlayerView);
        listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(VIDEO_ID);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youTubePlayerView.initialize(API_KEY,listener);
            }
        });

    }


}