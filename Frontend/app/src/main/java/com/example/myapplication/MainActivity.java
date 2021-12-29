package com.example.myapplication;

import androidx.support.annotation.NonNull;
import androidx.support.design.widget.BottomNavigationView;
import androidx.support.v4.app.Fragment;
import androidx.support.v7.app.AppCompatActivity;
import androidx.os.Bundle;
import androidx.view.MenuItem;
import androidx.widget.LinearLayout;

import com.example.myapplication.fragment.HomeFragment;
import com.example.myapplication.fragment.MissionFragment;
import com.example.myapplication.fragment.MyPageFragment;
import com.example.myapplication.fragment.RecordFragment;

public class MainActivity extends AppCompatActivity {

    LinearLayout home_layout;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
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
        getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, fragment).commit();
    }
}