package com.example.applicationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class CalendarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Button goBack = (Button)findViewById(R.id.backButton);

        goBack.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                returnToMain();
            }
        });
    }

    public void returnToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}