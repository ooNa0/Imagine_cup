package com.example.imagincup.activity.survey;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imagincup.R;

import java.util.ArrayList;

public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.CustomViewHolder>{

    private ArrayList<Survey> mList;

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        protected TextView question;
        protected RadioButton btn1;
        protected RadioButton btn2;
        protected RadioButton btn3;
        protected RadioButton btn4;
        protected RadioGroup group;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.question = itemView.findViewById(R.id.survey_question);
            this.btn1 = itemView.findViewById(R.id.survey_radiobutton_1);
            this.btn2 = itemView.findViewById(R.id.survey_radiobutton_2);
            this.btn3 = itemView.findViewById(R.id.survey_radiobutton_3);
            this.btn4 = itemView.findViewById(R.id.survey_radiobutton_4);
            this.group = itemView.findViewById(R.id.survey_radiogroup);
        }
    }

    public SurveyAdapter(ArrayList<Survey> list){
        this.mList = list;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_survey, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.question.setText(mList.get(position).getQuestion());
        holder.group.getChildAt(position);

        holder.group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checked_radiobutton = (RadioButton) group.findViewById(checkedId);
                Log.d("?????????????????????????????????????", String.valueOf(position));
                Log.d("????????????????-----??????????????", String.valueOf(mList.get(position)));
                Log.d("???????????????------????????????????", String.valueOf(mList.get(position).getQuestion()));
                Log.d("???????????????--23213?????????????", String.valueOf(checked_radiobutton.getId()));
                Log.d("???????????????--223123213???", String.valueOf(checkedId));

                switch (checked_radiobutton.getId()){
                    case R.id.survey_radiobutton_1:
                        if(position == 3 || position == 7 || position == 11 || position == 15){
                            mList.get(position).setScore(3);
                        }
                        else{
                            mList.get(position).setScore(0);
                        }
                        break;
                    case R.id.survey_radiobutton_2:
                        if(position == 3 || position == 7 || position == 11 || position == 15){
                            mList.get(position).setScore(2);
                        }
                        else{
                            mList.get(position).setScore(1);
                        }
                        break;
                    case R.id.survey_radiobutton_3:
                        if(position == 3 || position == 7 || position == 11 || position == 15){
                            mList.get(position).setScore(1);
                        }
                        else{
                            mList.get(position).setScore(2);
                        }
                        break;
                    case R.id.survey_radiobutton_4:
                        if(position == 3 || position == 7 || position == 11 || position == 15){
                            mList.get(position).setScore(0);
                        }
                        else{
                            mList.get(position).setScore(3);
                        }
                        break;
                }

                Log.d("00000000000000000000000000000000000000000000000", String.valueOf(checked_radiobutton));
                Log.d("00000000000000000000000000000000000000000000000", String.valueOf(checked_radiobutton.isChecked()));
                Log.d("00000000000000000000000000000000000000000000000", String.valueOf(checked_radiobutton.getId()));
                Log.d("00000000000000000000000000000000000000000000000", String.valueOf(checked_radiobutton.getAccessibilityClassName()));
                //store the clicked radiobutton
                //lastCheckedRB = checked_rb;
            }
        });
        mList.get(position).setBtn1(holder.btn1);
        mList.get(position).setBtn2(holder.btn2);
        mList.get(position).setBtn3(holder.btn3);
        mList.get(position).setBtn4(holder.btn4);
    }

    @Override
    public int getItemCount() {
        return (mList != null ? mList.size() : 0);
    }

}
