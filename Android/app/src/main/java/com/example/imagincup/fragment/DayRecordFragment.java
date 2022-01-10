package com.example.imagincup.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imagincup.R;
import com.example.imagincup.back.DTORecord;
//import com.example.imagincup.back.RecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class DayRecordFragment extends Fragment{

    List<DTORecord> listRecord;

    public DayRecordFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listRecord = new ArrayList<>();
        //listRecord.add(new DTORecord());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_day, container, false);

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
