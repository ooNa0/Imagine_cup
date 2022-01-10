package com.example.imagincup.activity.survey;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
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
        protected RadioButton btn4;
        protected RadioButton btn3;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.question = itemView.findViewById(R.id.survey_question);
        }
    }

    public SurveyAdapter(ArrayList<Survey> list){
        this.mList = list;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_survey, parent, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.question.setText(mList.get(position).getQuestion());
    }

    @Override
    public int getItemCount() {
        return (mList != null ? mList.size() : 0);
    }

}
