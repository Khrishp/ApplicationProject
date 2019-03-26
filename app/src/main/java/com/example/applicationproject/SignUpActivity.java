package com.example.applicationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Here the User will see all of the hours they are signed up for on that date (with a tag so they know they signed up),
 * as well as the free hours that day that they can sign up for. By tapping on a free date, they sign up. By tapping
 * on a date they have signed up for, it cancels the volunteered shift and frees it for others.
 * An User with read-all permissions (ie Admin) would see EVERYONE's shifts for the day.
 */
public class SignUpActivity extends AppCompatActivity {
    private TextView theDate;
    private Button calendarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        theDate = findViewById(R.id.scheduleDate);
        calendarButton = findViewById(R.id.calendarButton);

        Intent incomingIntent = getIntent();
        String date = incomingIntent.getStringExtra("date");
        theDate.setText(date);

        calendarButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, CalendarActivity.class);
                startActivity(intent);

                }
            }
        );
    }
}
