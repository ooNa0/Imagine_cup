package com.example.imagincup.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imagincup.Constants;
import com.example.imagincup.IntroActivity;
import com.example.imagincup.R;
import com.example.imagincup.back.DTO.DTOPerson;
import com.example.imagincup.back.DTO.DTORecord;
import com.example.imagincup.back.RecycleViewAdapter;
import com.example.imagincup.back.task.InsertSurveyThread;
import com.example.imagincup.back.task.SelectRecordDayThread;
//import com.example.imagincup.back.RecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class DayRecordFragment extends Fragment{

    private List<DTORecord> listRecord;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DTOPerson dtoPerson;
    private String personID;

    private Thread recordDayThread;

    private String selectYear;
    private String selectMonth;

    private RecycleViewAdapter adapter;

    public DayRecordFragment() { }

    //private Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listRecord = new ArrayList<DTORecord>();

        if (getArguments() != null) {
            dtoPerson = (DTOPerson) getArguments().getSerializable(Constants.DATABASE_PERSON_TABLENAME);
            personID = String.valueOf(dtoPerson.getPersonId());
            selectYear = getArguments().getString("Year");
            selectMonth = getArguments().getString("Month");

            recordDayThread = new SelectRecordDayThread(personID, listRecord, selectYear, selectMonth);
            recordDayThread.start();
            try {
                recordDayThread.join();
                Log.d("aaaaa2222222222-----------oooooooooookkkkkkkkkkkk", String.valueOf(listRecord));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.d("aaaaaa0----------------------oooooooooookkkkkkkkkkkk", String.valueOf(listRecord));

            adapter = new RecycleViewAdapter(getContext(), listRecord, 1, String.valueOf(dtoPerson.getPersonId()));
        }
        //listRecord = recordDayThread.getlistRecord();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_day, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(this.getContext());

        if(dtoPerson != null){

            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
        Log.d("aaaaaa0----------------------", String.valueOf(dtoPerson));


        //RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_id);
        //RecycleViewAdapter recyclerViewAdapter = new RecycleViewAdapter(getContext(),listRecord);
//        RecycleViewAdapter recyclerViewAdapter = new RecycleViewAdapter(getApplicationContext(),listRecord);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
//        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

}
