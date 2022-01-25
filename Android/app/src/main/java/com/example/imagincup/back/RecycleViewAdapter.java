package com.example.imagincup.back;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imagincup.AnswerActivity;
import com.example.imagincup.Constants;
import com.example.imagincup.R;
import com.example.imagincup.back.DTO.DTOPerson;
import com.example.imagincup.back.DTO.DTORecord;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.CustomViewHolder> {

    private CardView cardView;
    private Context context;
    private List<DTORecord> items;
    private int i;
    private CustomViewHolder viewHolder;

    private DTOPerson dtoPerson;
    private String personID;

    public RecycleViewAdapter(Context context, List<DTORecord> items, int i, DTOPerson dtoPerson) {
        this.personID = dtoPerson.getPersonId().toString();
        this.context = context;
        this.items = items;
        this.i = i;
        this.dtoPerson = dtoPerson;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_day_record, parent, false);
        cardView = view.findViewById(R.id.card_view);
        viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final DTORecord item = items.get(position);
        holder.date.setText("Day " + item.getRecordDate());
        holder.question.setText(item.getQuestion()); // 질문
        holder.content.setText(getEmotionStateIcon(item.getEmotion())); // 얼굴
        if(item.getEmotion().equals("positive")){
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.happy_day_record_card));
        }
        else if(item.getEmotion().equals("negative")){
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.sad_day_record_card));
        }
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passRecordData(item);
            }
        });
    }

    void passRecordData(DTORecord item){
        Intent intent = new Intent(context, AnswerActivity.class);
        intent.putExtra(Constants.DATABASE_PERSON_TABLENAME, dtoPerson);
        intent.putExtra(Constants.DATABASE_RECORD_TABLENAME, item);
        intent.putExtra("isVisible", true);
        context.startActivity(intent);
    }

    String getEmotionStateIcon(String emotionStateString){
        if(emotionStateString.equals("positive")){
            return "😀";
        }
        else if(emotionStateString.equals("negative")){
            return "😢";
        }
        return "😐";
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView question;
        TextView content;

        public CustomViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.card_item_day);
            question = itemView.findViewById(R.id.card_item_detail);
            content = itemView.findViewById(R.id.card_item_emotion);
        }
    }
}
