package com.example.applicationproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class VolunteerListActivity extends AppCompatActivity {
    public void returnToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
