package com.example.easyjob_finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WorkerPrivateSchedule extends AppCompatActivity {

    DatabaseReference myRefWorkers;

//Define all the variables needed to display the personal work arrangement
//////////////////////////////////////////////////////////////////////////
    WorkerScheduleAdapter workerScheduleAdapter;

    String nameGet, managerUserName;
    TextView title, sunday, monday, tuesday, wednesday, thursday, friday, saturday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_private_schedule);

        this.setTitle("Schedule");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRefWorkers = database.getReference("workers");
        nameGet = getIntent().getStringExtra("FullName");

        title = findViewById(R.id.txtTitleWorkerPrivateSchedule);
        sunday = findViewById(R.id.txtPrivateSunday);
        monday = findViewById(R.id.txtPrivateMonday);
        tuesday = findViewById(R.id.txtPrivateTuesday);
        wednesday = findViewById(R.id.txtPrivateWednesday);
        thursday = findViewById(R.id.txtPrivateThursday);
        friday = findViewById(R.id.txtPrivateFriday);
        saturday = findViewById(R.id.txtPrivateSaturday);


        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#136EF1"));
        actionBar.setBackgroundDrawable(colorDrawable);


        //Withdrawal of employee manager data from the database
        myRefWorkers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("userName").getValue(String.class).equals(nameGet)){
                        managerUserName = dataSnapshot.child("managerUserName").getValue(String.class);

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




//////////////////////////////////////////////////
//Withdrawing all the data regarding each employee's working day from the database, and placing the information on display
/////////////////////////////////////////////////////
        myRefWorkers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String sunday1 = dataSnapshot.child("sunday").getValue(String.class);
                    String monday1 = dataSnapshot.child("monday").getValue(String.class);
                    String tuesday1 = dataSnapshot.child("tuesday").getValue(String.class);
                    String wednesday1 = dataSnapshot.child("wednesday").getValue(String.class);
                    String thursday1 = dataSnapshot.child("thursday").getValue(String.class);
                    String friday1 = dataSnapshot.child("friday").getValue(String.class);
                    String saturday1 = dataSnapshot.child("saturday").getValue(String.class);
                    String userCheck = dataSnapshot.child("userName").getValue(String.class);
                    if(userCheck.equals(nameGet)){
                        title.setText(dataSnapshot.child("fullName").getValue(String.class));
                        sunday.setText(sunday1);
                        monday.setText(monday1);
                        tuesday.setText(tuesday1);
                        wednesday.setText(wednesday1);
                        thursday.setText(thursday1);
                        friday.setText(friday1);
                        saturday.setText(saturday1);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        /////Functions beyond the notifications screen by clicking on each day in the work schedule/////
        ///////////////////////////////////         ||       ///////////////////////////////////////////
        ///////////////////////////////////       \ || /     ///////////////////////////////////////////
        ///////////////////////////////////        \||/      ///////////////////////////////////////////
        ///////////////////////////////////         \/       ///////////////////////////////////////////

        sunday .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent3 = new Intent(getApplicationContext(), MessageActivityWorker.class);
                intent3.putExtra("WorkerUserName", nameGet);
                intent3.putExtra("ManagerUserName", managerUserName);
                startActivity(intent3);
                finish();
            }
        });

        monday .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent3 = new Intent(getApplicationContext(), MessageActivityWorker.class);
                intent3.putExtra("WorkerUserName", nameGet);
                intent3.putExtra("ManagerUserName", managerUserName);
                startActivity(intent3);
                finish();
            }
        });

        tuesday .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent3 = new Intent(getApplicationContext(), MessageActivityWorker.class);
                intent3.putExtra("WorkerUserName", nameGet);
                intent3.putExtra("ManagerUserName", managerUserName);
                startActivity(intent3);
                finish();
            }
        });

        thursday .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent3 = new Intent(getApplicationContext(), MessageActivityWorker.class);
                intent3.putExtra("WorkerUserName", nameGet);
                intent3.putExtra("ManagerUserName", managerUserName);
                startActivity(intent3);
                finish();
            }
        });

        friday .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent3 = new Intent(getApplicationContext(), MessageActivityWorker.class);
                intent3.putExtra("WorkerUserName", nameGet);
                intent3.putExtra("ManagerUserName", managerUserName);
                startActivity(intent3);
                finish();
            }
        });

        saturday .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent3 = new Intent(getApplicationContext(), MessageActivityWorker.class);
                intent3.putExtra("WorkerUserName", nameGet);
                intent3.putExtra("ManagerUserName", managerUserName);
                startActivity(intent3);
                finish();
            }
        });

        wednesday .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent3 = new Intent(getApplicationContext(), MessageActivityWorker.class);
                intent3.putExtra("WorkerUserName", nameGet);
                intent3.putExtra("ManagerUserName", managerUserName);
                startActivity(intent3);
                finish();
            }
        });

        //////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////

    }
}