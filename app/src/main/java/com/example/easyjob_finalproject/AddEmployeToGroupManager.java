package com.example.easyjob_finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddEmployeToGroupManager extends AppCompatActivity {

    TextView title;
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//A screen whose job it is to inform the manager that there is no group of employees at all, so he must create one.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employe_to_group_manager);
        title  = findViewById(R.id.txtNoteToManager);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefManagers = database.getReference("managers");
        String nameGet = getIntent().getStringExtra("FullName");
        this.setTitle("Add employee");

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#136EF1"));
        actionBar.setBackgroundDrawable(colorDrawable);

        myRefManagers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

//Check if that user is not have a group yet.
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String username = dataSnapshot.child("fullName").getValue(String.class);
                    if(nameGet.toString() .equals(username.toString())){
                        if(dataSnapshot.child("isHaveGroupOfEmployes").equals("false")){
                            title.setText("There is no a group of employees yet !");
                        }
                        else{

                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}