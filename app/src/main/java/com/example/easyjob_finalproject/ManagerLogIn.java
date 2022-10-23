package com.example.easyjob_finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
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

import org.w3c.dom.Text;

//Application login screen on the part of the manager
public class ManagerLogIn extends AppCompatActivity {
    //==============================================================
    //Define database variables and objects for use
    EditText userName, password;
    Vibrator v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_log_in);
        this.setTitle("Log In");

        getSupportActionBar().hide();

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_manager_log_in);
        //==============================================================
        //Define database variables and objects for use
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefManagers = database.getReference("managers");

        userName = findViewById(R.id.editManagerUserName);
        password = findViewById(R.id.editManagerPassword);
        Button signInBtn = findViewById(R.id.btnLogInManager);
        TextView txtForgotPassword = findViewById(R.id.txtForgotPassword);
        TextView txtNewManager = findViewById(R.id.txtNewManager);
        TextView txtFidback = findViewById(R.id.txtNote);


        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#136EF1"));
        actionBar.setBackgroundDrawable(colorDrawable);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               *****************************************************************************
//               * By clicking on the login button, the system verifies in the database      *
//               * that the password and username entered are indeed compatible and correct. *
//               * If so, log in and go to the main screen                                   *
//               * If not, an appropriate error message will be displayed to the user        *
//               *****************************************************************************
                myRefManagers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            String username = dataSnapshot.child("userName").getValue(String.class);
                            String passwordCheck = dataSnapshot.child("password").getValue(String.class);

                            //If the username and password are correct, go to the main screen
                        if(username.equals(userName.getText().toString()) && passwordCheck.equals(password.getText().toString())){
                           txtFidback.setText("");
                            Toast.makeText(ManagerLogIn.this,
                                    "Welcome "+dataSnapshot.child("fullName").getValue(String.class)+" !",
                                    Toast.LENGTH_LONG).show();

                            String putName;
                            putName = dataSnapshot.child("userName").getValue(String.class);
                            Intent intent = new Intent(getApplicationContext(), MainScreenManager.class);
                            intent.putExtra("FullName", putName);
                            startActivity(intent);
                            finish();
                            break;
                        }
                        //If any of the details are incorrect, an error message and a short vibration activation
                        else{
                            txtFidback.setText("One of the details is incorrect !!");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
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

            //Go to the "forgot password" screen
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WorkerForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        //Go to "new Manager" screen
        txtNewManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewManagerActivity.class);
                startActivity(intent);
            }
        });
    }
}