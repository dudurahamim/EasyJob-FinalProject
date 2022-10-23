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
//A screen whose job it is to present to the manager a message that he does not have a group of employees yet,
// in case he is interested in adding an employee to a group that does not exist
public class NoneGroupActivityManager extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRefManagers;
    String nameGet;
    String putName;
    String nameToReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_none_group_manager);
        database = FirebaseDatabase.getInstance();
        myRefManagers = database.getReference("managers");
        nameGet = getIntent().getStringExtra("FullName");
        putName = nameGet;
        this.setTitle("Add employee");

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#136EF1"));
        actionBar.setBackgroundDrawable(colorDrawable);

        TextView title = findViewById(R.id.txtTitleNoneGrope);
        /////////////////////////////////////////////////////////////////////////////////////
        myRefManagers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    String currentUser = dataSnapshot.child("userName").getValue(String.class);
                    if(nameGet.equals(currentUser)){
                        nameToReturn = dataSnapshot.child("fullName").getValue(String.class);
                        title.setText("hey "+nameToReturn);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /////////////////////////////////////////////////////////////////////////////


    }

    //Setting the options menu
    //--------------------------
    @Override
    public boolean onCreateOptionsMenu (Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.manager_add_group, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Button to create a new group of employees:
        switch (item.getItemId()) {
            case R.id.btnCreatGroup:
                String putName;
                putName = nameGet;
                Intent intent = new Intent(getApplicationContext(), creatGroupOfEmployes.class);
                intent.putExtra("FullName", putName);
                startActivity(intent);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}