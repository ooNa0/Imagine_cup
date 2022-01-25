package com.example.imagincup.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imagincup.AnswerActivity;
import com.example.imagincup.Constants;
import com.example.imagincup.R;
import com.example.imagincup.back.DTO.DTOPerson;
import com.example.imagincup.back.DTO.DTORecord;
import com.example.imagincup.back.ProgressDialog;
import com.example.imagincup.back.task.SumDepressionThread;
import com.example.imagincup.back.task.answer.SelectAnswerExistThread;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageButton callButton;
    private Button questionButton;
    private TextView scoreTextView;
    private ImageView character;

    private DTOPerson dtoPerson;
    private DTORecord dtoRecord = null;
    private SelectAnswerExistThread selectAnswerExistThread;
    private Bundle bundle;
    private Integer resultSum;
    private Integer depressionScore;

    private ProgressDialog progressDialog;

    private SumDepressionThread sumDepressionThread;

    public HomeFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        //args.putSerializable("Person", dtoPerson);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = ProgressDialog.getInstance();
        //dtoPerson = (DTOPerson) bundle.getSerializable(Constants.DATABASE_PERSON_TABLENAME);
        if (getArguments() != null) {
            dtoPerson = (DTOPerson) getArguments().getSerializable(Constants.DATABASE_PERSON_TABLENAME);
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            Log.d("이게 홈프레그먼트에서 언크리에이트에서 가져온 널 포인트 오류가 뜨는 디티오 펄슨", String.valueOf(dtoPerson));
            Log.d("이게 홈프레그먼트에서 언크리에이트에서 가져온 널 포인트 오류가 뜨는 디티오 펄슨2", dtoPerson.getPersonDevice());
            Log.d("이게 홈프레그먼트에서 언크리에이트에서 가져온 널 포인트 오류가 뜨는 디티오 펄슨3", String.valueOf(dtoPerson.getPersonId()));
            sumDepressionThread = new SumDepressionThread(dtoPerson.getPersonId());
            sumDepressionThread.start();
            try {
                sumDepressionThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resultSum = sumDepressionThread.getSumScore();
            dtoPerson.setPersonDepressionScore(resultSum);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        character = view.findViewById(R.id.home_character);
        scoreTextView = view.findViewById(R.id.emotion_score);
        scoreTextView.setText(String.valueOf(dtoPerson.getPersonDepressionPercent()));

        depressionScore = dtoPerson.getPersonDepressionScore();
        if(depressionScore <= 20){
            character.setImageDrawable(getResources().getDrawable(R.drawable.happy));
        }
        else if(depressionScore <= 40){
            character.setImageDrawable(getResources().getDrawable(R.drawable.middle));
        }
        else{
            character.setImageDrawable(getResources().getDrawable(R.drawable.sad));
        }
        questionButton = view.findViewById(R.id.home_today_question_button);
        questionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AnswerActivity.class);
                progressDialog.show(getActivity());
                // 데베 값이 존재하는지?
                selectAnswerExistThread = new SelectAnswerExistThread(dtoPerson.getPersonId().toString(), dtoRecord);
                selectAnswerExistThread.start();
                try {
                    selectAnswerExistThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dtoRecord = selectAnswerExistThread.getdtoRecord();
                if(dtoRecord == null){
                    intent.putExtra("isVisible", false); // 데베에 값이 존재하지 않을 경우
                    // 해당 QuestioniD를 통해서 question값을 가져온다.
                }
                else{
                    intent.putExtra(Constants.DATABASE_RECORD_TABLENAME, dtoRecord);
                    intent.putExtra("isVisible", true);
                }
                intent.putExtra(Constants.DATABASE_PERSON_TABLENAME, dtoPerson);
                progressDialog.dismiss();
                startActivity(intent);
            }
        });
        callButton = view.findViewById(R.id.home_emergency_call);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:988"));
                startActivity(intent);
            }
        });
        return view;
    }

    public void SumDepression(){

    }
}
