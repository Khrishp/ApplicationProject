package com.example.applicationproject.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applicationproject.R;
import com.example.applicationproject.Objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Displays the current information of the user accessing it. This info is taken from Firestore.
 * They can also change jobs here to reflect their position.
 */
public class ProfileActivity extends AppCompatActivity {

    User currentUser;
    User user;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    String TAG = "ProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final TextView name = findViewById(R.id.nameText);
        final TextView age = findViewById(R.id.ageText);
        final TextView email = findViewById(R.id.emailText);
        final TextView phone = findViewById(R.id.phoneText);
        final TextView hours = findViewById(R.id.hoursText);
        final TextView job = findViewById(R.id.jobText);
        final Button changeJobButton = findViewById(R.id.changeJobButton);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        //won't need to change this chunk if working correctly. checks current user's permissions
        changeJobButton.setVisibility(View.INVISIBLE);
        DocumentReference doc = db.collection("users").document(mAuth.getCurrentUser().getEmail());
        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "task successful");
                    DocumentSnapshot documentAcc = task.getResult();
                    if (documentAcc.exists()) {
                        currentUser = documentAcc.toObject(User.class);
                        if(currentUser.canEditInterns()) {
                            changeJobButton.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });


        Log.d(TAG, "declared textviews");


        final DocumentReference docRef = db.collection("users").document(mAuth.getCurrentUser().getEmail());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "task successful");
                    DocumentSnapshot documentAcc = task.getResult();
                    if (documentAcc.exists()) {
                        user = documentAcc.toObject(User.class);
                        Log.d(TAG, "User object created");
                        name.setText(user.getName());
                        Log.d(TAG, "User name gotten");
                        email.setText(documentAcc.getId());
                        Log.d(TAG, "User email gotten");
                        phone.setText(user.getPhoneNumber());
                        Log.d(TAG, "User phone # gotten");
                        age.setText(user.getAge().toString());
                        Log.d(TAG, "User age gotten");
                        hours.setText(user.getHoursCount().toString());
                        Log.d(TAG, "User hours gotten");
                        job.setText("Volunteer");
                        Log.d(TAG, "User job gotten");



                        Log.d(TAG, "end of task");
                    }


                }else{
                    Log.d(TAG,"couldn't get the document");
                    Toast.makeText(ProfileActivity.this, "Couldn't get document", Toast.LENGTH_SHORT).show();
                }
            }
        });

        changeJobButton.setOnClickListener(new Button.OnClickListener(){
            @Override
                    public void onClick(View v) {
                if (user.getJob() == 1) {
                    user.setJob(2);
                    Toast.makeText(ProfileActivity.this, "Volunteer is now Intern", Toast.LENGTH_SHORT).show();
                } else if (user.getJob() == 2) {
                    user.setJob(3);
                    Toast.makeText(ProfileActivity.this, "Intern is now Admin", Toast.LENGTH_SHORT).show();
                } else {
                    user.setJob(1);
                    Toast.makeText(ProfileActivity.this, "Admin is now Volunteer", Toast.LENGTH_SHORT).show();
                }
                docRef.set(user);
            }
            });
        }
    }

