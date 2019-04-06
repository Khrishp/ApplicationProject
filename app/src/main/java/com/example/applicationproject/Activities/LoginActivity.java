package com.example.applicationproject.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.applicationproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A login screen that offers login via email/password. The user may register a new account if they have not yet done so.
 */
public class LoginActivity extends AppCompatActivity{

    private String TAG = "LoginActivity"; // used for debugging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // Check if user is signed in (non-null) and send to MainActivity if they are already logged in
        FirebaseUser currentUser = mAuth.getCurrentUser(); // this grabs the user if they are signed in
        if(currentUser != null) { // if they are already signed in, go to the main activity
            goToMain();
        }

        final Button loginButton = (Button)findViewById(R.id.buttonlog);
        final Button registerButton = (Button)findViewById(R.id.buttonReg);


        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar2);

                progressBar.setVisibility(View.VISIBLE);

                loginButton.setVisibility(View.INVISIBLE);
                registerButton.setVisibility(View.INVISIBLE);

                AutoCompleteTextView mEmailView = (AutoCompleteTextView) findViewById(R.id.emailLog);
                EditText mPasswordView = (EditText) findViewById(R.id.passwordLog);
                String email = mEmailView.getText().toString();
                String pass = mPasswordView.getText().toString();

                Log.v(TAG, "Email being passed to function is: " + email);
                Log.v(TAG, "Password being passed to function is: " + pass);


                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Sign-in Successful", Toast.LENGTH_LONG).show();
                            goToMain();
                        }else{
                            Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            registerButton.setVisibility(View.VISIBLE);
                            loginButton.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }});

        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }
        });
    }

    private void goToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToRegister(){
        Intent intent = new Intent(this, RegActivity.class);
        startActivity(intent);
        finish();
    }
}

