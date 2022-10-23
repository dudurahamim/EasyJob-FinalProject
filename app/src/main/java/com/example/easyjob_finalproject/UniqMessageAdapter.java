package com.example.easyjob_finalproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UniqMessageAdapter extends RecyclerView.Adapter<UniqMessageAdapter.MessageViewHolder>{

    ArrayList<Message> list;
    Context context;



    public UniqMessageAdapter(Context context, ArrayList<Message> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UniqMessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.uniq_message_item, parent, false);
        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = list.get(position);
        holder.nameOfSender.setText(" "+message.getNameOfSender());
        holder.uniqMessage.setText("  "+message.getTextMessage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MessageViewHolder extends RecyclerView.ViewHolder{
        TextView nameOfSender, uniqMessage;

        public MessageViewHolder(@NonNull View itemView){
            super(itemView);
            nameOfSender = itemView.findViewById(R.id.txtUniqName);
            uniqMessage = itemView.findViewById(R.id.txtUniqMessage);
        }
    }
}

