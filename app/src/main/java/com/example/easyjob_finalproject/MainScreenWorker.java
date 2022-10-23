package com.example.easyjob_finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

import java.text.DateFormat;
import java.util.Calendar;

public class MainScreenWorker extends AppCompatActivity {
    //==============================================================
    //Define database variables and objects for use
    String nameGet;
    String managerUserName="";
    String managerName="";
    String nameOfManager;
    boolean flag;
    String nameToReturn, currentMonth, totalSum;
    DatabaseReference myRefShifts;
    TextView totalHours;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_worker);


        //==============================================================
        //Define database variables and objects for use
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefManagers = database.getReference("managers");
        DatabaseReference myRefWorkers = database.getReference("workers");
        myRefShifts = database.getReference("shifts");
        this.setTitle("Easy job");

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
        currentMonth = subMonth(currentDate);
        flag=true;
        nameGet = getIntent().getStringExtra("FullName");
        TextView yourManager = findViewById(R.id.txtHelloFromManager);
        TextView title = findViewById(R.id.txtHello);
        totalHours = findViewById(R.id.txtTotalHoursWorker);


        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#136EF1"));
        actionBar.setBackgroundDrawable(colorDrawable);

        /////////////////////////////////////////////////////////////////////////////////////
        //Locate the user from the database by his username
        myRefWorkers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("userName").getValue(String.class).equals(nameGet)){
                        managerUserName = dataSnapshot.child("managerUserName").getValue(String.class);
                        nameToReturn=dataSnapshot.child("fullName").getValue(String.class);
                        title.setText("Hello "+nameToReturn);
                        break;
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

                //Pulling the sum of the employee's monthly working hours to display on the main screen
                // (only the current hours of the current month)
                double sumOfMonthHours=0;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String date = dataSnapshot.child("date").getValue(String.class);
                    String month = subMonth(date);
                    String hours = dataSnapshot.child("sumOfHours").getValue(String.class);
                    String currentUser =  dataSnapshot.child("userName").getValue(String.class);

                    if(Integer.parseInt(month)==(Integer.parseInt(currentMonth)) && dataSnapshot.child("userName").getValue(String.class).equals(nameGet)){
                        double currentHours = Double.parseDouble(hours);
                        sumOfMonthHours += currentHours;


                    }
                }
                System.out.println(String.valueOf(sumOfMonthHours));
                totalSum = String.valueOf(sumOfMonthHours);
                //print the sum of hour on the screen: ->
                totalHours.setText(totalSum);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        /////////////////////////////////////////////////////////////////////////////////////
        //Locate the manager of the current user from the database by his username
        myRefManagers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(flag) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (managerUserName.equals(dataSnapshot.child("userName").getValue(String.class))) {
                            managerName = dataSnapshot.child("fullName").getValue(String.class);
                            nameOfManager = dataSnapshot.child("fullName").getValue(String.class);
                            yourManager.setText("Your manager: "+managerName);
                            flag=false;
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        System.out.println(nameOfManager);
        System.out.println("hello test");




    }

    //Setting the worker options menu
    @Override
    public boolean onCreateOptionsMenu (Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.worker_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            //Beyond the settings screen

            case R.id.btnSettingsWorker:
                String putName;
                putName = nameGet;
                Intent intent = new Intent(getApplicationContext(), SettingsActivityWorker.class);
                intent.putExtra("FullName", nameGet);
                startActivity(intent);
                finish();

                break;

            //Beyond the work schedule screen
            case R.id.btnWorkerSchedule:

                putName = nameGet;
                Intent intent2 = new Intent(getApplicationContext(), WorkerPrivateSchedule.class);
                intent2.putExtra("FullName", nameGet);
                startActivity(intent2);
                finish();

                break;

            //Beyond the messages screen
            case R.id.btnWorkerMessages:

                putName = nameGet;
                Intent intent3 = new Intent(getApplicationContext(), MessageActivityWorker.class);
                intent3.putExtra("WorkerUserName", nameGet);
                intent3.putExtra("ManagerUserName", managerUserName);
                startActivity(intent3);
                finish();

                break;


            case R.id.btnCalcHoursWorker:

                putName = nameGet;
                Intent intent4 = new Intent(getApplicationContext(), CalculationHoursActivity.class);
                intent4.putExtra("FullName", nameGet);
                startActivity(intent4);
                finish();

                break;

            case R.id.btnLogOut:


                Intent intent5 = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent5);
                finish();

                break;


        }


        return super.onOptionsItemSelected(item);
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