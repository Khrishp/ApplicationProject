package com.example.applicationproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegActivity extends AppCompatActivity {

    private static final String TAG = "RegActivity"; // use TAG for Logging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        Button registerButton = (Button)findViewById(R.id.buttonReg);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                EditText mEmail = (EditText)findViewById(R.id.emailReg);
                EditText mPassword = (EditText)findViewById(R.id.passwordReg1);
                EditText mConfirmPassword = (EditText)findViewById(R.id.passwordReg2);
                EditText mName = (EditText)findViewById(R.id.nameReg);
                EditText mAge = (EditText)findViewById(R.id.ageReg);
                EditText mHours = (EditText)findViewById(R.id.hoursReg);

                Log.d(TAG, "about to declare All variables");

                String email = mEmail.getText().toString();
                String pass = mPassword.getText().toString();
                String cpass= mConfirmPassword.getText().toString();
                String name = mName.getText().toString();

                Log.d(TAG, "about to declare integer values");
                Integer age = Integer.parseInt(mAge.getText().toString());
                Integer hours = Integer.parseInt(mHours.getText().toString());

                Log.d(TAG, "this is the string age: " + age);
                Log.d(TAG, "this is the string hours: " + hours);

                Log.d(TAG, "Declared All variables");

                if(pass.equals(cpass)){ //TODO: add checks if bodies are empty or not
                    User user = new User(name,age,hours,Double.valueOf("5093851497"));

                    mAuth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(RegActivity.this, "Account Created", Toast.LENGTH_LONG).show();
                            goToMain();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegActivity.this, "Account Creation Failed", Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Toast.makeText(RegActivity.this, "Passwords don't match", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    boolean isEmailValid(String email){
        if (email.contains("@")){
            return true;
        }else
            return false;
    }

    boolean isPasswordValid(String pass) {
        if (pass.length() < 4) {
            return false;
        } else {
            return true;
        }
    }

    private void goToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}