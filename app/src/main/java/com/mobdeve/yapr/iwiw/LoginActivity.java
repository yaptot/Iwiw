package com.mobdeve.yapr.iwiw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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


    // var declarations
    private EditText etEmail;
    private EditText etPass;
    private Button btnLogin;
    private TextView tvRegRedirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.etEmail = findViewById(R.id.login_etEmail);
        this.etPass = findViewById(R.id.login_etPass);

        // listener for Login btn
        this.btnLogin = findViewById(R.id.login_btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if login successful

                // --inside Login method, enable map features

                // navigate to Maps activity
            }
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // pass this User for the needed Activity
                            FirebaseUser currUser = mAuth.getCurrentUser();

                            if (currUser != null) {
                                Log.d(LOGIN_ACTIVITY, "Logged in as: " + currUser.getEmail());


                                // enable Map features for logged in user [ Rate, Review, Add Restroom ]
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.d(LOGIN_ACTIVITY, "Failed login. /n", task.getException());
                                Toast.makeText(LoginActivity.this, "Please check credentials.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });


    }
}