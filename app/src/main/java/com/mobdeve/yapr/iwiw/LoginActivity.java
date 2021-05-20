package com.mobdeve.yapr.iwiw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    // TAG declarations
    public static final String LOGIN_ACTIVITY = "LoginActivity";

    // Firebase authentication
    private FirebaseAuth mAuth;

    // component declarations
    private EditText etEmail;
    private EditText etPass;
    private Button btnLogin;
    private TextView tvRegRedirect;

    // var declarations
    private boolean isValid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.etEmail = findViewById(R.id.login_etEmail);
        this.etPass = findViewById(R.id.login_etPass);
        this.btnLogin = findViewById(R.id.login_btnLogin);
        this.tvRegRedirect = findViewById(R.id.login_tvRegRedirect);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();


        // listener for Login btn
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strEmail = etEmail.getText().toString();
                String strPass = etPass.getText().toString();

                Log.d(LOGIN_ACTIVITY, "TRACK: In btnLogin onclick");
                Log.d(LOGIN_ACTIVITY, "EMAIL: " + strEmail);
                // check if fields are empty
                if (strEmail.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please input email", Toast.LENGTH_SHORT).show();
                    isValid = false;
                }

                if (strPass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please input password", Toast.LENGTH_SHORT).show();
                    isValid = false;
                }

                // search for User in firebase, login
                if (isValid)
                    loginUser(strEmail, strPass);
            }
        });

        // listener for redirect to Register screen
        tvRegRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigate to <Register activity>
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    public void loginUser(String email, String password) {

        Log.d(LOGIN_ACTIVITY, "TRACK: In loginUser()");

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(LOGIN_ACTIVITY, "TRACK: In loginUser() onComplete()");
                        if (task.isSuccessful()) {
                            // pass this User for the needed Activity
                            FirebaseUser currUser = mAuth.getCurrentUser();

                            if (currUser != null) {
                                Log.d(LOGIN_ACTIVITY, "Logged in as: " + currUser.getEmail());

                                // navigate to <Maps activity>
                                finish();

                                // TODO: enable Map features for logged in user [ Rate, Review, Add Restroom ]

                            } else {
                                Log.d(LOGIN_ACTIVITY, "Failed login. /n", task.getException());
                                Toast.makeText(LoginActivity.this, "Please check credentials.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });


    }
}