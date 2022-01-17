package com.example.imagincup.activity.mission;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.imagincup.MainActivity;
import com.example.imagincup.MissionActivity;
import com.example.imagincup.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Camera extends AppCompatActivity {

    private Button pictureButton;
    private ImageView  imageView;
    private Button clearButton;

    private boolean isDone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        init();
        Intent intent = getIntent();
        isDone = intent.getBooleanExtra("is_done",false);
    }


    private void init(){
        pictureButton = findViewById(R.id.camera_button_take_picture);
        pictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,1);
            }
        });
        imageView = findViewById(R.id.camera_imageview);

        clearButton = findViewById(R.id.camera_button_clear);
        clearButton.setVisibility(View.INVISIBLE);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDone = true;
                Intent intent = new Intent();
                intent.putExtra("is_done",true);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            Bundle extras =data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(bitmap);

            pictureButton.setText("Retake");
            clearButton.setVisibility(View.VISIBLE);
    }
}