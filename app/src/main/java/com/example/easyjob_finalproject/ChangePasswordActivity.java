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

public class ChangePasswordActivity extends AppCompatActivity {
    //==============================================================
    //Define database variables and objects for use
    FirebaseDatabase database;
    DatabaseReference myRefManagers;
    String putName;
    EditText currentPassword, newPassword, confirmNewPassword;
    Button send;
    TextView txtNote;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //==============================================================
        //Define database variables and objects for use
        setContentView(R.layout.activity_change_password);
        database = FirebaseDatabase.getInstance();
        myRefManagers = database.getReference("managers");

        this.setTitle("Change password");
        txtNote = findViewById(R.id.txtPasswordFidback);
        String nameGet = getIntent().getStringExtra("FullName");
       currentPassword = findViewById(R.id.editCurrentPassword);
       newPassword = findViewById(R.id.editNewPassword);
       confirmNewPassword = findViewById(R.id.editConfirmPassword);
       send = findViewById(R.id.btnUpdateNewPassword);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#136EF1"));
        actionBar.setBackgroundDrawable(colorDrawable);

       send.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               myRefManagers.addValueEventListener(new ValueEventListener() {

                   //A piece of code whose function is to check the correctness of a new password input, before updating it

                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       if (flag){
                           for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                               String name = dataSnapshot.child("userName").getValue(String.class);
                               //Checking the list of conditions before the password is updated - validation, new password compatibility and copy,
                               // failure to enter password already in use
                               if (name.equals(nameGet)) {
                                   if (currentPassword.getText().toString().equals(dataSnapshot.child("password").getValue(String.class))) {
                                       if (confirmNewPassword.getText().toString().equals(newPassword.getText().toString())) {
                                           if (checkPassword(newPassword.getText().toString())) {
                                               dataSnapshot.child("password").getRef().setValue(newPassword.getText().toString());
                                               txtNote.setText("");
                                               Toast.makeText(ChangePasswordActivity.this, "Your password has change !", Toast.LENGTH_SHORT).show();
                                               flag = false;

                                               Intent intent = new Intent(getApplicationContext(), MainScreenManager.class);
                                               intent.putExtra("FullName", nameGet);
                                               startActivity(intent);
                                               finish();
                                               break;

                                           }
                                           txtNote.setText("Invalid new password !");
                                           break;
                                       }
                                       txtNote.setText("Incompatible passwords !");
                                       //dataSnapshot.child("password").getRef().setValue(newPassword.getText().toString());
                                       break;
                                   }
                                   txtNote.setText("Current password is not correct !");
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