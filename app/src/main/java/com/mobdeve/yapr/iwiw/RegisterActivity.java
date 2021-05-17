package com.mobdeve.yapr.iwiw;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    // TAG declarations
    public static final String REG_ACTIVITY = "Reg Activity";

    // component declarations
    private EditText etEmail;
    private EditText etPass;
    private EditText etConPass;
    private Button btnReg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.etEmail = findViewById(R.id.reg_etEmail);
        this.etPass = findViewById(R.id.reg_etPass);
        this.etConPass = findViewById(R.id.reg_etConfirmPass);

    }
}