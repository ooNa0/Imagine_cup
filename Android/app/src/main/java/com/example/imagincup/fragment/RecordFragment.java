package com.example.imagincup.fragment;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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
    private Button recordButton;
    private DayRecordFragment dayRecordFragment;
    private MonthRecordFragment monthRecordragment;
    private boolean isDay = false;

    private DTOPerson dtoPerson;
    private Bundle bundle;

    private String selectYear;
    private String selectMonth;

    private SimpleDateFormat monthDataFormat = new SimpleDateFormat("MMMM", Locale.US);
    private SimpleDateFormat dayDataFormat = new SimpleDateFormat("dd", Locale.US);
    private SimpleDateFormat yearDataFormat = new SimpleDateFormat("yyyy");
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

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

        selectYear = SetYear();
        selectMonth = SetMonth();

        bundle = getArguments();
        dtoPerson = (DTOPerson) bundle.getSerializable(Constants.DATABASE_PERSON_TABLENAME);

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

        RackMonthPicker rackMonthPicker = new RackMonthPicker(getContext());
        rackMonthPicker.setColorTheme(ContextCompat.getColor(getContext(), R.color.brown));

        rackMonthPicker.setLocale(Locale.ENGLISH)
                .setPositiveButton(new DateMonthDialogListener() {
                    @Override
                    public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {
                        String convertDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + "12";
                        try {
                            selectYear = String.valueOf(year);
                            selectMonth = monthDataFormat.format(format.parse(convertDate));
                            SetDateValue(selectYear, selectMonth);
                            if(isDay){
                                FragmentChange(new MonthRecordFragment());
                            }
                            else{
                                FragmentChange(new DayRecordFragment());
                            }
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

        SetDateValue(selectYear, selectMonth);

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
    }

    private void FragmentChange(Fragment fragment){
        bundle.putSerializable("Person", dtoPerson);
        bundle.putString("Year", selectYear);
        bundle.putString("Month", selectMonth);
        bundle.putString("Day", SetDay());
        fragment.setArguments(bundle);
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

    }

    void SetDateValue(String year, String month){
        yearTextView.setText(year);
        recordButton.setText(month);
    }

    String SetDay() {
        return dayDataFormat.format(new Date());
    }
    String SetMonth() {
        return monthDataFormat.format(new Date());
    }
    String SetYear() { return yearDataFormat.format(new Date()); }
}