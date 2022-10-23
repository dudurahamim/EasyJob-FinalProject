package com.example.easyjob_finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WorkerForgotPasswordActivity extends AppCompatActivity {
    EditText phoneNumber;
    String messagePassword, messageUserName;
    String details;
    DatabaseReference myRefManagers, myRefWorkers;
    FirebaseDatabase database;
    Button btn;
    boolean flag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_forgot_password);
        database = FirebaseDatabase.getInstance();
        myRefManagers = database.getReference("managers");
        myRefWorkers = database.getReference("workers");
        this.setTitle("Forgot password");

        ActivityCompat.requestPermissions(WorkerForgotPasswordActivity.this, new String[]{
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.SEND_SMS}
                , PackageManager.PERMISSION_GRANTED);

         phoneNumber = findViewById(R.id.editPhoneForForgot);
        btn = findViewById(R.id.btnResendPassword);

        String number = phoneNumber.getText().toString();
        String phone = "tel: "+phoneNumber.getText().toString().trim();


        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#136EF1"));
        actionBar.setBackgroundDrawable(colorDrawable);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRefManagers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            String phoneCheck = dataSnapshot.child("phoneNumber").getValue(String.class);
                            if(phoneCheck.equals(phoneNumber.getText().toString())){
                                flag=false;
                                messagePassword = dataSnapshot.child("password").getValue(String.class);
                                messageUserName = dataSnapshot.child("userName").getValue(String.class);
                                details = "EasyJob (Manager) account details:\nUsername: "+messageUserName+"\nPassword: "+messagePassword;

                                sendSms(dataSnapshot.child("phoneNumber").getValue(String.class), details);

                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });


                if(flag){
                    myRefWorkers.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                String phoneCheck = dataSnapshot.child("phoneNumber").getValue(String.class);
                                if(phoneCheck.equals(phoneNumber.getText().toString()) && flag){
                                    flag=false;
                                    messagePassword = dataSnapshot.child("password").getValue(String.class);
                                    messageUserName = dataSnapshot.child("userName").getValue(String.class);
                                    details = "EasyJob (Worker) account details:\nUsername: "+messageUserName+"\nPassword: "+messagePassword;

                                    sendSms(dataSnapshot.child("phoneNumber").getValue(String.class), details);

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });



    }


    private void sendSms(String phone, String message){
        if(!phone.isEmpty() && !details.isEmpty()){
            SmsManager smsManager = SmsManager.getDefault();

            smsManager.sendTextMessage(phone, null, details, null, null);
            Toast.makeText(WorkerForgotPasswordActivity.this, "Message sent", Toast.LENGTH_LONG).show();
        }
    }
}



































//                SmsManager smsManager = SmsManager.getDefault();
//
//                smsManager.sendTextMessage(phone, null, "hello", null,null );
//                Toast.makeText(WorkerForgotPasswordActivity.this, "Message sent", Toast.LENGTH_LONG).show();
//