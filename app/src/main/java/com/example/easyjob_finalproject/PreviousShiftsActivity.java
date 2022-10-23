package com.example.easyjob_finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PreviousShiftsActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRefManagers, myRefWorkers, myRefShifts;
    TextView title;
    ArrayList<Shift> list;
    RecyclerView recyclerView;
    shiftAdapter shiftAdapter;
    String nameGet, currentMonth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_shifts);
        database = FirebaseDatabase.getInstance();
        list = new ArrayList<>();
        myRefManagers = database.getReference("managers");
        myRefWorkers = database.getReference("workers");
        myRefShifts = database.getReference("shifts");
        title=findViewById(R.id.txtPreviousShiftsTitle);
        shiftAdapter = new shiftAdapter(this,list);
        nameGet = getIntent().getStringExtra("FullName");
        this.setTitle("Previous Shifts");

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());

        currentMonth = subMonth(currentDate);
        System.out.println(currentMonth);
        System.out.println(currentMonth);
        double sumOfHoursMonth=0;


        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#136EF1"));
        actionBar.setBackgroundDrawable(colorDrawable);


        myRefManagers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String currentUser = dataSnapshot.child("userName").getValue(String.class);
                    if(currentUser.equals(nameGet)){
                        title.setText(dataSnapshot.child("fullName").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRefShifts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String currentUser = dataSnapshot.child("userName").getValue(String.class);
                    if(currentUser.equals(nameGet)){
                        Shift shift = new Shift(dataSnapshot.child("startHour").getValue(String.class),dataSnapshot.child("endHour").getValue(String.class),
                                dataSnapshot.child("date").getValue(String.class),dataSnapshot.child("userName").getValue(String.class),dataSnapshot.child("sumOfHours").getValue(String.class));

                        list.add(shift);

                    }
                }

                boolean flag = false;
                for (int i=0;i< list.size();i++) {
                    for (int j = 0; j < list.size() - 1; j++) {

                        Shift current = list.get(j);
                        Shift next = list.get(j + 1);
                        int currentMonth = 0;
                        int nextMonth = 0;


                        String month = "";
                        for (int k = 0; k < current.date.length() - 1; k++) {


                            if(!flag)
                            if (current.date.charAt(k) < '0' || current.date.charAt(k) > '9') {
                                flag = true;
                                k++;
                            }
                            if (flag) {
                                month += current.date.charAt(k);
                                if (current.date.charAt(k + 1) < '0' || current.date.charAt(k + 1) > '9') {
                                    break;
                                    //flag = false;
                                }
                            }
                        }
                        currentMonth = Integer.parseInt(month);
                        ////////////////
                        month = "";
                        flag=false;
                        for (int l = 0; l < next.date.length() - 1; l++) {


                            if (next.date.charAt(l) < '0' || next.date.charAt(l) > '9') {
                                flag = true;
                                l++;
                            }
                            if (flag) {
                                month += next.date.charAt(l);
                                if (next.date.charAt(l + 1) < '0' || next.date.charAt(l + 1) > '9') {
                                    flag = false;
                                }
                            }
                        }


                        if (currentMonth > nextMonth) {
                            list.set(j, next);

                            list.set(j + 1, current);

                        }
                    }
                }

                shiftAdapter.notifyDataSetChanged();
            }




            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        myRefShifts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                double sumOfMonthHours=0;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String date = dataSnapshot.child("date").getValue(String.class);
                    String month = subMonth(date);
                    String hours = dataSnapshot.child("sumOfHours").getValue(String.class);

                    if(month.equals(currentMonth)){
                        double currentHours = Double.parseDouble(hours);
                        sumOfMonthHours += currentHours;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        recyclerView = findViewById(R.id.recyclerViewShifts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(shiftAdapter);

    }

    private String subMonth(String currentDate) {

        String month = "";
        boolean flag=false;
        for (int i = 0; i < currentDate.length()-1; i++) {

            if(!flag)
                if (currentDate.charAt(i) < '0' || currentDate.charAt(i) > '9') {
                    flag = true;
                    i++;
                }
            if (flag) {
                month += currentDate.charAt(i);
                if (currentDate.charAt(i + 1) < '0' || currentDate.charAt(i + 1) > '9') {
                    break;
                    //flag = false;
                }
            }
        }
        return month;

    }
}