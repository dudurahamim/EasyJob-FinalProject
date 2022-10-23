package com.example.easyjob_finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManagerWorkSchedule extends AppCompatActivity {

    //Define database variables and objects for use
    DatabaseReference myRefWorkers, myRefManagers;

    RecyclerView recyclerView;
    WorkerScheduleAdapter workerScheduleAdapter;
    ArrayList<Worker> workerList;
    String nameGet, managerUser;
    TextView titleName, titleWorkPlace;
    boolean flag=false;
    Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_work_schedule);
        this.setTitle("Work schedule");

        titleName = findViewById(R.id.txtScheduleManagerName);
        titleWorkPlace = findViewById(R.id.txtScheduleWorkPlace);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRefWorkers = database.getReference("workers");
        myRefManagers = database.getReference("managers");

        nameGet = getIntent().getStringExtra("FullName");
        managerUser = getIntent().getStringExtra("ManagerUserName");
        workerList = new ArrayList<>();
        workerScheduleAdapter = new WorkerScheduleAdapter(this,workerList);
        btnReset = findViewById(R.id.btnResetSchedule);


        // change color of actionBar
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#136EF1"));
        actionBar.setBackgroundDrawable(colorDrawable);

        myRefManagers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String userCheck = dataSnapshot.child("userName").getValue(String.class);
                    if(userCheck.equals(nameGet)){
                        //Screen title definition (name and workplace)
                        titleName.setText(dataSnapshot.child("fullName").getValue(String.class));
                        titleWorkPlace.setText(dataSnapshot.child("workPlace").getValue(String.class)+", "+dataSnapshot.child("streetWorkPlace").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //****************************************
        //A button to initialize a work schedule
        //****************************************
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ConfirmResetSchedule.class);
                intent.putExtra("FullName", nameGet);
                startActivity(intent);

            }
        });


        myRefWorkers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Running on the employee database, and creating an object from each employee that includes his working days.
                //
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(nameGet.equals(dataSnapshot.child("managerUserName").getValue(String.class))) {
                        Worker worker = new Worker(dataSnapshot.child("fullName").getValue(String.class),
                                null, dataSnapshot.child("userName").getValue(String.class),
                                null, null, null,
                                dataSnapshot.child("managerUserName").getValue(String.class), dataSnapshot.child("sunday").getValue(String.class),
                                dataSnapshot.child("monday").getValue(String.class), dataSnapshot.child("tuesday").getValue(String.class),
                                dataSnapshot.child("wednesday").getValue(String.class), dataSnapshot.child("thursday").getValue(String.class),
                                dataSnapshot.child("friday").getValue(String.class), dataSnapshot.child("saturday").getValue(String.class),null);
                        workerList.add(worker);
                    }
                }
                //Sending all the objects to the adapter for displaying them:
                workerScheduleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        recyclerView = findViewById(R.id.recycleSchedule);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(workerScheduleAdapter);


    }

    //Setting the options menu
    @Override
    public boolean onCreateOptionsMenu (Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.back_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            //go to back page:
            case R.id.btnBackPage:
                Intent intent = new Intent(getApplicationContext(), MainScreenManager.class);
                intent.putExtra("FullName", nameGet);
                startActivity(intent);
                finish();

                break;

        }


        return super.onOptionsItemSelected(item);
    }
}










// myRefWorkers.addValueEventListener(new ValueEventListener() {
//@Override
//public void onDataChange(@NonNull DataSnapshot snapshot) {
//        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//
//        if (nameGet.equals(dataSnapshot.child("managerUserName").getValue(String.class))) {
//        // String putName = dataSnapshot.child("userName").getValue(String.class);
//        Intent intent = new Intent(getApplicationContext(), ConfirmResetSchedule.class);
//        intent.putExtra("FullName", nameGet);
//        startActivity(intent);
//
////                                dataSnapshot.child("sunday").getRef().setValue("");
////                                dataSnapshot.child("monday").getRef().setValue("");
////                                dataSnapshot.child("tuesday").getRef().setValue("");
////                                dataSnapshot.child("wednesday").getRef().setValue("");
////                                dataSnapshot.child("thursday").getRef().setValue("");
////                                dataSnapshot.child("friday").getRef().setValue("");
////                                dataSnapshot.child("saturday").getRef().setValue("");
//        }
//
//        }
//        }
//
//@Override
//public void onCancelled(@NonNull DatabaseError error) {
//
//        }
//        });