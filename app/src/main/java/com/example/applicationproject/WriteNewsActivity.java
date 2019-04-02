package com.example.applicationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.Date;

public class WriteNewsActivity extends AppCompatActivity {
    private static final String TAG = "WriteNewsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Log.v(TAG, "Creating...");
        Log.v(TAG, "Initializing/declaring variables...");
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        Date dateTest = Date.getDefaultInstance();
        String now = (Integer.toString(dateTest.getMonth() + 1));
        now += ("/" + Integer.toString(dateTest.getDay()));
        now += ("/" + Integer.toString(dateTest.getYear()));
        Log.v(TAG, "Today's date is: " + now);

        TextView dateText = findViewById(R.id.dateText);
        dateText.setText(now);
        final Button makeNewsButton = findViewById(R.id.makeNewsButton);

        makeNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText headerText = findViewById(R.id.headerText);
                EditText bodyText = findViewById(R.id.bodyText);
                Date today = Date.getDefaultInstance();
                String date = (Integer.toString(today.getMonth() + 1));
                date += ("/" + Integer.toString(today.getDay()));
                date += ("/" + Integer.toString(today.getYear()));
                final String header = headerText.getText().toString();
                final String body = bodyText.getText().toString();
                final News news = new News(header, body, date);
                Toast.makeText(WriteNewsActivity.this, "News Created", Toast.LENGTH_LONG).show();
                DocumentReference reference = db.collection("news").document(header);
                reference.set(news);
                Intent intent = new Intent(WriteNewsActivity.this, MainActivity.class);
                        startActivity(intent);
            }
        });

    }
}
