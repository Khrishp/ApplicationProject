package com.example.applicationproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView myText;

    private static final String TAG = "MainActivity"; // use TAG for Logging

    @Override
    public void onStart(){
        super.onStart();
    }

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

        FirebaseApp.initializeApp(this);
    }

    public void createNews(View view) {
        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        DatabaseReference myRef2 = database.getReference("test2");

        myRef2.setValue("A more stable test");
        myText = findViewById(R.id.TEMPORARY);
        myRef.setValue(myText);*/
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Log.v(TAG, "got the instance.");
        DatabaseReference myRef = database.getReference("message");
        Log.v(TAG, "got the reference.");
        myRef.setValue("Poop");
        Log.v(TAG, "set the value");
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
