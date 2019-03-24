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

/**
 * This activity will be able to lead to most of the other activities. It will check
 * SharedPreferences to see if a user is logged in and change it's UI accordingly.
 */
public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    SharedPreferences sharedPref; // TODO: need to store a boolean here that states "Logged in"

    private static final String TAG = "MainActivity"; // use TAG for Logging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button volunteerListButton = (Button)findViewById(R.id.main_user_list);
        Button calendarButton = (Button)findViewById(R.id.main_calendar);
        Button logoutButton = (Button)findViewById(R.id.main_logout);

        mAuth = FirebaseAuth.getInstance();

        volunteerListButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                goToVolunteerList();
            }
        });
//        createNewsButton.setOnClickListener(new Button.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                createNews();
//            }
//        });
        calendarButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                goToCalendar();
            }
        });
        logoutButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                    signout();
            }
        });

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

    }

    public void signout(){
        mAuth.signOut();
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

//    public void updateUi(FirebaseUser user){
//        mAuth = FirebaseAuth.getInstance();
//
//
//    }

}
