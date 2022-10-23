package com.example.easyjob_finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class shiftAdapter extends RecyclerView.Adapter<shiftAdapter.ShiftViewHolder>{

    Context context;
    ArrayList<Shift> list;

    public shiftAdapter(Context context, ArrayList<Shift> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public shiftAdapter.ShiftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.shift_item,parent,false);
        return new ShiftViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull shiftAdapter.ShiftViewHolder holder, int position) {
        Shift shift = list.get(position);
        holder.date.setText(shift.getDate());
        holder.enterHour.setText(shift.getStartHour());
        holder.exitHour.setText(shift.getEndHour());
        holder.sumOfHours.setText(shift.getSumOfHours());

    }

    @Override
    public int getItemCount() {
       return list.size();
    }

    public static class ShiftViewHolder extends RecyclerView.ViewHolder{
        TextView date, enterHour, exitHour, sumOfHours;

        public ShiftViewHolder(@NonNull View itemView){
            super(itemView);
            date = itemView.findViewById(R.id.txtDate);
            enterHour = itemView.findViewById(R.id.txtEnterHour);
            exitHour = itemView.findViewById(R.id.txtExitHour);
            sumOfHours = itemView.findViewById(R.id.txtSumOfHours);
        }
    }

}