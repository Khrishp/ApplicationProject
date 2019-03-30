package com.example.applicationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Log.v(TAG, "Entering onCreate().");
        TextView theDate = (TextView)findViewById(R.id.scheduleDate);
        Button calendarButton = (Button)findViewById(R.id.calendarButton);
        RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.recycler);
        ArrayList<String> openHours;

        //I have suspicions about this line. Is this right? It's from the tutorial I watched but...
        Intent incomingIntent = getIntent();

        String date = incomingIntent.getStringExtra("date");
        theDate.setText(date);
        openHours = new ArrayList();
        mRecyclerView.addOnItemTouchListener(new MyTouchListener(this,
                mRecyclerView,
                new MyTouchListener.OnTouchActionListener() {
                    @Override
                    public void onLeftSwipe(View view, int position) {
//we don't need it at least for now
                        Log.v(TAG, "Left swipe on recycler");
                    }

                    @Override
                    public void onRightSwipe(View view, int position) {
//might not need, but nice to have just in case
                        Log.v(TAG, "Right swipe on recycler");
                    }

                    @Override
                    public void onClick(View view, int position) {
                        Log.v(TAG, "Clicked on recyclerView at position: " + position);
                        //Check if signed up for already or not

                        //If signed up already, remove that hour from the user's schedule

                        //Else add that hour to the user's schedule

                    }
                }));

        calendarButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.v(TAG, "CalendarButton.onClick listener");
                Intent intent = new Intent(SignUpActivity.this, CalendarActivity.class);
                startActivity(intent);
                }
            }
        );
    }
}
