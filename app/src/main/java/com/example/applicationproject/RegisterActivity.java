package com.example.applicationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private EditText mName;
    private EditText mAge;
    private EditText mHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registerButton = (Button)findViewById(R.id.buttonReg);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mEmail = (EditText)findViewById(R.id.emailReg);
                mPassword = (EditText)findViewById(R.id.passwordReg1);
                mConfirmPassword = (EditText)findViewById(R.id.passwordReg2);
                mName = (EditText)findViewById(R.id.nameReg);
                mAge = (EditText)findViewById(R.id.ageReg);
                mHours = (EditText)findViewById(R.id.hoursReg);

                String email = mEmail.toString();
                String pass = mPassword.toString();
                String cpass= mConfirmPassword.toString();
                String name = mName.toString();
                Integer age = Integer.valueOf(mAge.toString());
                Integer hours = Integer.valueOf(mHours.toString());

                if(pass.equals(cpass)){ //TODO: add checks if bodies are empty or not
                    User user = new User(name,age,hours,Double.valueOf("5093851497"));

                    mAuth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(RegisterActivity.this, "Account Created", Toast.LENGTH_LONG).show();
                            goToMain();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, "Account Creation Failed", Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Toast.makeText(RegisterActivity.this, "Passwords don't match", Toast.LENGTH_LONG).show();
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