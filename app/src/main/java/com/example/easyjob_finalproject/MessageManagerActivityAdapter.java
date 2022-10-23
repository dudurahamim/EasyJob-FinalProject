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
import java.util.List;

public class MessageManagerActivityAdapter extends RecyclerView.Adapter<MessageManagerActivityAdapter.MessageViewHolder> {

    Context context;
    ArrayList<Worker> list;

    public MessageManagerActivityAdapter(Context context, ArrayList<Worker> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MessageManagerActivityAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.message_object_item,parent,false);
        return new MessageManagerActivityAdapter.MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageManagerActivityAdapter.MessageViewHolder holder, int position) {
        Worker worker = list.get(position);
        holder.nameOfSender.setText(worker.getFullName());
        holder.messageConatant.setText(worker.getLastMessage());
        String workerUserName = worker.getUserName();
        String workerManagerUserName = worker.getManagerUserName();


        holder.nameOfSender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String putWorkerUserName = workerUserName;
                String putManagerUserName = workerManagerUserName;
                Intent intent = new Intent(context.getApplicationContext(), UniqueMessagesActivity.class);
                intent.putExtra("WorkerUserName", putWorkerUserName);
                intent.putExtra("ManagerUserName", putManagerUserName);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder{
        TextView nameOfSender, messageConatant;

        public MessageViewHolder(@NonNull View itemView){
            super(itemView);
            nameOfSender = itemView.findViewById(R.id.txtNameSender);
            messageConatant = itemView.findViewById(R.id.txtContantMessage);
        }



    }
}
