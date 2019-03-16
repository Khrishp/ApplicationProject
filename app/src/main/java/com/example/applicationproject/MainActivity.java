package com.example.applicationproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    SharedPreferences sharedPref; // TODO: need to store a boolean here that states "Logged in"

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
        Button createNewsButton = (Button)findViewById(R.id.createNewsButton);
        Button volunteerButton = (Button)findViewById(R.id.volunteerButton);
        Button loginButton = (Button)findViewById(R.id.loginButton);


        volunteerListButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                goToVolunteerList();
            }
        });
        createNewsButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNews();
            }
        });
        volunteerButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                goToCalendar();
            }
        });
        loginButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


        FirebaseApp.initializeApp(this);
    }

    public void createNews() {
        /* This was working database stuff
        System.out.println("in create news function");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        System.out.println( "got the instance.");
        DatabaseReference myRef = database.getReference("message");
        System.out.println("got the reference.");
        myRef.setValue("Poop");
        System.out.println("set the value");
        */

        db = FirebaseFirestore.getInstance();

        User richard = new User();
        richard.setAge(23);
        richard.setName("Richard Hawkins");
        richard.setPhoneNumber(Double.valueOf("5093851497"));

        db.collection("users").add(richard).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "Document added with ID: " + documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document", e);
            }
        });

    }

    public void login(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
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
