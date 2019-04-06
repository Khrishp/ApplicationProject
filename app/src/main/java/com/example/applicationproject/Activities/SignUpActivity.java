package com.example.applicationproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.applicationproject.Objects.Date;
import com.example.applicationproject.R;
import com.example.applicationproject.Adapters.RecyclerViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
    Boolean gotData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Log.v(TAG, "Entering onCreate().");
        TextView theDate = findViewById(R.id.scheduleDate);

        Intent incomingIntent = getIntent();

        date = incomingIntent.getStringExtra("date");
        theDate.setText(date);
        openHours = new ArrayList<>();

        openHours.add("8:00am-9:00am");
        openHours.add("9:00am-10:00am");
        openHours.add("10:00am-11:00am");
        openHours.add("11:00am-12:00pm");
        openHours.add("12:00pm-1:00pm");
        openHours.add("1:00pm-2:00pm");
        openHours.add("2:00pm-3:00pm");
        openHours.add("3:00pm-4:00pm");
        openHours.add("4:00pm-5:00pm");

        FirebaseFirestore fs = FirebaseFirestore.getInstance();

        DocumentReference docRef = fs.collection("shifts").document(date);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){

                        Log.d(TAG, "found document, here's the data: " + document.getData());
                        Date data = document.toObject(Date.class);
                        Log.d(TAG, "declared data");

                        if(!data.getShifts().isEmpty()){
                            Log.d(TAG, "right before for loop");
                            for (String shift : data.getShifts()){
                                {
                                    openHours.remove(shift);
                                    Log.d(TAG, "removing shift - " + shift);

                                }
                            }
                        }
                        Log.d(TAG, "going to start the recycler");
                        initRecyclerView();
                    }
                }
            }
        });
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView Initialized");
        RecyclerView recyclerView = findViewById(R.id.recycler);

        Log.d(TAG, "about to go into recycler view adapter");

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, openHours, date);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}