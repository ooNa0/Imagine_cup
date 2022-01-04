package com.example.imagincup;

import android.os.AsyncTask;

import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.models.CosmosDatabaseResponse;

public class CosmosAsyncTask extends AsyncTask<String,Void,String> {

    private String ACCOUNT_HOST = "https://imagine-cup.documents.azure.com:443/";
    private String ACCOUNT_KEY = "FRATiAMOprn8AcscBe5JGsxF1ewPFDiNm8aUeT7iEHAcorV5KWKKWQzTAvMQOcySICb2Aa9kzJ2EgsfemHffQQ";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        CosmosClient cosmosClient = new CosmosClientBuilder()
                .endpoint(ACCOUNT_HOST)
                .key(ACCOUNT_KEY)
                .buildClient();
        CosmosDatabaseResponse databaseResponse = cosmosClient.createDatabaseIfNotExists("testDBcreate");
        System.out.println("Checking database 2222222222222222222222222222222222222222222");
        CosmosDatabase database = cosmosClient.getDatabase(databaseResponse.getProperties().getId());
        System.out.println("Checking database 2222222222222222222222222222222222222222222");

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    private void getStartDatabase() throws Exception{


        // 비동기
        CosmosAsyncClient cosmosAsyncClient = new CosmosClientBuilder()
                .endpoint(ACCOUNT_HOST)
                .key(ACCOUNT_KEY)
                .buildAsyncClient();

        System.out.println("Checking database 2222222222222222222222222222222222222222222");
        // 동기
        CosmosClient cosmosClient = new CosmosClientBuilder()
                .endpoint(ACCOUNT_HOST)
                .key(ACCOUNT_KEY)
                .buildClient();

        System.out.println("Checking database 2222222222222222222222222222222222222222222");
        // 컨테이너 참조 및 데베 생성
//        cosmosAsyncClient.createDatabaseIfNotExists("testDBcreate^^")
//                .map(databaseResponse -> cosmosAsyncClient.getDatabase(databaseResponse.getProperties().getId()))
//                .subscribe(database -> System.out.printf("Created database '%s'.%n", database.getId()));

        CosmosDatabaseResponse databaseResponse = cosmosClient.createDatabaseIfNotExists("testDBcreate");
        System.out.println("Checking database 2222222222222222222222222222222222222222222");
        CosmosDatabase database = cosmosClient.getDatabase(databaseResponse.getProperties().getId());
        System.out.println("Checking database 2222222222222222222222222222222222222222222");

        System.out.println("Checking database " + database.getId() + " completed!\n");

    }
}
