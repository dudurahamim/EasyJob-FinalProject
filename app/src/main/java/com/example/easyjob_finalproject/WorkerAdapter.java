package com.example.easyjob_finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class WorkerAdapter extends RecyclerView.Adapter<WorkerAdapter.WorkerViewHolder> {

    Context context;
    ArrayList<Worker> list;

    public WorkerAdapter(Context context, ArrayList<Worker> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public WorkerAdapter.WorkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.worker_item,parent,false);
        return new WorkerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerAdapter.WorkerViewHolder holder, int position) {
    Worker worker = list.get(position);
    holder.fullName.setText(worker.getFullName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class WorkerViewHolder extends RecyclerView.ViewHolder{
        TextView fullName;

        public WorkerViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.workerName);
        }
    }
}
