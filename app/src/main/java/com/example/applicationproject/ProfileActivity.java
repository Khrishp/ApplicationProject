package com.example.applicationproject;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

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


        Log.d(TAG, "declared textviews");

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        DocumentReference docRef = db.collection("users").document(mAuth.getCurrentUser().getEmail());
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

    }
}
