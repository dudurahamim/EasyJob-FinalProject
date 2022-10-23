package com.example.easyjob_finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class WorkerLogInActivity extends AppCompatActivity {
    Button logInBtn;
    TextView txtForgetPassword;
    TextView txtWrondDetails;
    EditText userName;
    EditText password;
    boolean isLogIn=false;

    DatabaseReference myRefManagers;
    DatabaseReference myRefWorkers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_log_in);
        this.setTitle("Log in");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRefManagers = database.getReference("managers");
        myRefWorkers = database.getReference("workers");


        getSupportActionBar().hide();

        logInBtn = findViewById(R.id.btnLogInWorker);
        txtForgetPassword = findViewById(R.id.txtForgotPasswordWorker);
        txtWrondDetails = findViewById(R.id.txtWrongDetailsWorker);
        userName = findViewById(R.id.editUserNameWorker);
        password = findViewById(R.id.editLoginPasswordWorker);

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            myRefWorkers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        String currentPassword = dataSnapshot.child("password").getValue(String.class);
                        String currentUserName = dataSnapshot.child("userName").getValue(String.class);

                        if (currentUserName.equals(userName.getText().toString()) && currentPassword.equals(password.getText().toString())) {
                            Toast.makeText(WorkerLogInActivity.this, "Welcome "+dataSnapshot.child("fullName").getValue(String.class)+" !", Toast.LENGTH_SHORT).show();
                            String putName;
                            putName = dataSnapshot.child("userName").getValue(String.class);
                            Intent intent = new Intent(getApplicationContext(), MainScreenWorker.class);
                            intent.putExtra("FullName", putName);
                            startActivity(intent);
                            finish();
                            isLogIn=true;
                        }
                    }
                    if(!isLogIn){
                        Toast.makeText(WorkerLogInActivity.this, "Wrong !", Toast.LENGTH_SHORT).show();
                        txtWrondDetails.setText("One of your details is incorrected !");
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            }
        });


        txtForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WorkerForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}