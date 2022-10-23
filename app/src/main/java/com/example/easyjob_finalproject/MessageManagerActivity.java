package com.example.easyjob_finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;

public class MessageManagerActivity extends AppCompatActivity {

    //Defining variables and objects for work
    DatabaseReference myRefWorkers, myRefManagers;
    ArrayList<Worker> workerList;
    RecyclerView recyclerView;
    WorkerScheduleAdapter messageManagerActivityAdapter;
    String nameGet;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_manager);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRefWorkers = database.getReference("workers");
        myRefManagers = database.getReference("managers");
        nameGet = getIntent().getStringExtra("FullName");
        title = findViewById(R.id.txtTitleMessage);
        workerList = new ArrayList<>();
        this.setTitle("Messages");
        MessageManagerActivityAdapter messageManagerActivityAdapter=new MessageManagerActivityAdapter(this, workerList);

        //Change actionBar Color
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#136EF1"));
        actionBar.setBackgroundDrawable(colorDrawable);

        //Locating the username from the database and displaying it in the screen title
        myRefManagers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String userCheck = dataSnapshot.child("userName").getValue(String.class);
                    if(userCheck.equals(nameGet)){
                        title.setText(dataSnapshot.child("fullName").getValue(String.class));

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Creating objects of all the messages between the specific Manager and his employees,
        // and inserting them into a unique list for the purpose of displaying them
        myRefWorkers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(nameGet.equals(dataSnapshot.child("managerUserName").getValue(String.class))) {
                        Worker worker = new Worker(dataSnapshot.child("fullName").getValue(String.class), null, dataSnapshot.child("userName").getValue(String.class), null, null, null,
                                dataSnapshot.child("managerUserName").getValue(String.class), dataSnapshot.child("sunday").getValue(String.class), dataSnapshot.child("monday").getValue(String.class), dataSnapshot.child("tuesday").getValue(String.class),
                                dataSnapshot.child("wednesday").getValue(String.class), dataSnapshot.child("thursday").getValue(String.class), dataSnapshot.child("friday").getValue(String.class), dataSnapshot.child("saturday").getValue(String.class),dataSnapshot.child("lastMessage").getValue(String.class));
                        workerList.add(worker);
                    }
                }
                messageManagerActivityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //=============================================================================


        //Defining RecyclerView:
        recyclerView = findViewById(R.id.recyclerMessageList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageManagerActivityAdapter);



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
//
//                String putName;
//                putName = titleName.getText().toString();
                Intent intent = new Intent(getApplicationContext(), MainScreenManager.class);
                intent.putExtra("FullName", nameGet);
                startActivity(intent);
                finish();

                break;

        }


        return super.onOptionsItemSelected(item);
    }
}