package com.example.easyjob_finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WorkerChangeDetails extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRefWorkers;
    String putName;
    String nameGet;
    TextView fullName, mail, phoneNum;
    Button sendDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_change_details);
        database = FirebaseDatabase.getInstance();
        myRefWorkers = database.getReference("workers");
        nameGet = getIntent().getStringExtra("FullName");
        this.setTitle("Change details");


        fullName = findViewById(R.id.editWorkerNewName);
        mail = findViewById(R.id.editWorkerNewMail);
        phoneNum = findViewById(R.id.editWorkerNewPhone);
        sendDetails = findViewById(R.id.btnWorkerUpdateDet);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#136EF1"));
        actionBar.setBackgroundDrawable(colorDrawable);

        sendDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRefWorkers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            String name = dataSnapshot.child("userName").getValue(String.class);
                            String emailCheck = dataSnapshot.child("email").getValue(String.class);
                            if(name.equals(nameGet)) {
                                if (checkValidEmail(mail.getText().toString()) && phoneNum.getText().toString().length()==10) {
                                    dataSnapshot.child("fullName").getRef().setValue(fullName.getText().toString());
                                    dataSnapshot.child("email").getRef().setValue(mail.getText().toString());
                                    dataSnapshot.child("phoneNumber").getRef().setValue(phoneNum.getText().toString());
                                    break;
                                }
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

    public boolean checkValidEmail(String email) {
        boolean point=false;
        boolean strudel=false;
        boolean strudelBeforePoint=false;
        int indexStrudel=0;
        int indexPoint=0;

        for (int i = 0; i < email.length(); i++) {
            if(email.charAt(i)=='.'){
                point = true;
                indexPoint = i;
            }
            if(email.charAt(i)=='@'){
                strudel = true;
                indexStrudel = i;
            }
        }
        if(strudel && point && (indexStrudel<indexPoint)){
            return true;
        }
        return false;
    }
}