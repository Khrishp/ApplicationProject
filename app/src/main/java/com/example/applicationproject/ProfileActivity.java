package com.example.applicationproject;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    User user;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    TextView name;
    TextView age;
    TextView email;
    TextView phone;
    TextView job;

    String TAG = "ProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.nameText);
        age = findViewById(R.id.ageText);
        email = findViewById(R.id.emailText);
        phone = findViewById(R.id.phoneText);
        job = findViewById(R.id.jobText);


        Log.d(TAG, "declared textviews");

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        db.collection("users").document(mAuth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "task successful");
                    DocumentSnapshot document = task.getResult();
                    user = document.toObject(User.class);

                    name.setText(user.getName());
                    age.setText(user.getAge());
                    email.setText(mAuth.getCurrentUser().getEmail());
//                    phone.setText("" + user.getPhoneNumber());

                    Log.d(TAG, "end of task");


                }else{
                    Log.d(TAG,"couldnt get the document");
                    Toast.makeText(ProfileActivity.this, "Couldn't get document", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
