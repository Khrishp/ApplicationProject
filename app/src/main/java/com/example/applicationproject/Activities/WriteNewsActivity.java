package com.example.applicationproject.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.applicationproject.Objects.News;
import com.example.applicationproject.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class WriteNewsActivity extends AppCompatActivity {
    private static final String TAG = "WriteNewsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Log.v(TAG, "Creating...");
        Log.v(TAG, "Initializing/declaring variables...");
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        final Button makeNewsButton = findViewById(R.id.makeNewsButton);

        makeNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText headerText = findViewById(R.id.headerText);
                EditText bodyText = findViewById(R.id.bodyText);
                EditText dateText = findViewById(R.id.dateText);

                final String header = headerText.getText().toString();
                final String body = bodyText.getText().toString();
                final String date = dateText.getText().toString();
                final News news = new News(header, body, date);
                Toast.makeText(WriteNewsActivity.this, "News Created", Toast.LENGTH_LONG).show();
                DocumentReference reference = db.collection("news").document(date);
                reference.set(news);
                finish();
            }
        });

    }
}
