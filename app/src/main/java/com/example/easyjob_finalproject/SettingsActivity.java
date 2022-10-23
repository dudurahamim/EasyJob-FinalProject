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

public class SettingsActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRefManagers;
    String putName;
    TextView name, position, phoneNumber, mail, changeDetails, changePassword, logOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        database = FirebaseDatabase.getInstance();
        myRefManagers = database.getReference("managers");
        this.setTitle("Settings");

        putName = getIntent().getStringExtra("FullName");

        name = findViewById(R.id.txtSettingsName);
        phoneNumber = findViewById(R.id.txtSettingsPhoneNumber);
        position = findViewById(R.id.txtSettingsPosition);
        mail = findViewById(R.id.txtSettingsMail);
        changeDetails = findViewById(R.id.btnChangeDetails);
        changePassword = findViewById(R.id.btnChangePassword);
      // logOut = findViewById(R.id.btnLogoutManager);

        String nameGet = getIntent().getStringExtra("FullName");

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#136EF1"));
        actionBar.setBackgroundDrawable(colorDrawable);


        myRefManagers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String nameCheck = dataSnapshot.child("userName").getValue(String.class);
                    if(nameCheck.equals(nameGet)){
                        name.setText(dataSnapshot.child("fullName").getValue(String.class));
                        phoneNumber.setText(dataSnapshot.child("phoneNumber").getValue(String.class));
                        position.setText(dataSnapshot.child("position").getValue(String.class));
                        mail.setText(dataSnapshot.child("email").getValue(String.class));
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

                Intent intent = new Intent(getApplicationContext(), ChangeDetailsActivity.class);
                intent.putExtra("FullName", putName);
                startActivity(intent);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                intent.putExtra("FullName", putName);
                startActivity(intent);
            }
        });



    }


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


                Intent intent = new Intent(getApplicationContext(), MainScreenManager.class);
                intent.putExtra("FullName", putName);
                startActivity(intent);
                finish();

                break;

        }


        return super.onOptionsItemSelected(item);
    }
}