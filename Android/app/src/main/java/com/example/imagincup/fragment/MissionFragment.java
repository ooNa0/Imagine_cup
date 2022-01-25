package com.example.imagincup.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.imagincup.Constants;
import com.example.imagincup.MissionActivity;
import com.example.imagincup.R;
import com.example.imagincup.back.DTO.DTOPerson;
import com.example.imagincup.back.task.SumDepressionThread;
import com.unity3d.player.UnityPlayerActivity;
import com.unity3d.player.UnityPlayerActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MissionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MissionFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private Button activebtn;
    private Button gamebtn;
    private DTOPerson dtoPerson;

    public MissionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MissionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MissionFragment newInstance(String param1, String param2) {
        MissionFragment fragment = new MissionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    View.OnClickListener onClick;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mission, container, false);

        if (getArguments() != null) {
            dtoPerson = (DTOPerson) getArguments().getSerializable(Constants.DATABASE_PERSON_TABLENAME);
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        Button activeButton = view.findViewById(R.id.active_button);
        Button gameButton = view.findViewById(R.id.game_button);

        activeButton.setOnClickListener(this);
        gameButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        switch (button.getId()){
            case R.id.active_button:
                Intent activeIntent =new Intent(getActivity(), MissionActivity.class);
                activeIntent.putExtra(Constants.DATABASE_PERSON_TABLENAME, dtoPerson);
                startActivity(activeIntent);
                break;

            case R.id.game_button:
                Intent intent = new Intent(getActivity(), UnityPlayerActivity.class);
                startActivity(intent);
                break;
        }
    }
}