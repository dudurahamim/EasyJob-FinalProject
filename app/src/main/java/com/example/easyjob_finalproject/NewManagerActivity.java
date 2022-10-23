package com.example.easyjob_finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

//Registration screen for a new Manager to the system, he must fill in all the displayed fields and press "OK"
public class NewManagerActivity extends AppCompatActivity {

    //Defining variables and objects for work
    EditText fullName, userName, email, password, confirmPassword,phoneNumber;
    ArrayList<String> groupDetails = new ArrayList<>();

    TextView fiddback;
    Button sendBtn;
    int flagDupUsername=1;
    int flagPassword=0;
    int flagInsertedUser=0;
    boolean isExitUser=false;
    TextView logIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_manager);

        logIn = findViewById(R.id.BtnNoveToLogInManager);
        getSupportActionBar().hide();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefManagers = database.getReference("managers");

        //Defining fields to fill
        fiddback = findViewById(R.id.txtNoteFidback);
        fullName = findViewById(R.id.editNewManagerName);
        userName = findViewById(R.id.editNewManagerUserName);
        email = findViewById(R.id.editNewManagerMail);
        password = findViewById(R.id.editPasswordManager);
        confirmPassword = findViewById(R.id.editTextCpasswordManager);
        phoneNumber = findViewById(R.id.editPhoneNumManager);
        sendBtn = findViewById(R.id.btnNewManagerSighup);
        ArrayList<Person> list = new ArrayList<Person>();
        this.setTitle("Manager - register");


        //Change actionBar color
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#136EF1"));
        actionBar.setBackgroundDrawable(colorDrawable);


        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ManagerLogIn.class);
                startActivity(intent);

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passwordString = password.getText().toString();
                String userNameCheck = userName.getText().toString();
                String passwordStringConfirm = confirmPassword.getText().toString();
                System.out.println(passwordString);
                System.out.println(passwordStringConfirm);



                myRefManagers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        flagDupUsername=0;

                        if(flagInsertedUser==0) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String username = dataSnapshot.child("userName").getValue(String.class);
                                String passwordCheck = dataSnapshot.child("password").getValue(String.class);

                                //Checking whether a user already exists in the system
                                // with the same username as the one entered
                                if (username.equals(userNameCheck)) {
                                    flagDupUsername = 1;
                                    break;
                                }
                            }


                            //If such a user does not exist, build a new "manager" object and enter it in the database
                            if (flagDupUsername == 0) {
                                if (checkPassword(password.getText().toString()) && password.getText().toString().equals(confirmPassword.getText().toString())) {
                                    Manager manager = new Manager(fullName.getText().toString(), email.getText().toString(), userName.getText().toString(), password.getText().toString(),
                                           "Manager","null","null","null","null", "null", false,phoneNumber.getText().toString());
                                    fiddback.setText("");
                                    Toast.makeText(NewManagerActivity.this, "Your register is confirmed !", Toast.LENGTH_SHORT).show();
                                    myRefManagers.push().setValue(manager);
                                    flagInsertedUser = 1;
                                    fiddback.setText("");

                                } else {
                                    flagPassword = 1;
                                }
                            } else {
                                fiddback.setText("Username is already taken !");

                            }

                            if (flagPassword == 1) {
                                fiddback.setText("The password is not vaild !");
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


    ///////////////////////method to check validation of password input//////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////
}