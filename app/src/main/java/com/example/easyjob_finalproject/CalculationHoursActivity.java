package com.example.easyjob_finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalculationHoursActivity  extends AppCompatActivity{
    boolean flag=true;
    //A screen whose job is to calculate the amount of working hours of a user based on input he entered.
    //Define database variables and objects for use
    FirebaseDatabase database;
    DatabaseReference myRefManagers, myRefWorkers, myRefShifts;
    EditText startHour, exitHour, editDate;
    TextView fullNameTitle, currentDateTitle, wrongDate;
    Button sendBtn, previousShiftsBtn;
    long diffHours;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation_hours);

        this.setTitle("Calculate hours");
        database = FirebaseDatabase.getInstance();
        myRefManagers = database.getReference("managers");
        myRefWorkers = database.getReference("workers");
        myRefShifts = database.getReference("shifts");
        startHour = findViewById(R.id.editStartHour);
        exitHour = findViewById(R.id.editExitHour);
        fullNameTitle = findViewById(R.id.txtTitleUserFullName);
        currentDateTitle = findViewById(R.id.txtCurrentTime);
        editDate = findViewById(R.id.editDate);
        previousShiftsBtn = findViewById(R.id.btnPreviousShifts);
        sendBtn = findViewById(R.id.btnExit);
        sendBtn.setClickable(false);
        wrongDate = findViewById(R.id.txtWrongDate);
       // startBtn.setClickable(false);
        sendBtn.setBackgroundColor(Color.GRAY);
       // startBtn.setBackgroundColor(Color.GRAY);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());

        System.out.println(currentDate);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#136EF1"));
        actionBar.setBackgroundDrawable(colorDrawable);

        System.out.println(editDate.getText().toString());

        //=========================================================================
        //Controls the availability of the buttons according to the input entered.

        editDate.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if(editDate.getText().toString().isEmpty()){
                    sendBtn.setClickable(false);
                }
                else
                {
                    sendBtn.setClickable(true);
                }
            }


            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                if(editDate.getText().toString().equals("")){
                    sendBtn.setClickable(false);
                    sendBtn.setBackgroundColor(Color.GRAY);
//                    startBtn.setClickable(false);
//                    startBtn.setBackgroundColor(Color.GRAY);
                }
                else
                {
                    sendBtn.setClickable(true);
                    sendBtn.setBackgroundColor(Color.BLUE);
                }

            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                if(editDate.getText().toString().equals("")){
                    sendBtn.setBackgroundColor(Color.GRAY);
                    sendBtn.setClickable(false);
                  //  startBtn.setClickable(false);
                  //  startBtn.setBackgroundColor(Color.GRAY);
                }
                else
                {
                    sendBtn.setBackgroundColor(Color.BLUE);
                    sendBtn.setClickable(true);
                }
            }
        });



        currentDateTitle.setText(currentDate);
        //=========================================================================

        String nameGet = getIntent().getStringExtra("FullName");

        //Setting the screen title//
        myRefManagers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String currentUser = dataSnapshot.child("userName").getValue(String.class);
                    if(currentUser.equals(nameGet)){
                        fullNameTitle.setText(dataSnapshot.child("fullName").getValue(String.class));

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Setting the screen title//
        myRefWorkers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String currentUser = dataSnapshot.child("userName").getValue(String.class);
                    if(currentUser.equals(nameGet)){
                        fullNameTitle.setText(dataSnapshot.child("fullName").getValue(String.class));

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        startBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                myRefManagers.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                            String currentUser = dataSnapshot.child("userName").getValue(String.class);
//                            if(currentUser.equals(nameGet)){
//                                dataSnapshot.child("startHour").getRef().setValue(startHour.getText().toString());
//
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//                myRefWorkers.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                            String currentUser = dataSnapshot.child("userName").getValue(String.class);
//                            if(currentUser.equals(nameGet)){
//                                dataSnapshot.child("startHour").getRef().setValue(startHour.getText().toString());
//
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//        });

        //Clicking a button to define a new shift
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //If the date entered by the user is correct
                if(editDate.getText().toString().length() ==10 ) {
                    double num = differneceHours(startHour.getText().toString(), exitHour.getText().toString());
                    String number = "" + num;
                    myRefManagers.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String currentUser = dataSnapshot.child("userName").getValue(String.class);
                                if (currentUser.equals(nameGet)) {
                                    //Creating an object of type "shift" and inserting it into the database
                                    Shift shift = new Shift(startHour.getText().toString(),
                                            exitHour.getText().toString(), editDate.getText().toString(),
                                            currentUser, number);
                                    myRefShifts.push().setValue(shift);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    ////////////////////////////

                    myRefWorkers.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String currentUser = dataSnapshot.child("userName").getValue(String.class);
                                if (currentUser.equals(nameGet) && flag) {
                                    //Creating an object of type "shift" and inserting it into the database
                                    Shift shift = new Shift(startHour.getText().toString(), exitHour.getText().toString(), editDate.getText().toString(),
                                            currentUser, number);
                                    myRefShifts.push().setValue(shift);
                                    flag=false;
                                    break;

//                                dataSnapshot.child("exitHour").getRef().setValue(exitHour.getText().toString());
//                                dataSnapshot.child("lastDateSignHour").getRef().setValue(editDate.getText().toString());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                //If the date entered by the user is NOT correct !!! ->
                else{
                    wrongDate.setText("Not valid date !");
                }


                //Update last shift times for the specific user
                myRefWorkers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            String currentUser = dataSnapshot.child("userName").getValue(String.class);
                            if(currentUser.equals(nameGet)){
                                dataSnapshot.child("exitHour").getRef().setValue(exitHour.getText().toString());
                                dataSnapshot.child("lastDateSignHour").getRef().setValue(editDate.getText().toString());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        //Button to go to the "previous shifts" screen: --->
        previousShiftsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //******************************************************************************//
                //Sending the unique details to the aforementioned user so that on the next screen
                // the details of his shifts will appear
                //******************************************************************************//
                String putName;
                putName = nameGet;
                Intent intent = new Intent(getApplicationContext(), PreviousShiftsActivity.class);
                intent.putExtra("FullName", putName);
                startActivity(intent);
                finish();
            }
        });
    }

    public void calculateHours(){
        String hour = startHour.getText().toString();
        String endHour = exitHour.getText().toString();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
        Date start, end;

        {
            try {
                start = dateFormat.parse(hour);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                end = dateFormat.parse(endHour);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Date d1 = null;
        Date d2 = null;

        {

            try {
                d1 = dateFormat.parse(hour);
                d2 = dateFormat.parse(endHour);
                long diff = d2.getTime() - d1.getTime();
                diffHours = diff/(60*60*1000);

            } catch(ParseException e)
            {
                e.printStackTrace();
            }

        }
    }

    //A function to calculate the difference in hours between the entry time and the shift exit time
    public double differneceHours(String start, String exit){

                String startHour="", exitHour="", startMinut="", exitMinut="";
                boolean flagSearchMinuts=false;

                //16:00
                int tempIndex=0;
        for (int i = 0; i < start.length(); i++) {
            if(start.charAt(i)>='0' && start.charAt(i)<='9'){
                startHour+=start.charAt(i);
            }
            else{
                tempIndex=i;
                break;
            }
        }
        for (int i = tempIndex+1; i < start.length(); i++) {
            startMinut+=start.charAt(i);
        }
        //\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        for (int i = 0; i < exit.length(); i++) {
            if(exit.charAt(i)>='0' && exit.charAt(i)<='9'){
                exitHour+=exit.charAt(i);
            }
            else{
                tempIndex=i;
                break;
            }
        }
        for (int i = tempIndex+1; i < exit.length(); i++) {
            exitMinut+=exit.charAt(i);
        }


        //Implements "integer" type variables into "double" type variables,
        // in order to save a decimal place for the minute chain for the total difference
        double startHourInt = Integer.parseInt(startHour);
        double exitHourInt = Integer.parseInt(exitHour);
        double startMinutInt = Integer.parseInt(startMinut);
        double exitMinutInt = Integer.parseInt(exitMinut);

        double diffHours=0;
        double diffMinuts=0;


        if(exitHourInt<startHourInt){
            diffHours+=(24-startHourInt);
            diffHours+=exitHourInt;
        }
        else{
            diffHours+=exitHourInt-startHourInt;
        }

        if(exitMinutInt<startMinutInt){
            diffMinuts+=(60-startMinutInt);
            diffMinuts+=exitMinutInt;
            diffHours--;
        }
        else{
            diffMinuts+=exitMinutInt-startMinutInt;
        }



        diffHours+=(diffMinuts/100);

        return diffHours;



        /////////////////////////////////////////////////////


//                for (int i = 0; i < start.length(); i++) {
//                    if (start.charAt(i) == ':') {
//                        flagSearchMinuts = true;
//                        i++;
//                    }
//                    if (!flagSearchMinuts) {
//                        startHour += start.charAt(i);
//                    }
//                    if (flagSearchMinuts) {
//                        startMinut += start.charAt(i);
//                    }
//                }
//
//                flagSearchMinuts=false;
//                for (int i = 0; i < exit.length(); i++) {
//                    if(exit.charAt(i)==':'){
//                        flagSearchMinuts = true;
//                        i++;
//                    }
//                    if (!flagSearchMinuts) {
//                        exitHour += exit.charAt(i);
//                    }
//                    if (flagSearchMinuts) {
//                        exitMinut += exit.charAt(i);
//                    }
//                }
//
//                System.out.println(startHour+" : "+startMinut);
//                System.out.println(exitHour+" : "+exitMinut);
//
//                int startHourInt = Integer.parseInt(startHour);
//                int exitHourInt = Integer.parseInt(exitHour);
//                int startMinutInt = Integer.parseInt(startMinut);
//                int exitMinutInt = Integer.parseInt(exitMinut);
//
//                int differenceHours=0;
//                int differenceMinuts=0;
//
//                if(exitHourInt<startHourInt){
//                    differenceHours+=(24-startHourInt);
//                    differenceHours+=exitHourInt;
//                }
//                else{
//                    differenceHours=exitHourInt-startHourInt;
//                }
//
//                if(exitMinutInt<startMinutInt){
//                    differenceMinuts+=(60-startMinutInt);
//                    differenceMinuts+=exitMinutInt;
//                }
//                else{
//                    differenceMinuts+=exitMinutInt-startMinutInt;
//                }
//
//                System.out.println(differenceHours);
//                System.out.println(differenceMinuts);


    }


}