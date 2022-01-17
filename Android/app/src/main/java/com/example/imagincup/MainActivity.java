package com.example.imagincup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.AsyncTask;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.imagincup.back.DTO.DTOPerson;
import com.example.imagincup.fragment.HomeFragment;
import com.example.imagincup.fragment.MissionFragment;
import com.example.imagincup.fragment.MyPageFragment;
import com.example.imagincup.fragment.RecordFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    LinearLayout home_layout;
    BottomNavigationView bottomNavigationView;

    private DTOPerson dtoPerson;
    private Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        dtoPerson = (DTOPerson)intent.getSerializableExtra("Person");
        bundle.putSerializable("Person", dtoPerson);


        init();
        SettingListener();
        bottomNavigationView.setSelectedItemId(R.id.tab_home);

    }

    private void init(){
        home_layout = findViewById(R.id.home_layout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    private void SettingListener(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new TabSelectedListener());
    }

    class TabSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.tab_home:
                    changeFragment(new HomeFragment());
                    return true;

                case R.id.tab_records:
                    changeFragment(new RecordFragment());
                    return true;

                case R.id.tab_mission:
                    changeFragment(new MissionFragment());
                    return true;

                case R.id.tab_mypage:
                    changeFragment(new MyPageFragment());
                    return true;
            }
            return false;
        }
    }

    private void changeFragment(Fragment fragment){
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, fragment).commit();
    }

}