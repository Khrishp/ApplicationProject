package com.example.applicationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Here the User will see all of the hours they are signed up for on that date (with a tag so they know they signed up),
 * as well as the free hours that day that they can sign up for. By tapping on a free date, they sign up. By tapping
 * on a date they have signed up for, it cancels the volunteered shift and frees it for others.
 * An User with read-all permissions (ie Admin) would see EVERYONE's shifts for the day.
 */
public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";

    ArrayList<String> openHours;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Log.v(TAG, "Entering onCreate().");
        TextView theDate = (TextView) findViewById(R.id.scheduleDate);

        Intent incomingIntent = getIntent();

        date = incomingIntent.getStringExtra("date");
        theDate.setText(date);
        openHours = new ArrayList<>();

        openHours.add("8:00am-9:00am");
        openHours.add("9:00am-10:00am");
        openHours.add("10:00am-11:00am");
        openHours.add("11:00am-12:00pm");
        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView Initialized");
        RecyclerView recyclerView = findViewById(R.id.recycler);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, openHours, date);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
