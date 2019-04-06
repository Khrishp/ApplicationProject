package com.example.applicationproject.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.example.applicationproject.Objects.News;
import com.example.applicationproject.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


/**
 * Admin-only activity.
 * The information typed in is stored as a body, header, and date. These are put into a News object and stored in Firestore.
 */
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
                //get index for the news
                db.collection("news")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    int index = 1000;
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                     index--;
                                }
                                    final News news = new News(header, body, date);
                                    Toast.makeText(WriteNewsActivity.this, "News Created", Toast.LENGTH_LONG).show();
                                    DocumentReference reference = db.collection("news").document(Integer.toString(index));
                                    reference.set(news);
                                    finish();
                            }
            }
        });

    }
});
    }
}
