package com.example.easyjob_finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManagerSetScheduleForWorker extends AppCompatActivity {

    //Define database variables and objects for use
    String workerName = "";
    EditText sunday, monday, tuesday, wednesday, thursday, friday, saturday;
    Button btnUpdate;
    boolean flag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_set_schedule_for_worker);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefWorkers = database.getReference("workers");
        TextView title = findViewById(R.id.txtCreatScheduleDay);

        this.setTitle("Work schedule");
        String nameGet = getIntent().getStringExtra("FullName");
        String managerUserName = getIntent().getStringExtra("ManagerUserName");

        //Defining fields for all days of the week to be filled
        sunday = findViewById(R.id.editSunday);
        monday = findViewById(R.id.editMonday);
        tuesday = findViewById(R.id.editTuesday);
        wednesday = findViewById(R.id.editWednesday);
        thursday = findViewById(R.id.editThuersday);
        friday = findViewById(R.id.editFriday);
        saturday = findViewById(R.id.editSaturday);
        btnUpdate = findViewById(R.id.btnUpdateNewScheduleWeek);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        //Change color of the actionBar--->
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#136EF1"));
        actionBar.setBackgroundDrawable(colorDrawable);


        myRefWorkers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //find the name of the current user (set title).
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String userCheck = dataSnapshot.child("userName").getValue(String.class);
                    if (userCheck.equals(nameGet)) {
                        workerName = dataSnapshot.child("fullName").getValue(String.class);
                        title.setText("Please enter hours for " + workerName);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myRefWorkers.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String userCheck = dataSnapshot.child("userName").getValue(String.class);

                            //set all new schedule values at current worker.
                                if (userCheck.equals(nameGet)) {
                                    dataSnapshot.child("sunday").getRef().setValue(sunday.getText().toString());
                                    dataSnapshot.child("monday").getRef().setValue(monday.getText().toString());
                                    dataSnapshot.child("tuesday").getRef().setValue(tuesday.getText().toString());
                                    dataSnapshot.child("wednesday").getRef().setValue(wednesday.getText().toString());
                                    dataSnapshot.child("thursday").getRef().setValue(thursday.getText().toString());
                                    dataSnapshot.child("friday").getRef().setValue(friday.getText().toString());
                                    dataSnapshot.child("saturday").getRef().setValue(saturday.getText().toString());

                                  //  flag = false;
                                   // break;

                                }

                        }

                        //Go to the main screen after filling in the fields--->>>
                            Intent intent = new Intent(getApplicationContext(), MainScreenManager.class);
                            intent.putExtra("FullName",managerUserName);
                            intent.putExtra("ManagerUserName",managerUserName);
                            startActivity(intent);
                            finish();



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }


}