package com.example.applicationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Comparator;

public class VolunteerListActivity extends AppCompatActivity {

    FirebaseFirestore db;
    String TAG = "VolunteerListActivity";
    ArrayList<User> userList;
    ArrayList<String> nameList;
    ArrayList<Integer> hoursList;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteerlist);

        db = FirebaseFirestore.getInstance();

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            nameList = new ArrayList<>();
                            hoursList = new ArrayList<>();
                            userList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                user = document.toObject(User.class);
                                userList.add(user);
                            }


                            userList.sort(new Comparator<User>() {
                                @Override
                                public int compare(User o1, User o2) {
                                    if ((int)o1.getName().charAt(0) < (int)o2.getName().charAt(0))
                                        return 1;
                                    else
                                        return 0;
                                }
                            });

                            for(User person : userList){
                                nameList.add(person.getName());
                                hoursList.add(person.getHoursCount());
                            }

                            initRecyclerView();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView Initialized");
        RecyclerView recyclerView = findViewById(R.id.volunteerRecyclerView);

        Log.d(TAG, "about to go into recycler view adapter");

        VolunteerViewAdapter adapter = new VolunteerViewAdapter(this, nameList, hoursList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
