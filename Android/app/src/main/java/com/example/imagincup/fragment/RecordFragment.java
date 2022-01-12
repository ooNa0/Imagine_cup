package com.example.imagincup.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.imagincup.Constants;
import com.example.imagincup.R;
import com.example.imagincup.back.RecycleViewAdapter;

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

    private ImageButton changeDayMonthButton;
    private FragmentTransaction fragmentTransaction;
    private DayRecordFragment dayRecordFragment;
    private MonthRecordFragment monthRecordragment;
    private boolean isDay = false;

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

        dayRecordFragment = new DayRecordFragment();
        monthRecordragment = new MonthRecordFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_record, container, false);

        changeDayMonthButton = view.findViewById(R.id.select_day_and_month);

        changeDayMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDay == true){
                    changeDayMonthButton.setBackgroundResource(R.drawable.cal);
                    FragmentChange(Constants.DAY_RECORD_FRAGMENT);
                    //changeDayMonthButton.setImageResource(R.drawable.adult_lion);
                    isDay = false;
                }else {
                    changeDayMonthButton.setBackgroundResource(R.drawable.hamburger);
                    FragmentChange(Constants.MONTH_RECORD_FRAGMENT);
                    //imageView.setImageResource(R.drawable.baby_lion);
                    isDay = true;
                }
            }
        });

        FragmentChange(Constants.DAY_RECORD_FRAGMENT);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Fragment childFragment = new DayRecordFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, childFragment).commit();
    }

//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.select_month: // 0
//                FragmentChange(Constants.DAY_RECORD_FRAGMENT);
//                break;
//            case R.id.select_day_and_month:
//                FragmentChange(Constants.MONTH_RECORD_FRAGMENT);
//                break;
//        }
//    }

    private void FragmentChange(int fragment){
        fragmentTransaction = getChildFragmentManager().beginTransaction();
        switch (fragment){
            case 0:
                fragmentTransaction.replace(R.id.fragment_container, dayRecordFragment);
                break;
            case 1:
                fragmentTransaction.replace(R.id.fragment_container, monthRecordragment);
                break;
        }
        fragmentTransaction.commit();
    }
}