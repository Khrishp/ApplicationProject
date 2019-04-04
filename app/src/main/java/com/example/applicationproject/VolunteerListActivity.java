package com.example.applicationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class VolunteerListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteerlist);
    }


    public void returnToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToProfile(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}
