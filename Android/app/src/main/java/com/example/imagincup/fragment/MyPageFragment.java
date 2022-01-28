package com.example.imagincup.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imagincup.AboutUs;
import com.example.imagincup.Constants;
import com.example.imagincup.IntroActivity;
import com.example.imagincup.R;
import com.example.imagincup.activity.survey.SurveyActivity;
import com.example.imagincup.back.DTO.DTOPerson;
import com.example.imagincup.back.task.person.DeletePersonThread;
import com.example.imagincup.back.task.person.DeleteRecordThread;
import com.example.imagincup.back.task.person.DeleteScoreThread;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private ImageView imageView;
    private TextView nameTextView;
    private TextView scoreTextView;
    private Integer depressionScore;
    private Button refillSurveyButton;
    private Button leaveButton;
    private Button deleteUserButton;
    private Button aboutUs;
    private DTOPerson dtoPerson;
    private DeletePersonThread deletePersonThread;
    private DeleteScoreThread deleteScoreThread;
    private DeleteRecordThread deleteRecordThread;
    private Intent intent;

    public MyPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyPageFragment newInstance(String param1, String param2) {
        MyPageFragment fragment = new MyPageFragment();
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
            dtoPerson = (DTOPerson) getArguments().getSerializable(Constants.DATABASE_PERSON_TABLENAME);
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_page, container, false);

        imageView = view.findViewById(R.id.mypage_image_character);
        nameTextView = view.findViewById(R.id.mypage_text_name);
        scoreTextView = view.findViewById(R.id.mypage_text_score);

        leaveButton = view.findViewById(R.id.mypage_button_leave);
        refillSurveyButton = view.findViewById(R.id.mypage_button_survey);
        deleteUserButton = view.findViewById(R.id.mypage_button_resign);
        aboutUs = view.findViewById(R.id.mypage_button_about_us);

        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Confirm Deletion");
                builder.setMessage("Are you sure you want to delete your account?");
                builder.setPositiveButton("Delete account",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                        deleteScoreThread = new DeleteScoreThread(dtoPerson.getPersonId());
                        deleteRecordThread = new DeleteRecordThread(dtoPerson.getPersonId());
                        deletePersonThread = new DeletePersonThread(dtoPerson.getPersonId());
                        try {
                            deleteScoreThread.start();
                            deleteScoreThread.join();

                            deleteRecordThread.start();
                            deleteRecordThread.join();

                            deletePersonThread.start();
                            deletePersonThread.join();
                            Toast.makeText(getActivity().getApplicationContext(),"delete your account complete", Toast.LENGTH_SHORT).show();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        intent = new Intent(getActivity(), IntroActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP); // 스택에 쌓여있는 액티비티 비우기
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancle", null);
                builder.create().show(); //보이기
            }
        });

        refillSurveyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteScoreThread = new DeleteScoreThread(dtoPerson.getPersonId());
                deleteScoreThread.start();
                try {
                    deleteScoreThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                intent = new Intent(getActivity(), SurveyActivity.class);
                intent.putExtra("PersonID", String.valueOf(dtoPerson.getPersonId()));
                startActivity(intent);
            }
        });

        leaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.runFinalization();
                System.exit(0);
            }
        });

        nameTextView.setText(dtoPerson.getPersonName());
        scoreTextView.setText(dtoPerson.getPersonDepressionPercent() + "/100");

        depressionScore = dtoPerson.getPersonDepressionScore();
        if(depressionScore <= 20){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.happy));
        }
        else if(depressionScore <= 40){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.areyousuremid));
        }
        else{
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.areyousuresad));
        }

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutUs.class);
                startActivity(intent);
            }
        });

        return view;
    }
}