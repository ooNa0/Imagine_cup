package com.example.imagincup.activity.mission;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.imagincup.R;

public class Music extends AppCompatActivity {

    Button button;
    MediaPlayer mediaPlayer;
    boolean flag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        mediaPlayer = MediaPlayer.create(this,R.raw.music1);
        button = findViewById(R.id.music_play_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){
                    mediaPlayer.start();
                    flag=false;
                }
                else{
                    mediaPlayer.pause();
                    flag=true;
                }
            }
        });
    }
}