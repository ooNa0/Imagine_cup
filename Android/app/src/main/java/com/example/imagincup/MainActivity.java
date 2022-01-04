package com.example.imagincup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.models.CosmosDatabaseResponse;
import com.example.imagincup.fragment.HomeFragment;
import com.example.imagincup.fragment.MissionFragment;
import com.example.imagincup.fragment.MyPageFragment;
import com.example.imagincup.fragment.RecordFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.tab_home:
                    changeFragment(new HomeFragment());
                    return true;

                case R.id.tab_records:
                    changeFragment(new RecordFragment());
                    //new CosmosAsyncTask().execute();
                    try {
                        getStartDatabase();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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

    private void getStartDatabase() throws Exception{

        String ACCOUNT_HOST = "https://imagine-cup.documents.azure.com:443/";
        String ACCOUNT_KEY = "kIvXotbe07IrUvzVQskbyX8dcNybQvjTnxLqVu39xUHiYs6MDyNcYwLpT8vUoTN0SU5DpUXaWbr3A689idzWrA==";

        // 비동기
//        CosmosAsyncClient cosmosAsyncClient = new CosmosClientBuilder()
//                .endpoint(ACCOUNT_HOST)
//                .key(ACCOUNT_KEY)
//                .buildAsyncClient();

        System.out.println("Checking database 2222222222222222222222222222222222222222222");
        // 동기
        CosmosClient cosmosClient = new CosmosClientBuilder()
                .endpoint(ACCOUNT_HOST)
                .key(ACCOUNT_KEY)
                .buildClient();

        System.out.println("Checking database 111111111111111111111111");
        // 컨테이너 참조 및 데베 생성
//        cosmosAsyncClient.createDatabaseIfNotExists("testDBcreate^^")
//                .map(databaseResponse -> cosmosAsyncClient.getDatabase(databaseResponse.getProperties().getId()))
//                .subscribe(database -> System.out.printf("Created database '%s'.%n", database.getId()));

        CosmosDatabaseResponse databaseResponse = cosmosClient.createDatabaseIfNotExists("testDBcreate");
        System.out.println("Checking database12321312312312313213");
        CosmosDatabase database = cosmosClient.getDatabase(databaseResponse.getProperties().getId());
        System.out.println("Checking database444444444444444444444422");

        System.out.println("Checking database " + database.getId() + " completed!\n");

    }
}