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
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
//---------------------------------------------------------------------------------------------------------------
//The main screen of the manager, this screen will display his details, in addition to the details of the options
//---------------------------------------------------------------------------------------------------------------

public class MainScreenManager extends AppCompatActivity {
    //==============================================================
    //Define database variables and objects for use
    FirebaseDatabase database;
    DatabaseReference myRefManagers, myRefShifts;
    TextView totalHours;
    boolean flag;
    Manager m;
    String putName;
    String nameGet, currentMonth, totalSum;
    String nameToReturn="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_manager);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#136EF1"));
        actionBar.setBackgroundDrawable(colorDrawable);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
        currentMonth = subMonth(currentDate);

        //==============================================================
        //Define database variables and objects for use
         database = FirebaseDatabase.getInstance();
        myRefManagers = database.getReference("managers");
        myRefShifts = database.getReference("shifts");
        this.setTitle("Easy job");

        nameGet = getIntent().getStringExtra("FullName");
        TextView title = findViewById(R.id.txtTitle);
        totalHours = findViewById(R.id.txtTotalHoursManager);

        /////////////////////////////////////////////////////////////////////////////////////
        //Locate the user from the database by his username
        myRefManagers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    String currentUser = dataSnapshot.child("userName").getValue(String.class);
                    if(nameGet.equals(currentUser)){
                        nameToReturn = dataSnapshot.child("fullName").getValue(String.class);
                        title.setText("Hello "+nameToReturn);
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

                //Pulling the sum of the manager's monthly working hours to display on the main screen
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

        /////////////////////////////////////////////////////////////////////////////


        System.out.println("hhh");

        putName = nameGet;




        flag=false;

      
    }



//Setting the manager's options menu
    @Override
    public boolean onCreateOptionsMenu (Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.manager_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

//                          Beyond the "add employee" screen
//        *****************************************************************************************************************
//        * In this transition, an important check takes place in the database,                                           *
//        * whose function is to identify whether the above-mentioned manager already has a group of employees or not.    *
//        * If so, it is moved to the normal screen of adding an employee.                                                *
//        * If not, it is moved to a screen where there is a notification that there is no group of employees yet.        *
//        *****************************************************************************************************************

            case R.id.AddEmployBtn:
                    myRefManagers.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String name = dataSnapshot.child("userName").getValue(String.class);
                                boolean isHaveGroupOfEmployes = dataSnapshot.child("isHaveGroupOfEmployes").getValue(boolean.class);

                                if (name.equals(nameGet)) {
                                    if (!isHaveGroupOfEmployes) {
                                        flag = true;
                                        break;
                                    }
                                }
                            }

                            Intent intent;
                            if (flag){
                                intent = new Intent(getApplicationContext(), NoneGroupActivityManager.class);
                            }
                            else{
                                intent = new Intent(getApplicationContext(), ManagerAddEmployeeActivity.class);
                            }
                            intent.putExtra("FullName", putName);
                            startActivity(intent);
                            finish();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });




                break;

                //Beyond the employee screen
            case R.id.ViewGroupBtn:
                Intent intent = new Intent(getApplicationContext(), WorkerGroupScreen.class);
                intent.putExtra("FullName", putName);
                startActivity(intent);
                finish();

                break;

            //Beyond the settings screen
            case R.id.btnSettings:
                Intent intent2 = new Intent(getApplicationContext(), SettingsActivity.class);
                intent2.putExtra("FullName", putName);
                startActivity(intent2);
                finish();

                break;

            //Beyond the work schedule screen
            case R.id.btnWorkScheduleManager:
                Intent intent3 = new Intent(getApplicationContext(), ManagerWorkSchedule.class);
                intent3.putExtra("FullName", putName);
                startActivity(intent3);
                finish();

                break;

            //Beyond the messages screen
            case R.id.btnMessagesManager:
                Intent intent4 = new Intent(getApplicationContext(), MessageManagerActivity.class);
                intent4.putExtra("FullName", putName);
                startActivity(intent4);
                finish();

                break;

            case R.id.btnCalcHoursManager:
                Intent intent5 = new Intent(getApplicationContext(), CalculationHoursActivity.class);
                intent5.putExtra("FullName", putName);
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

            //01/02/2022
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