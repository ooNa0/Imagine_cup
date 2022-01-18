package com.example.imagincup.back;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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

    private String personID;

    public RecycleViewAdapter(Context context, List<DTORecord> items, int i, String personID) {
        this.personID = personID;
        this.context = context;
        this.items = items;
        this.i = i;
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
        Log.d("aaaaaa1----------------------", String.valueOf(items));
        final DTORecord item = items.get(position);
        Log.d("aaaaaa1----------------------", String.valueOf(item));
        holder.date.setText("Day " + item.getRecordDay());
        holder.question.setText(item.getQuestion()); // 질문
        holder.content.setText(item.getEmotion()); // 얼굴
        if(item == null){
            cardView.setCardBackgroundColor(Color.parseColor("#CC82DBD7"));
        }
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
