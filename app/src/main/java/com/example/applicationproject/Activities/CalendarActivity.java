package com.example.applicationproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;

import com.example.applicationproject.R;


/**
 * Users will tap on the date they would like to view. This will take them to the
 * SignUp activity where they can manage dates/times of their work.
 * Future implementations/ stretch goals:
 * - Days with no more hours to fill will show in a different color like gray.
 * - Days that the particular user has at least one scheduled hour on will show up in a color (red?)
 * - At the bottom, below the calendar, the user's next scheduled hour of work will appear. "Thursday, March 30 @ 3pm"
 */
public class CalendarActivity extends AppCompatActivity{


    private static final String TAG = "CalendarActivity"; // use TAG for Logging
    private CalendarView mCalendarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "Entered onCreate.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        mCalendarView = findViewById(R.id.calendarView);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Log.v(TAG, "Setting dateChangeListener.");
                String date = (month + 1) + " - " + dayOfMonth + " - " + year;
                Log.v(TAG, "The date is: " + date);
                Intent intent = new Intent(CalendarActivity.this, SignUpActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });

    }
    
}
