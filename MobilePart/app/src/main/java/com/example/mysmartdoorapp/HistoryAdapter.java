package com.example.mysmartdoorapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysmartdoorapp.models.HistoryItem;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    Context context;
    ArrayList<HistoryItem> list;

    public HistoryAdapter(Context context, ArrayList<HistoryItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.history_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HistoryItem historyItem = list.get(position);
        holder.historydate.setText(historyItem.getHistorydate());
        holder.historystatus.setText(historyItem.getHistorystatus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
       TextView historydate, historystatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            historydate = itemView.findViewById(R.id.history_date);
            historystatus = itemView.findViewById(R.id.history_status);
        }
    }
}
