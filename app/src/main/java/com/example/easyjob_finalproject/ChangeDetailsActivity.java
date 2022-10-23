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

public class ChangeDetailsActivity extends AppCompatActivity {

    //Define database variables and objects for use
    FirebaseDatabase database;
    DatabaseReference myRefManagers;
    String putName;
    String nameGet;
    TextView fullName, mail, phoneNum;
    Button sendDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_details);
        this.setTitle("Change details");


        //==============================================================
        //Define database variables and objects for use
        database = FirebaseDatabase.getInstance();
        myRefManagers = database.getReference("managers");
        nameGet = getIntent().getStringExtra("FullName");

        fullName = findViewById(R.id.editManagerNewName);
        mail = findViewById(R.id.editManagerNewMail);
        phoneNum = findViewById(R.id.editManagerNewPhone);
        sendDetails = findViewById(R.id.btnManagerSendUpdate);
        //==============================================================

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#136EF1"));
        actionBar.setBackgroundDrawable(colorDrawable);
        sendDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRefManagers.addValueEventListener(new ValueEventListener() {
                    //==============================================================

                    //This locates the user from the database at the click of a button
                    //And also updates its details in the database according to the user input
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
                                }
                            }
                        }
                    }
                    //==============================================================


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



    }

    //==============================================================
    //method to check validation of new password

    public boolean checkPassword(String password) {

        boolean valid=false;
        boolean number=false;
        boolean letter=false;
        boolean upperChar=false;


        for (int i=0;i<password.length();i++){

            if (password.charAt(i)>='A' && password.charAt(i)<='Z'){
                upperChar=true;
            }
            if (password.charAt(i)>='a' && password.charAt(i)<='z'){
                letter=true;
            }
            if (password.charAt(i)>='1' && password.charAt(i)<='9'){
                number=true;
            }
        }
        if (upperChar==true && password.length()>=8 && letter==true && number==true){
            valid=true;
        }
        return valid;
    }
    //==============================================================


    //==============================================================

    //method to check validation of new email address
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
    //==============================================================


}