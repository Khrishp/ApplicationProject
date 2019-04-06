package com.example.applicationproject.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applicationproject.Objects.News;
import com.example.applicationproject.R;
import com.example.applicationproject.Objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Displays the current information of the user accessing it. This info is taken from Firestore.
 * They can also change jobs here to reflect their position.
 */
public class ProfileActivity extends AppCompatActivity {

    User tempUser;
    User currentUser;
    User viewer;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    DocumentReference currentUserDocument;

    String email;
    String name;
    ArrayList<User> users;

    String TAG = "ProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent incomingIntent = getIntent();

        name = incomingIntent.getStringExtra("name");

        final TextView nameView = findViewById(R.id.nameText);
        final TextView ageView = findViewById(R.id.ageText);
        final TextView emailView = findViewById(R.id.emailText);
        final TextView phoneView = findViewById(R.id.phoneText);
        final TextView hoursView = findViewById(R.id.hoursText);
        final TextView jobView = findViewById(R.id.jobText);
        final Button changeJobButton = findViewById(R.id.changeJobButton);
        final Button deleteUserButton = findViewById(R.id.removeUser);


        Log.d(TAG, "declared textviews");

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        changeJobButton.setVisibility(View.INVISIBLE);
        deleteUserButton.setVisibility(View.INVISIBLE);

        Log.d(TAG, "get current email is: " + mAuth.getCurrentUser().getEmail());

        final DocumentReference doc = db.collection("users").document(mAuth.getCurrentUser().getEmail());
        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "task successful");
                    DocumentSnapshot documentAcc = task.getResult();
                    if (documentAcc.exists()) {
                        viewer = documentAcc.toObject(User.class);
                        if(viewer.canEditInterns()) {
                            changeJobButton.setVisibility(View.VISIBLE);
                            deleteUserButton.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                tempUser = document.toObject(User.class);
                                if(name.equals(tempUser.getName())){
                                    currentUser = tempUser;
                                    email = document.getId();
                                }
                            }

                            Log.d(TAG, "User object created");
                            nameView.setText(currentUser.getName());
                            Log.d(TAG, "User nameView gotten");
                            emailView.setText(email);
                            Log.d(TAG, "User emailView gotten");
                            phoneView.setText(currentUser.getPhoneNumber());
                            Log.d(TAG, "User phoneView # gotten");
                            ageView.setText(currentUser.getAge().toString());
                            Log.d(TAG, "User ageView gotten");
                            hoursView.setText(currentUser.getHoursCount().toString());
                            Log.d(TAG, "User hoursView gotten");

                            db.collection("users").document(email);
                            if(currentUser.getJob() == 3)
                                jobView.setText("Manager");
                            else if(currentUser.getJob() == 2)
                                jobView.setText("Intern");
                            else
                                jobView.setText("Volunteer");
                            Log.d(TAG, "User jobView gotten");

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        changeJobButton.setOnClickListener(new Button.OnClickListener(){
            @Override
                    public void onClick(View v) {
                currentUserDocument = db.collection("users").document(email);

                if (currentUser.getJob() == 1) {
                    currentUser.setJob(2);
                    Toast.makeText(ProfileActivity.this, "Volunteer is now Intern", Toast.LENGTH_SHORT).show();
                } else if (currentUser.getJob() == 2) {
                    currentUser.setJob(3);
                    Toast.makeText(ProfileActivity.this, "Intern is now Admin", Toast.LENGTH_SHORT).show();
                } else {
                    currentUser.setJob(1);
                    Toast.makeText(ProfileActivity.this, "Admin is now Volunteer", Toast.LENGTH_SHORT).show();
                }
                currentUserDocument.set(currentUser);
            }
            });

        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUserDocument = db.collection("users").document(email);
                currentUserDocument.delete();
            }
        });
        }
    }

