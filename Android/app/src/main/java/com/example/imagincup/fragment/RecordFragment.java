package com.example.imagincup.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.imagincup.Constants;
import com.example.imagincup.R;
import com.example.imagincup.back.DTO.DTOPerson;
import com.kal.rackmonthpicker.RackMonthPicker;
import com.kal.rackmonthpicker.listener.DateMonthDialogListener;
import com.kal.rackmonthpicker.listener.OnCancelMonthDialogListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    private TextView yearTextView;
    private FragmentTransaction fragmentTransaction;
    private Button recordButton;
    private DayRecordFragment dayRecordFragment;
    private MonthRecordFragment monthRecordragment;
    private boolean isDay = false;

    private DTOPerson dtoPerson;
    private Bundle bundle;

    private SimpleDateFormat monthDataFormat = new SimpleDateFormat("MMMM", Locale.US);
    private DateFormat format = new SimpleDateFormat("MM");

    public RecordFragment() {
        // Required empty public constructor
    }

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

        bundle = getArguments();
        dtoPerson = (DTOPerson) bundle.getSerializable(Constants.DATABASE_PERSON_TABLENAME);
        Log.d("==========1----------------------", String.valueOf(bundle));
        Log.d("==========-11-3333--------------------", String.valueOf(dtoPerson));

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, container, false);

        changeDayMonthButton = view.findViewById(R.id.select_day_and_month);
        yearTextView = view.findViewById(R.id.record_year);
        recordButton = view.findViewById(R.id.month_button);

        RackMonthPicker rackMonthPicker = new RackMonthPicker(container.getContext());

        rackMonthPicker.setLocale(Locale.ENGLISH)
                .setPositiveButton(new DateMonthDialogListener() {
                    @Override
                    public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {
                        String convertDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + "12";
                        try {
                            Date date = format.parse(convertDate);
                            SetDateValue(String.valueOf(year), monthDataFormat.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton(new OnCancelMonthDialogListener() {
                    @Override
                    public void onCancel(androidx.appcompat.app.AlertDialog dialog) {
                        dialog.dismiss();
                    }
                });

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rackMonthPicker.show();
            }
        });

        SetDateValue(SetYear(), SetMonth());

        dayRecordFragment = new DayRecordFragment();
        monthRecordragment = new MonthRecordFragment();

        changeDayMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDay == true){
                    FragmentChange(dayRecordFragment);
                    changeDayMonthButton.setBackgroundResource(R.drawable.calendar);
                    isDay = false;
                }else {
                    FragmentChange(monthRecordragment);
                    changeDayMonthButton.setBackgroundResource(R.drawable.hamburger);
                    isDay = true;
                }
            }
        });
        FragmentChange(dayRecordFragment);
        changeDayMonthButton.setBackgroundResource(R.drawable.calendar);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Fragment childFragment = new DayRecordFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, childFragment).commit();
    }

    private void FragmentChange(Fragment fragment){

        bundle.putSerializable("Person", dtoPerson);
        //fragment.setArguments(bundle);
        //bundle.putSerializable(Constants.DATABASE_PERSON_TABLENAME, dtoPerson);
        //fragment.setArguments(bundle);


        //Bundle putBundle = new Bundle();
        //putBundle.putSerializable("Person", dtoPerson);
        fragment.setArguments(bundle);

        Log.d("==========1-22---------------------", String.valueOf(bundle));
        Log.d("==========-11--222-------------------", String.valueOf(dtoPerson));
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

    }

    void SetDateValue(String year, String month){
        yearTextView.setText(year);
        recordButton.setText(month);
    }

    String SetMonth() {
        return monthDataFormat.format(new Date());
    }
    String SetYear() {
        SimpleDateFormat yearDataFormat = new SimpleDateFormat("yyyy");
        return yearDataFormat.format(new Date());
    }
}