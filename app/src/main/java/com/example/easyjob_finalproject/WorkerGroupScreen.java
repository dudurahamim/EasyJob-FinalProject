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
import java.util.HashMap;

public class WorkerGroupScreen extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRefManagers;
    DatabaseReference listRef;
    RecyclerView recyclerView;
    WorkerAdapter workerAdapter;
    String managerUserName;
    ArrayList<Worker> list;
    String nameGet;
    TextView title;
    TextView titleName;
    String nameToReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_group_screen);
        database = FirebaseDatabase.getInstance();
        myRefManagers = database.getReference("managers");
        DatabaseReference myRefWorker = database.getReference("workers");
        nameGet = getIntent().getStringExtra("FullName");
        titleName = findViewById(R.id.txtManagerName);
        this.setTitle("Employees");

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#136EF1"));
        actionBar.setBackgroundDrawable(colorDrawable);

        /////////////////////////////////////////////////////////////////////////////////////
        myRefManagers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    String currentUser = dataSnapshot.child("userName").getValue(String.class);
                    if(nameGet.equals(currentUser)){
                        nameToReturn = dataSnapshot.child("fullName").getValue(String.class);
                        titleName.setText(nameToReturn);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /////////////////////////////////////////////////////////////////////////////



        list = new ArrayList<>();
        workerAdapter = new WorkerAdapter(this, list);
        title = findViewById(R.id.txtGroupTitle);
        myRefManagers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String name = dataSnapshot.child("userName").getValue().toString();
                    if(name.equals(nameGet)){
                        title.setText(dataSnapshot.child("workPlace").getValue().toString()+", "+dataSnapshot.child("cityWorkPlace").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //==============================================================
        myRefManagers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String userName = dataSnapshot.child("userName").getValue(String.class);
                    if(userName.equals(nameGet)){
                        managerUserName = dataSnapshot.child("userName").getValue(String.class);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



       myRefWorker.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                   String name = dataSnapshot.child("fullName").getValue().toString();
                   String userCheckName = dataSnapshot.child("managerUserName").getValue(String.class);
                        if(managerUserName.equals(userCheckName)) {
                            Worker worker = new Worker(dataSnapshot.child("fullName").getValue().toString(), null, null, null, null, null, null,
                                    null, null, null, null, null, null,null,null);
                            list.add(worker);
                        }

               }
               workerAdapter.notifyDataSetChanged();
           }
           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });



        recyclerView = findViewById(R.id.recyclerWorkers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(workerAdapter);

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