package com.example.easyjob_finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsActivityWorker extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRefWorkers;
    String nameGet;
    TextView name, userName, phoneNumber, mail, changeDetails, changePassword, logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_worker);
        this.setTitle("Settings");

        database = FirebaseDatabase.getInstance();
        myRefWorkers = database.getReference("workers");
        nameGet = getIntent().getStringExtra("FullName");

        name = findViewById(R.id.txtWorkerName);
        phoneNumber = findViewById(R.id.txtWorkerPhoneNum);
        mail = findViewById(R.id.txtWorkerMail);
        userName = findViewById(R.id.txtWorkerUsername);
        changeDetails = findViewById(R.id.btnChangeDetailsWorker);
        changePassword = findViewById(R.id.btnChangePasswordWorker);
        logOut = findViewById(R.id.btnLogoutWorker);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#136EF1"));
        actionBar.setBackgroundDrawable(colorDrawable);

        myRefWorkers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String fullName = dataSnapshot.child("userName").getValue(String.class);
                    if(fullName.equals(nameGet)){
                        name.setText(dataSnapshot.child("fullName").getValue(String.class));
                        phoneNumber.setText(dataSnapshot.child("phoneNumber").getValue(String.class));
                        mail.setText(dataSnapshot.child("email").getValue(String.class));
                        userName.setText(dataSnapshot.child("userName").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        changeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WorkerChangeDetails.class);
                intent.putExtra("FullName", nameGet);
                startActivity(intent);
                finish();
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WorkerChangePasswordActivity.class);
                intent.putExtra("FullName", nameGet);
                startActivity(intent);
                finish();
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}