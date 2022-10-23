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
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//A screen whose job it is to create a new group of employees if it does not already exist
public class creatGroupOfEmployes extends AppCompatActivity {
    //==============================================================
    //Define database variables and objects for use
    FirebaseDatabase database;
    DatabaseReference myRefManagers;

    EditText workPlace, workCity, workStreet;
    Button send;
    boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_group_of_employes);
        //==============================================================
        //Define database variables and objects for use
        String nameGet = getIntent().getStringExtra("FullName");
        flag = false;
        String putName = nameGet;
        database = FirebaseDatabase.getInstance();
        myRefManagers = database.getReference("managers");

        this.setTitle("Create group");
        workCity = findViewById(R.id.editCity);
        workPlace = findViewById(R.id.editPlaceName);
        workStreet = findViewById(R.id.editStreetName);
        send = findViewById(R.id.btnSendNewGroupDetails);

        TextView title = findViewById(R.id.txtTitleCreatGroup);
        title.setText(nameGet+", Please enter details:");



        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#136EF1"));
        actionBar.setBackgroundDrawable(colorDrawable);

        //Clicking the "OK" button to create a group after filling in details
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRefManagers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                                String name = dataSnapshot.child("userName").getValue(String.class);
                                //******************************************************************
                                //Locating the user from the database and updating the group details

                                if (name.equals(nameGet.toString())) {
                                    dataSnapshot.child("streetWorkPlace").getRef().setValue(workStreet.getText().toString());
                                    dataSnapshot.child("cityWorkPlace").getRef().setValue(workCity.getText().toString());
                                    dataSnapshot.child("workPlace").getRef().setValue(workPlace.getText().toString());
                                    dataSnapshot.child("isHaveGroupOfEmployes").getRef().setValue(true);
                                    flag=true;

                                    Intent intent = new Intent(getApplicationContext(), ManagerAddEmployeeActivity.class);
                                    intent.putExtra("FullName", putName);
                                    startActivity(intent);
                                    finish();
                                    break;
                                }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });



    }


}