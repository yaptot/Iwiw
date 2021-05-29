package com.mobdeve.yapr.iwiw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ViewRestRmActivity extends AppCompatActivity {
    // TAG declarations
    public static String ID_TAG = "DocumentId";

    // Gson declaration
    private Gson gson = new Gson();

    // component declarations
    private TextView tvAddress;
    private TextView tvLocDistance;
    private TextView tvRatings;
    private TextView tvRateCount;

    private ImageView imvPaid;
    private ImageView imvDisability;
    private ImageView imvBidet;

    private TextView tvLocType;
    private TextView tvToiletries;

    private ImageView imvStar1;
    private ImageView imvStar2;
    private ImageView imvStar3;
    private ImageView imvStar4;
    private ImageView imvStar5;

    private LinearLayout ll_viewReviews;

    private Button btnAddReview;

    // var declarations
    private FirebaseAuth mAuth; // Firebase authentication
    private FirebaseUser currUser; // Current user logged in (if any)
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String id;
    private String crAddr, crDist, crRating, crRateCount, crPaid, crDisability, crBidet, crLocType;
    ArrayList<String> crToiletries;
    ArrayList<Review> reviews;
    Intent gi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrestrm);

        mAuth = FirebaseAuth.getInstance();
        currUser = mAuth.getCurrentUser();

        this.tvAddress = findViewById(R.id.view_tvAddress);
        this.tvLocDistance = findViewById(R.id.view_tvLocDistance);
        this.tvRatings = findViewById(R.id.view_tvRatings);
        this.tvRateCount = findViewById(R.id.view_tvRateCount);

        this.imvPaid = findViewById(R.id.view_imvPaid);
        this.imvDisability = findViewById(R.id.view_imvDisability);
        this.imvBidet = findViewById(R.id.view_imvBidet);

        this.tvLocType = findViewById(R.id.view_tvLocType);
        this.tvToiletries = findViewById(R.id.view_tvToiletries);

        this.imvStar1 = findViewById(R.id.rate_star1);
        this.imvStar2 = findViewById(R.id.rate_star2);
        this.imvStar3 = findViewById(R.id.rate_star3);
        this.imvStar4 = findViewById(R.id.rate_star4);
        this.imvStar5 = findViewById(R.id.rate_star5);

        this.ll_viewReviews = findViewById(R.id.ll_viewReviews);

        this.btnAddReview = findViewById(R.id.btnAddReview);

        gi = getIntent();
    }

    @Override
    protected void onStart() {
        super.onStart();

        double crLatitude = gi.getDoubleExtra(MapsActivity.LATITUDE_TAG, 0);
        double crLongitude = gi.getDoubleExtra(MapsActivity.LONGITUDE_TAG, 0);
        reviews = new ArrayList<>();
        crToiletries = new ArrayList<>();

        db.collection("restrooms").whereEqualTo("latitude", crLatitude).whereEqualTo("longitude", crLongitude).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    id = task.getResult().getDocuments().get(0).getId();
                    initStrings(task.getResult().getDocuments().get(0));
                    initDisplay();
                    Log.d("DATA", "RESTROOM ID: " + id);
                } else {
                    Log.d("DATA", "DATA: Restroom ID not found");
                }
            }
        });
    }

    private void initStrings(DocumentSnapshot document) {
       Restroom restroom = document.toObject(Restroom.class);

       crAddr = restroom.getName();
       crDisability = restroom.getCateg_disability();
       crBidet = restroom.getCateg_bidet();
       crLocType = restroom.getCateg_loc_type();
       crPaid = restroom.getCateg_paid();
       crToiletries = restroom.getCateg_toiletries();
       crRating = String.valueOf(restroom.getReviews().size());

       int total = 0;
       double average;

       if(restroom.getReviews().size() > 0) {
           for(Review review : restroom.getReviews()) {
               total += review.getRating();
               reviews.add(review);
           }
           average = total / (double) restroom.getReviews().size();
       } else {
           average = 0;
       }

       crRating = String.valueOf(average);
       crRateCount = String.valueOf(restroom.getReviews().size());
       crDist = gi.getStringExtra(MapsActivity.DISTANCE_TAG);
    }

    private void initDisplay() {
        Log.d("DATA", "DATA AFTER LOOP: " + reviews.toString());

        tvAddress.setText(crAddr);
        tvLocDistance.setText(crDist + " m");

        setRating(crRating);
        tvRatings.setText(crRating);
        tvRateCount.setText("(" + crRateCount + " ratings)");

        if(crPaid.equalsIgnoreCase("Paid"))
            imvPaid.setImageResource(R.drawable.ic_dollar_sign);
        else
            imvPaid.setImageResource(R.drawable.ic_dollar_sign_grey);

        if(crDisability.equalsIgnoreCase("Disability access"))
            imvDisability.setImageResource(R.drawable.ic_disability);
        else
            imvDisability.setImageResource(R.drawable.ic_disability_grey);

        if(crBidet.equalsIgnoreCase("Bidet"))
            imvBidet.setImageResource(R.drawable.ic_bidet);
        else
            imvBidet.setImageResource(R.drawable.ic_bidet_grey);

        tvLocType.setText(crLocType);

        for(int i = 0; i < crToiletries.size(); i++) {
            if(i == 0)
                tvToiletries.setText(crToiletries.get(i));
            else
                tvToiletries.append(", " + crToiletries.get(i));
        }

        showReviews(reviews);

        if(currUser != null) {
            btnAddReview.setVisibility(View.VISIBLE);
            btnAddReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ViewRestRmActivity.this, AddReviewActivity.class);
                    i.putExtra(ViewRestRmActivity.ID_TAG, id);

                    startActivity(i);
                }
            });
        }
    }

    private void setRating(String crRating) {
        double roundedRating = Math.round(Double.parseDouble(crRating));

        if(roundedRating == 1) {
            imvStar1.setImageResource(R.drawable.ic_icon_star);
            imvStar2.setImageResource(R.drawable.ic_star_grey);
            imvStar3.setImageResource(R.drawable.ic_star_grey);
            imvStar4.setImageResource(R.drawable.ic_star_grey);
            imvStar5.setImageResource(R.drawable.ic_star_grey);
        }
        else if(roundedRating == 2) {
            imvStar1.setImageResource(R.drawable.ic_icon_star);
            imvStar2.setImageResource(R.drawable.ic_icon_star);
            imvStar3.setImageResource(R.drawable.ic_star_grey);
            imvStar4.setImageResource(R.drawable.ic_star_grey);
            imvStar5.setImageResource(R.drawable.ic_star_grey);
        }
        else if(roundedRating == 3) {
            imvStar1.setImageResource(R.drawable.ic_icon_star);
            imvStar2.setImageResource(R.drawable.ic_icon_star);
            imvStar3.setImageResource(R.drawable.ic_icon_star);
            imvStar4.setImageResource(R.drawable.ic_star_grey);
            imvStar5.setImageResource(R.drawable.ic_star_grey);
        }
        else if(roundedRating == 4) {
            imvStar1.setImageResource(R.drawable.ic_icon_star);
            imvStar2.setImageResource(R.drawable.ic_icon_star);
            imvStar3.setImageResource(R.drawable.ic_icon_star);
            imvStar4.setImageResource(R.drawable.ic_icon_star);
            imvStar5.setImageResource(R.drawable.ic_star_grey);
        }
        else if(roundedRating == 5) {
            imvStar1.setImageResource(R.drawable.ic_icon_star);
            imvStar2.setImageResource(R.drawable.ic_icon_star);
            imvStar3.setImageResource(R.drawable.ic_icon_star);
            imvStar4.setImageResource(R.drawable.ic_icon_star);
            imvStar5.setImageResource(R.drawable.ic_icon_star);
        }
    }

    private void showReviews(ArrayList<Review> reviews) {
        Log.d("TRACK", "TRACK: in showReviews");

        ll_viewReviews.removeAllViews();

        for(Review review : reviews) {
            Log.d("LOOP", "LOOP: hehe");
            LinearLayout llBox = new LinearLayout(ViewRestRmActivity.this);
            llBox.setBackground(getDrawable(R.drawable.shape_details));
            llBox.setPadding(15, 20, 15, 20);
            llBox.setOrientation(LinearLayout.VERTICAL);

            llBox.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(llBox.getLayoutParams());
            marginParams.bottomMargin = 20;
            llBox.setLayoutParams(marginParams);

            ll_viewReviews.addView(llBox);

            TextView tvUsername = new TextView(ViewRestRmActivity.this);
            tvUsername.setText(review.getUsername());
            tvUsername.setTextColor(Color.parseColor("#49c6e5"));
            tvUsername.setTypeface(ResourcesCompat.getFont(ViewRestRmActivity.this, R.font.varela_round), Typeface.BOLD);
            tvUsername.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            marginParams.bottomMargin = 5;
            tvUsername.setLayoutParams(marginParams);
            llBox.addView(tvUsername);

            TextView tvReviewRating = new TextView(ViewRestRmActivity.this);
            tvReviewRating.setText(review.getRating() + " out of 5");
            tvReviewRating.setTypeface(ResourcesCompat.getFont(ViewRestRmActivity.this, R.font.varela_round));
            tvReviewRating.setTextColor(Color.parseColor("#666666"));
            tvReviewRating.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            marginParams.bottomMargin = 10;
            tvReviewRating.setLayoutParams(marginParams);
            llBox.addView(tvReviewRating);

            TextView tvReviewText = new TextView(ViewRestRmActivity.this);
            tvReviewText.setText(review.getText_review());
            tvReviewText.setTypeface(ResourcesCompat.getFont(ViewRestRmActivity.this, R.font.varela_round));
            tvReviewText.setTextColor(Color.parseColor("#1d1d1d"));
            tvReviewText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            llBox.addView(tvReviewText);
        }
    }
}