package com.example.imagincup.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imagincup.Constants;
import com.example.imagincup.R;
import com.example.imagincup.back.DTO.DTOPerson;
import com.example.imagincup.back.DTO.DTORecord;
import com.example.imagincup.back.RecycleViewAdapter;
import com.example.imagincup.back.task.record.SelectRecordDayThread;

import java.util.ArrayList;
import java.util.List;

public class DayRecordFragment extends Fragment{

    private List<DTORecord> listRecord;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DTOPerson dtoPerson;
    private String personID;

    private SelectRecordDayThread recordDayThread;

    private String selectYear;
    private String selectMonth;

    private RecycleViewAdapter adapter;

    public DayRecordFragment() { }

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
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            adapter = new RecycleViewAdapter(getContext(), listRecord, 1, dtoPerson);
        }
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
        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

}
