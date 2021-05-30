package com.mobdeve.yapr.iwiw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddReviewActivity extends AppCompatActivity {
    // TAG declarations
    public static final String ADD_REVIEW_ACTIVITY = "AddReviewActivity";

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

    private int crRating;
    private String crReview;
    private String currUname;
    private String crID;
    private String date;

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

        // get Username
        currUname = currUser.getDisplayName();

        // get rating from User
        // listener for each star rating (Algo: depending on which star user clicks, there is an equivalent int)
        rate_star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crRating = 1;
                // update rating tray as User selects the star
                rate_star1.setImageResource(R.drawable.ic_icon_star);
                rate_star2.setImageResource(R.drawable.ic_star_grey);
                rate_star3.setImageResource(R.drawable.ic_star_grey);
                rate_star4.setImageResource(R.drawable.ic_star_grey);
                rate_star5.setImageResource(R.drawable.ic_star_grey);
            }
        });

        rate_star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crRating = 2;
                rate_star1.setImageResource(R.drawable.ic_icon_star);
                rate_star2.setImageResource(R.drawable.ic_icon_star);
                rate_star3.setImageResource(R.drawable.ic_star_grey);
                rate_star4.setImageResource(R.drawable.ic_star_grey);
                rate_star5.setImageResource(R.drawable.ic_star_grey);
            }
        });

        rate_star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crRating = 3;
                rate_star1.setImageResource(R.drawable.ic_icon_star);
                rate_star2.setImageResource(R.drawable.ic_icon_star);
                rate_star3.setImageResource(R.drawable.ic_icon_star);
                rate_star4.setImageResource(R.drawable.ic_star_grey);
                rate_star5.setImageResource(R.drawable.ic_star_grey);
            }
        });

        rate_star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crRating = 4;
                rate_star1.setImageResource(R.drawable.ic_icon_star);
                rate_star2.setImageResource(R.drawable.ic_icon_star);
                rate_star3.setImageResource(R.drawable.ic_icon_star);
                rate_star4.setImageResource(R.drawable.ic_icon_star);
                rate_star5.setImageResource(R.drawable.ic_star_grey);
            }
        });

        rate_star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crRating = 5;
                rate_star1.setImageResource(R.drawable.ic_icon_star);
                rate_star2.setImageResource(R.drawable.ic_icon_star);
                rate_star3.setImageResource(R.drawable.ic_icon_star);
                rate_star4.setImageResource(R.drawable.ic_icon_star);
                rate_star5.setImageResource(R.drawable.ic_icon_star);
            }
        });

        // listener for Add Review btn
        btnPushReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get text review from User
                crReview = etReviewText.getText().toString();

                // get date
                Date d = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd/MMMM/yy", Locale.getDefault());
                date = df.format(d);

                Log.d("ADD REVIEW", "In btnPush click");

                // Add review to the Restroom (Ref: https://cloud.google.com/firestore/docs/manage-data/add-data#javaandroid)
                // get Firestore restroom id from intent
                Intent gi = getIntent();
                crID = gi.getStringExtra(ViewRestRmActivity.ID_TAG);

                // Restroom collection
                CollectionReference restroomDB = db.collection("restrooms");

                // Put data in a review object
                Map<String, Object> reviewObj = new HashMap<>();
                reviewObj.put("rating", crRating);
                reviewObj.put("text_review", crReview);
                reviewObj.put("username", currUname);
                reviewObj.put("date", date);

                Log.d("ADD REVIEW", "crRating: " + crRating);
                Log.d("ADD REVIEW", "crReview: " + crReview);
                Log.d("ADD REVIEW", "Username: " + currUname);
                Log.d("ADD REVIEW", "Date: " + date);

                // add review to array of "reviews" attribute of Restroom document
                restroomDB.document(crID)
                        .update("reviews", FieldValue.arrayUnion(reviewObj))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                    Log.d("ADD REVIEW",": review added!");
                                else
                                    Log.d("ADD REVIEW", ": failed.");
                            }
                        });
                finish();
            }
        });
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