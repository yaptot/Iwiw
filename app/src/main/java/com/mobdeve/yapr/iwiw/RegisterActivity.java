package com.mobdeve.yapr.iwiw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {
    // TAG declarations
    public static final String REG_ACTIVITY = "RegActivity";

    // Firebase authentication
    private FirebaseAuth mAuth;
    private FirebaseUser currUser;

    // component declarations
    private EditText etEmail;
    private EditText etUsername;
    private EditText etPass;
    private EditText etConfirmPass;
    private Button btnReg;
    private TextView tvLoginRedirect;

    // var declarations
    private boolean isValid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initComponents();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    public void initComponents (){
        this.etEmail = findViewById(R.id.reg_etEmail);
        this.etUsername = findViewById(R.id.reg_etUsername);
        this.etPass = findViewById(R.id.reg_etPass);
        this.etConfirmPass = findViewById(R.id.reg_etConfirmPass);
        this.btnReg = findViewById(R.id.reg_btnReg);
        this.tvLoginRedirect = findViewById(R.id.reg_tvLoginRedirect);
    }

    @Override
    public void onStart() {
        super.onStart();

        // listener for Register btn
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isValid = true;

                // get User inputs
                String email = etEmail.getText().toString();
                String username = etUsername.getText().toString();
                String pass = etPass.getText().toString();
                String confirmPass = etConfirmPass.getText().toString();

                // if any fields are empty
                if (email.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "No email input", Toast.LENGTH_SHORT).show();
                    isValid = false;
                } else {
                    // check if valid email
                    if (!isEmailValid(email)){
                        Toast.makeText(RegisterActivity.this, "Please input valid email", Toast.LENGTH_SHORT).show();
                        isValid = false;
                    }
                }

                if (username.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "No username input", Toast.LENGTH_SHORT).show();
                    isValid = false;
                }

                if (pass.isEmpty() || confirmPass.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "No password input", Toast.LENGTH_SHORT).show();
                    isValid = false;
                } else {
                    // check if password matches, valid password
                    if (!pass.equals(confirmPass)) {
                        Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                        isValid = false;
                    } else {
                        if (pass.length() < 8){
                            Toast.makeText(RegisterActivity.this, "Password should be at least 8 characters", Toast.LENGTH_SHORT).show();
                            isValid = false;
                        }
                    }
                }

                // User is considered to be logged in, proceed to Maps activity
                if (isValid) {
                    createUser(email, pass, username);
                }
            }
        });

        // listener for redirect to Login screen
        tvLoginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigate to <Register activity>
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    // for email validation (Ref: https://www.geeksforgeeks.org/implement-email-validator-in-android/)
    public boolean isEmailValid(String strEmail) {
        return !strEmail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(strEmail).matches();
    }

    public void createUser (String email, String password, String uname){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            // Initialize Firebase Auth
                            currUser = mAuth.getCurrentUser();

                            addUsername(uname);
                        } else {
                            Log.d(REG_ACTIVITY, "Failed register. /n", task.getException());
                            Toast.makeText(RegisterActivity.this, "Register Failed. Please check credentials.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void addUsername(String uname) {
        // set username of User
        UserProfileChangeRequest setName = new UserProfileChangeRequest.Builder()
                .setDisplayName(uname)
                .build();

        currUser.updateProfile(setName).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(REG_ACTIVITY, "User registered: " + currUser.getDisplayName());
                    Intent i = new Intent(RegisterActivity.this, MapsActivity.class);
                    Toast.makeText(RegisterActivity.this, "Register Successful!", Toast.LENGTH_SHORT).show();
                    startActivity(i);
                    finish();
                } else {
                    Log.d(REG_ACTIVITY, "Failed set username. /n", task.getException());
                }
            }
        });
    }

}