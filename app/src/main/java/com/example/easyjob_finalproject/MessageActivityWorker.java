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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageActivityWorker extends AppCompatActivity {

    //Defining variables and objects for work
    DatabaseReference myRefWorkers, myRefManagers, myRefMessages;
    ArrayList<Message> list;
    RecyclerView recyclerView;

    String workerUserName, managerUserName, workerName;
    TextView nameWorkerTitle;
    TextView managerNameTitle, workerNameTitle;
    UniqMessageAdapter uniqMessageAdapter;
    EditText typeMessage;
    Button btnSend;
    boolean flag=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_worker);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRefWorkers = database.getReference("workers");
        myRefManagers = database.getReference("managers");
        myRefMessages = database.getReference("messages");
        list = new ArrayList<>();
        workerUserName = getIntent().getStringExtra("WorkerUserName");
        managerUserName = getIntent().getStringExtra("ManagerUserName");
        nameWorkerTitle = findViewById(R.id.txtYourName);
        managerNameTitle = findViewById(R.id.txtYourManager);
        btnSend = findViewById(R.id.btnWorkerSendMessage);
        typeMessage = findViewById(R.id.editTypeMessage);
        uniqMessageAdapter = new UniqMessageAdapter(this, list);
        this.setTitle("Messages");

        //Change the actionBar color
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#136EF1"));
        actionBar.setBackgroundDrawable(colorDrawable);


        //Running on the database to find the name of the employee's manager and display it
        myRefManagers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    if(dataSnapshot.child("userName").getValue(String.class).equals(managerUserName)){
                        String managerName = dataSnapshot.child("fullName").getValue(String.class);
                        managerNameTitle.setText("Your manager: "+managerName);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //=============================================================================

        //Running on the database to find the name of the employee and display it
        myRefWorkers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("userName").getValue(String.class).equals(workerUserName)) {
                        workerName = dataSnapshot.child("fullName").getValue(String.class);
                        nameWorkerTitle.setText(dataSnapshot.child("fullName").getValue(String.class));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //=============================================================================


        //Creating objects of all the messages between the specific employee and his manager,
        // and inserting them into a unique list for the purpose of displaying them
        myRefMessages.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                String currentWorkerUname = dataSnapshot.child("workerUserName").getValue(String.class);
                if(currentWorkerUname.equals(workerUserName)){
                    Message message = new Message(dataSnapshot.child("managerUserName").getValue(String.class), dataSnapshot.child("workerUserName").getValue(String.class),
                            dataSnapshot.child("textMessage").getValue(String.class), dataSnapshot.child("nameOfSender").getValue(String.class), dataSnapshot.child("lastMessage").getValue(String.class));
                    list.add(message);
                }

                uniqMessageAdapter.notifyDataSetChanged();
            }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //=============================================================================


        //Defining RecyclerView:
        recyclerView = findViewById(R.id.recyclerViewWorkerMessages);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(uniqMessageAdapter);



        //button to send a message
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message(managerUserName, workerUserName, typeMessage.getText().toString(), workerName, typeMessage.getText().toString() );
                myRefMessages.push().setValue(message);

                myRefWorkers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                String workerUserCheck = dataSnapshot.child("userName").getValue(String.class);
                                if(workerUserCheck.equals(workerUserName) && flag){
                                    //Updating the last message of the specific employee:---->
                                    dataSnapshot.child("lastMessage").getRef().setValue(typeMessage.getText().toString());
                                    Toast.makeText(MessageActivityWorker.this, "Message sent", Toast.LENGTH_SHORT).show();
                                    flag=false;
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