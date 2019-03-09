package com.example.applicationproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button volunteerListButton = (Button)findViewById(R.id.volunteerListButton);
        Button volunteerButton = (Button)findViewById(R.id.volunteerButton);

        volunteerListButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                goToVolunteerList();
            }
        });

        volunteerButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                goToCalendar();
            }
        });
    }

    public void goToCalendar() {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }

    public void goToVolunteerList() {
        Intent intent = new Intent(this, VolunteerListActivity.class);
        startActivity(intent);
    }

}
