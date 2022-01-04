package com.example.imagincup.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.example.imagincup.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecordFragment newInstance(String param1, String param2) {
        RecordFragment fragment = new RecordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false);
    }

    private void getStartData() throws Exception{

        String ACCOUNT_HOST = "https://imagine-cup.documents.azure.com:443/";
        String ACCOUNT_KEY = "FRATiAMOprn8AcscBe5JGsxF1ewPFDiNm8aUeT7iEHAcorV5KWKKWQzTAvMQOcySICb2Aa9kzJ2EgsfemHffQQ";

        // 비동기
        CosmosAsyncClient cosmosAsyncClient = new CosmosClientBuilder()
                .endpoint(ACCOUNT_HOST)
                .key(ACCOUNT_KEY)
                .buildAsyncClient();

        // 동기
        CosmosClient cosmosClient = new CosmosClientBuilder()
                .endpoint(ACCOUNT_HOST)
                .key(ACCOUNT_KEY)
                .buildClient();

        // 컨테이너 참조 및 데베 생성
        cosmosAsyncClient.createDatabaseIfNotExists("testDBcreate^^")
                // TIP: Our APIs are Reactor Core based, so try to chain your calls
                .map(databaseResponse -> cosmosAsyncClient.getDatabase(databaseResponse.getProperties().getId()))
                .subscribe(database -> System.out.printf("Created database '%s'.%n", database.getId()));
    }
}