package com.example.applicationproject.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import com.example.applicationproject.Objects.News;
import com.example.applicationproject.Adapters.NewsViewAdapter;
import com.example.applicationproject.R;
import com.example.applicationproject.Objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * This activity will be able to lead to most of the other activities. It will check
 * SharedPreferences to see if a user is logged in and change it's UI accordingly.
 */
public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    User currentUser;
    ArrayList<String> dateList;
    ArrayList<String> headerList;
    ArrayList<String> bodyList;

    News news;

    private static final String TAG = "MainActivity"; // use TAG for Logging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView userUI = findViewById(R.id.welcomeText);


        final Button volunteerListButton = findViewById(R.id.main_user_list);
        Button calendarButton = findViewById(R.id.main_calendar);
        Button logoutButton = findViewById(R.id.main_logout);
        final Button writeNewsButton = findViewById(R.id.writeNewsButton);
        Button accountButton = findViewById(R.id.main_account);

        volunteerListButton.setVisibility(View.INVISIBLE);
        writeNewsButton.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        DocumentReference docRef = db.collection("users").document(mAuth.getCurrentUser().getEmail());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot name: " + document.getString("name"));
                        currentUser = document.toObject(User.class);
                        userUI.setText("Hello, " + currentUser.getName() + '!');

                        if(currentUser.canEditVolunteers()) {
                            volunteerListButton.setVisibility(View.VISIBLE);
                            writeNewsButton.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        db.collection("news")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            dateList = new ArrayList<>();
                            headerList = new ArrayList<>();
                            bodyList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                news = document.toObject(News.class);
                                dateList.add(news.getDate());
                                headerList.add(news.getHeader());
                                bodyList.add(news.getBody());
                            }
                            initRecyclerView();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        volunteerListButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                goToVolunteerList();
            }
        });
        writeNewsButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNews();
            }
        });
        calendarButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                goToCalendar();
            }
        });
        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAccount();
            }
        });
        logoutButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                signout();
            }
        });

    }

    public void createNews() {
        Intent intent = new Intent(this, WriteNewsActivity.class);
        startActivity(intent);
    }

    /**
     * Sign the user out of the app and go back to the sign-in activity. Terminate main because we
     * don't want the user to see this activity again without logging in
     */
    public void signout(){
        mAuth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToAccount(){
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("name", currentUser.getName());
        startActivity(intent);
    }

    public void goToCalendar() {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }

    public void goToVolunteerList() {
        Intent intent = new Intent(this, VolunteerListActivity.class);
        startActivity(intent);
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView Initialized");
        RecyclerView recyclerView = findViewById(R.id.newslist);

        Log.d(TAG, "about to go into recycler view adapter");

        NewsViewAdapter adapter = new NewsViewAdapter(this, dateList, headerList, bodyList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
