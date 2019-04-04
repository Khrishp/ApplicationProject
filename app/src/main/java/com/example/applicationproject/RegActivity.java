package com.example.applicationproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegActivity extends AppCompatActivity {

    private static final String TAG = "RegActivity"; // use TAG for Logging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        final ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        final Button registerButton = findViewById(R.id.buttonReg);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerButton.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                EditText mEmail = findViewById(R.id.emailReg);
                EditText mPassword = findViewById(R.id.passwordReg1);
                EditText mConfirmPassword = findViewById(R.id.passwordReg2);
                EditText mName = findViewById(R.id.nameReg);
                EditText mAge = findViewById(R.id.ageReg);
                EditText mHours = findViewById(R.id.hoursReg);
                EditText mPhone = findViewById(R.id.phoneReg);

                mEmail.setBackgroundResource(R.drawable.reg_edittext_style);
                mPassword.setBackgroundResource(R.drawable.reg_edittext_style);
                mConfirmPassword.setBackgroundResource(R.drawable.reg_edittext_style);
                mName.setBackgroundResource(R.drawable.reg_edittext_style);
                mAge.setBackgroundResource(R.drawable.reg_edittext_style);
                mHours.setBackgroundResource(R.drawable.reg_edittext_style);
                mPhone.setBackgroundResource(R.drawable.reg_edittext_style);

                Log.d(TAG, "about to declare All variables");

                Boolean emptyFields = false;
                final String email = mEmail.getText().toString();
                String pass = mPassword.getText().toString();
                String cpass= mConfirmPassword.getText().toString();
                String name = mName.getText().toString();

                Log.d(TAG, "about to declare integer values");
                String strAge = mAge.getText().toString();
                String strHours = mHours.getText().toString();
                String strPhone = mPhone.getText().toString();

                if(email.equals("")) {
                    mEmail.setBackgroundResource(R.drawable.reg_edittext_style_red);
                    emptyFields = true;
                }
                if(pass.equals("")) {
                    mPassword.setBackgroundResource(R.drawable.reg_edittext_style_red);
                    emptyFields = true;
                }
                if(cpass.equals("")) {
                    mConfirmPassword.setBackgroundResource(R.drawable.reg_edittext_style_red);
                    emptyFields = true;
                }
                if(name.equals("")) {
                    mName.setBackgroundResource(R.drawable.reg_edittext_style_red);
                    emptyFields = true;
                }

                Integer age = 0;
                Integer hours = 0;
                Double phone = 0.0;

                if(!strAge.equals("")){
                    age = Integer.parseInt(strAge);
                }else {
                    mAge.setBackgroundResource(R.drawable.reg_edittext_style_red);
                    emptyFields = true;
                }
                if (!strHours.equals("")) {
                    hours = Integer.parseInt(strHours);
                } else{
                    mHours.setBackgroundResource(R.drawable.reg_edittext_style_red);
                    emptyFields = true;
                }
                if (!strPhone.equals("")){
                    phone = Double.parseDouble(strPhone);
                } else{
                    mPhone.setBackgroundResource(R.drawable.reg_edittext_style_red);
                    emptyFields = true;
                }

                Log.d(TAG, "this is the age: " + age);
                Log.d(TAG, "this is the hours: " + hours);

                Log.d(TAG, "Declared All variables");

                if(!emptyFields) {
                    if (pass.equals(cpass)) {
                        final User user = new User(name, age, hours, phone);

                        mAuth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(RegActivity.this, "Account Created", Toast.LENGTH_LONG).show();
                                DocumentReference reference = db.collection("users").document(email);
                                reference.set(user);
                                goToMain();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegActivity.this, "Account Creation Failed", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.INVISIBLE);
                                registerButton.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        Toast.makeText(RegActivity.this, "Passwords don't match", Toast.LENGTH_LONG).show();
                        mConfirmPassword.setBackgroundResource(R.drawable.reg_edittext_style_red);
                        mPassword.setBackgroundResource(R.drawable.reg_edittext_style_red);
                        progressBar.setVisibility(View.INVISIBLE);
                        registerButton.setVisibility(View.VISIBLE);
                    }
                }else{
                    Toast.makeText(RegActivity.this,"Please Complete Empty Fields",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    registerButton.setVisibility(View.VISIBLE);
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