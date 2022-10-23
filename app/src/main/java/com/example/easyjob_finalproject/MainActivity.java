package com.example.easyjob_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//The main and first screen of the application,
// its role is to navigate between the types of users "manager" or "worker"
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnWorker = findViewById(R.id.btnMainWorker);
        Button btnManager = findViewById(R.id.btnManager);
        this.setTitle("Easy Job");

        getSupportActionBar().hide();

        //manager button
        btnManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                startActivity(intent);

            }
        });

        //worker button
        btnWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WorkerLogInActivity.class);
                startActivity(intent);

            }
        });
    }
}