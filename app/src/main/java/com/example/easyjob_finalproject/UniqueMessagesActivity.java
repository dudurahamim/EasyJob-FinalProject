package com.example.easyjob_finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UniqueMessagesActivity extends AppCompatActivity {

    DatabaseReference myRefWorkers, myRefManagers, myRefMessages;
    ArrayList <Message> list;
    RecyclerView recyclerView;

    String workerUserName, managerUserName;
    TextView nameWorkerTitle;
    TextView managerNameTitle, workerNameTitle;
    UniqMessageAdapter uniqMessageAdapter;
    EditText typeMessage;
    Button btnSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unique_messages);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRefWorkers = database.getReference("workers");
        myRefManagers = database.getReference("managers");
        myRefMessages = database.getReference("messages");
        this.setTitle("Messages");
        list = new ArrayList<>();
        workerUserName = getIntent().getStringExtra("WorkerUserName");
        managerUserName = getIntent().getStringExtra("ManagerUserName");
        uniqMessageAdapter = new UniqMessageAdapter(this, list);
        managerNameTitle = findViewById(R.id.txtTitleUniqManagerMessage);
        workerNameTitle = findViewById(R.id.txtTitleWorkerUniqMessage);
        typeMessage = findViewById(R.id.editPersonalMessage);
        btnSend = findViewById(R.id.btnSendMessage);

        System.out.println(workerUserName);
        System.out.println(managerUserName);


        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#136EF1"));
        actionBar.setBackgroundDrawable(colorDrawable);

        myRefManagers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String userCheck = dataSnapshot.child("userName").getValue(String.class);
                    if(userCheck.equals(managerUserName)){
                        managerNameTitle.setText(dataSnapshot.child("fullName").getValue(String.class).toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //===============================================
        myRefWorkers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String userCheck = dataSnapshot.child("userName").getValue(String.class);
                    if(userCheck.equals(workerUserName)){
                        workerNameTitle.setText(dataSnapshot.child("fullName").getValue(String.class).toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        myRefMessages.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                   String workerUserCheck = dataSnapshot.child("workerUserName").getValue(String.class);
                   if(workerUserCheck.equals(workerUserName)){
                       Message message = new Message(dataSnapshot.child("managerUserName").getValue(String.class),
                               dataSnapshot.child("workerUserName").getValue(String.class),
                               dataSnapshot.child("textMessage").getValue(String.class),
                               dataSnapshot.child("nameOfSender").getValue(String.class),
                               dataSnapshot.child("lastMessage").getValue(String.class));

                       list.add(message);
                   }
               }
                uniqMessageAdapter.notifyDataSetChanged();
            }




            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message(managerUserName, workerUserName,
                        typeMessage.getText().toString(), managerNameTitle.getText().toString(),
                        typeMessage.getText().toString());
                myRefMessages.push().setValue(message);


                myRefWorkers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            String workerUserCheck = dataSnapshot.child("userName").getValue(String.class);
                            if(workerUserCheck.equals(workerUserName)){
                                dataSnapshot.child("lastMessage").getRef().setValue(typeMessage.getText().toString());
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }


        });

        recyclerView = findViewById(R.id.recyclerViewUniqMessage);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(uniqMessageAdapter);
    }
}