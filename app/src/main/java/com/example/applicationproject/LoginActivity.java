package com.example.applicationproject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private String TAG = "LoginActiviy"; // used for debugging

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser(); // this grabs the user if they are signed in
        //updateUI(currentUser); TODO: update ui if they have already signed in
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = (Button)findViewById(R.id.email_sign_in_button);
        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmailView = (AutoCompleteTextView)findViewById(R.id.email);
                mPasswordView = (EditText)findViewById(R.id.password);
                String email = mEmailView.getText().toString();
                String pass = mPasswordView.getText().toString();

                Log.v(TAG, "Email being passed to function is: " + email);
                Log.v(TAG, "Password being passed to function is: " + pass);

                if(!isEmailValid(email))
                    Toast.makeText(LoginActivity.this, "Invalid Email", Toast.LENGTH_LONG).show();
                if(!isPasswordValid(pass))
                    Toast.makeText(LoginActivity.this, "Invalid Password", Toast.LENGTH_LONG).show();
                else
                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Log.d(TAG, "createUserWithEmail: Success");
                                FirebaseUser user = mAuth.getCurrentUser(); //TODO: update UI accordingly
                                updateUi(user);
                            } else{
                                Log.w(TAG, "createUserWithEmail: Failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                updateUi(null);
                            }
                        }
                    });
            }
        });
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with more logic?
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with more logic?
        return password.length() > 4;
    }

    private void updateUi(FirebaseUser user){
        return;
    }

}

