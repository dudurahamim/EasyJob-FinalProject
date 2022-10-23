package com.example.easyjob_finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManagerAddEmployeeActivity extends AppCompatActivity {


    //==============================================================
    //Define database variables and objects for use
    TextView fullName, email, phoneNumber, password, confirmPassword, userName, note;

    Button send;
    String nameGet;
    String putName;
    String managerUserName;
    Worker worker;
    boolean flag=true, dup=false, flag2=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_add_employee);
        //==============================================================
        //Define database variables and objects for use
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefManagers = database.getReference("managers");
        DatabaseReference myRefWorker = database.getReference("workers");
        nameGet = getIntent().getStringExtra("FullName");
        putName = nameGet;
        this.setTitle("Add employee");

        userName = findViewById(R.id.editUsernameWorker);
        fullName = findViewById(R.id.editFullNameWorker);
        email = findViewById(R.id.editMailWorker);
        phoneNumber = findViewById(R.id.editPhoneNumWorker);
        password = findViewById(R.id.editPasswordWorker);
        confirmPassword = findViewById(R.id.editConfirmPassWorker);
        send = findViewById(R.id.btnAddWorker);
        note = findViewById(R.id.txtErorNote);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#136EF1"));
        actionBar.setBackgroundDrawable(colorDrawable);

        //A button to add a new employee after all the details have been filled
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRefManagers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            String fullName = dataSnapshot.child("userName").getValue(String.class);
                            if(fullName.equals(nameGet) && flag){
                                worker.managerUserName = dataSnapshot.child("userName").getValue(String.class);
                               // myRefWorker.push().setValue(worker);
                                // dataSnapshot.child("list").getRef().push().setValue(worker);
                               // Toast.makeText(ManagerAddEmployeeActivity.this, "Added successfully :)", Toast.LENGTH_SHORT).show();

                                flag=false;

                                break;
                            }
                        }

                        //transition to the main screen
//                        Intent intent = new Intent(getApplicationContext(), MainScreenManager.class);
//                        intent.putExtra("FullName", putName);
//                        startActivity(intent);
//                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            //Build the object of the new worker by the current inputs
                if (checkPassword(password.getText().toString()) && password.getText().toString().equals(confirmPassword.getText().toString()) && checkValidEmail(email.getText().toString()) && phoneNumber.getText().toString().length()==10) {
                   worker = new Worker(fullName.getText().toString(), email.getText().toString(), userName.getText().toString(),
                           password.getText().toString(),"Worker", phoneNumber.getText().toString(), "managerUserName", "null1", "null2","null3","null4","null5","null6","null7","last message");

                   //Locating the manager in the database by his username, and entering the employee in the database
                    myRefWorker.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                if(dataSnapshot.child("userName").getValue(String.class).equals(worker.getUserName()) && !dup){
                                    dup=true;
                                }
                                flag2=true;
                            }
                            if(flag2) {
                                if (!dup) {
                                    myRefWorker.push().setValue(worker);
                                    Intent intent = new Intent(getApplicationContext(), MainScreenManager.class);
                                    intent.putExtra("FullName", putName);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    note.setText("Username already exist !!");
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });






                }
                else{
                    Toast.makeText(ManagerAddEmployeeActivity.this, "One of your details is not OK !", Toast.LENGTH_SHORT).show();
                }



            }


        });

    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.back_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

//Set button back to previous page
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.btnBackPage:

                String putName;
                putName = nameGet;
                Intent intent = new Intent(getApplicationContext(), MainScreenManager.class);
                intent.putExtra("FullName", putName);
                startActivity(intent);
                finish();

                break;

        }


        return super.onOptionsItemSelected(item);
    }
    //----------------------------------------
    //func to check mail validation
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
//----------------------------------------
//func to check password validation
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
            if (password.charAt(i)>='0' && password.charAt(i)<='9'){
                number=true;
            }
        }
        if (upperChar==true && password.length()>=8 && letter==true && number==true){
            valid=true;
        }
        return valid;
    }
}



