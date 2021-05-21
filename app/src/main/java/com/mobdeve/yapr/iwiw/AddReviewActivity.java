package com.mobdeve.yapr.iwiw;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddReviewActivity extends AppCompatActivity {

    // Component declarations
    private ImageView rate_star1;
    private ImageView rate_star2;
    private ImageView rate_star3;
    private ImageView rate_star4;
    private ImageView rate_star5;

    private EditText etReviewText;

    private Button btnPushReview;

    // var declarations
    private FirebaseAuth mAuth; // Firebase authentication
    private FirebaseUser currUser; // Current user logged in (if any)
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        initComponents();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();
        currUser = mAuth.getCurrentUser();

        // TODO Add review to db
    }

    private void initComponents() {
        this.rate_star1 = findViewById(R.id.rate_star1);
        this.rate_star2 = findViewById(R.id.rate_star2);
        this.rate_star3 = findViewById(R.id.rate_star3);
        this.rate_star4 = findViewById(R.id.rate_star4);
        this.rate_star5 = findViewById(R.id.rate_star5);

        this.etReviewText = findViewById(R.id.etReviewText);

        this.btnPushReview = findViewById(R.id.btnPushReview);

    }
}