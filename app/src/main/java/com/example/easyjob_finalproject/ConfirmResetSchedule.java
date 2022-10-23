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

//=============================================================================
//The function of this screen is to make sure that the user really wants,
// and is sure that he wants to reset the work schedule
//=============================================================================
public class ConfirmResetSchedule extends AppCompatActivity {

    //==============================================================
    //Define database variables and objects for use
    DatabaseReference myRefWorkers, myRefManagers;
    TextView title;
    Button btn;
    String nameGet;
    String name;
    boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_reset_schedule);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //==============================================================
        //Define database variables and objects for use
        myRefWorkers = database.getReference("workers");
        myRefManagers = database.getReference("managers");

        this.setTitle("Confirm reset schedule");

       title = findViewById(R.id.txtTitleConfirm);
       btn = findViewById(R.id.btnConfirmReset);
        nameGet = getIntent().getStringExtra("FullName");

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#136EF1"));
        actionBar.setBackgroundDrawable(colorDrawable);

        //A code snippet that locates the user from the database and displays his name on the screen
       myRefManagers.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                   String userCheck = dataSnapshot.child("userName").getValue(String.class);
                   if(nameGet.equals(userCheck)){
                       name = dataSnapshot.child("fullName").getValue(String.class);
                       title.setText(name+", Are you sure you want to reset\n the work schedule ?");
                   }
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });


       //At the click of a button, all the fields of the work arrangement for all employees are reset (" ")
       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               myRefWorkers.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                           if(nameGet.equals(dataSnapshot.child("managerUserName").getValue(String.class))) {
                               // String putName = dataSnapshot.child("userName").getValue(String.class);

                                    dataSnapshot.child("sunday").getRef().setValue("");
                                    dataSnapshot.child("monday").getRef().setValue("");
                                    dataSnapshot.child("tuesday").getRef().setValue("");
                                    dataSnapshot.child("wednesday").getRef().setValue("");
                                    dataSnapshot.child("thursday").getRef().setValue("");
                                    dataSnapshot.child("friday").getRef().setValue("");
                                    dataSnapshot.child("saturday").getRef().setValue("");



                           }
                       }
                       //transition to the work schedule screen (empty)
                       Intent intent = new Intent(getApplicationContext(), ManagerWorkSchedule.class);
                       intent.putExtra("FullName", nameGet);
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


    //Setting a button on the main bar whose function is to return to the previous screen
    @Override
    public boolean onCreateOptionsMenu (Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.back_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.btnBackPage:

//back to previous screen
                Intent intent = new Intent(getApplicationContext(), ManagerWorkSchedule.class);
                intent.putExtra("FullName", nameGet);
                startActivity(intent);
                finish();

                break;

        }


        return super.onOptionsItemSelected(item);
    }

}