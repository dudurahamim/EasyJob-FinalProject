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

public class WorkerScheduleAdapter extends RecyclerView.Adapter<WorkerScheduleAdapter.WorkerViewHolder> {

    Context context;
    ArrayList<Worker> list;

    public WorkerScheduleAdapter(Context context, ArrayList<Worker> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public WorkerScheduleAdapter.WorkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.worker_schedule_item,parent,false);
        return new WorkerViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull WorkerViewHolder holder, int position) {

       Worker worker = list.get(position);
        String workerUserName = worker.getUserName();
        holder.fullName.setText(worker.getFullName());
       holder.sunday.setText(worker.getSunday());
       holder.monday.setText(worker.getMonday());
       holder.tuesday.setText(worker.getTuesday());
       holder.wednesday.setText(worker.getWednesday());
       holder.thursday.setText(worker.getThursday());
       holder.friday.setText(worker.getFriday());
       holder.saturday.setText(worker.getSaturday());

       holder.fullName.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String putName = workerUserName;
               String managerUser = worker.getManagerUserName();
               Intent intent = new Intent(context.getApplicationContext(), ManagerSetScheduleForWorker.class);
               intent.putExtra("FullName", putName);
               intent.putExtra("ManagerUserName", managerUser);
               context.startActivity(intent);

           }
       });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class WorkerViewHolder extends RecyclerView.ViewHolder{
        TextView fullName, sunday, monday, tuesday, wednesday, thursday, friday, saturday;


        public WorkerViewHolder(@NonNull View itemView) {
            super(itemView);

            fullName = itemView.findViewById(R.id.txtWorkerNameSchedule);
            sunday = itemView.findViewById(R.id.txtSundayWorker);
            monday = itemView.findViewById(R.id.txtMondayWorker);
            tuesday = itemView.findViewById(R.id.txtTuesdayWorker);
            wednesday = itemView.findViewById(R.id.txtWednesdayWorker);
            thursday = itemView.findViewById(R.id.txtThursdayWorker);
            friday = itemView.findViewById(R.id.txtFridayWorker);
            saturday = itemView.findViewById(R.id.txtSaturdayWorker);
        }
    }
}
