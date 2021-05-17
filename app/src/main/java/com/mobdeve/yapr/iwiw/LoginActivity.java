package com.mobdeve.yapr.iwiw;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    // TAG declarations
    public static final String LOGIN_ACTIVITY = "Login Activity";

    // component declarations
    private EditText etEmail;
    private EditText etPass;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.etEmail = findViewById(R.id.login_etEmail);
        this.etPass = findViewById(R.id.login_etPass);
        this.btnLogin = findViewById(R.id.login_btnLogin);

    }
}