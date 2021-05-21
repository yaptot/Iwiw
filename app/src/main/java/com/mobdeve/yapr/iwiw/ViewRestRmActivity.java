package com.mobdeve.yapr.iwiw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ViewRestRmActivity extends AppCompatActivity {
    // TAG declarations

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

    // var declarations


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrestrm);

        this.tvAddress = findViewById(R.id.view_tvAddress);
        this.tvLocDistance = findViewById(R.id.view_tvLocDistance);
        this.tvRatings = findViewById(R.id.view_tvRatings);
        this.tvRateCount = findViewById(R.id.view_tvRateCount);

        this.imvPaid = findViewById(R.id.view_imvPaid);
        this.imvDisability = findViewById(R.id.view_imvDisability);
        this.imvBidet = findViewById(R.id.view_imvBidet);

        this.tvLocType = findViewById(R.id.view_tvLocType);
        this.tvToiletries = findViewById(R.id.view_tvToiletries);

        this.imvStar1 = findViewById(R.id.view_star1);
        this.imvStar2 = findViewById(R.id.view_star2);
        this.imvStar3 = findViewById(R.id.view_star3);
        this.imvStar4 = findViewById(R.id.view_star4);
        this.imvStar5 = findViewById(R.id.view_star5);

        this.ll_viewReviews = findViewById(R.id.ll_viewReviews);

        Intent gi = getIntent();
        // retrieve restroom details and set components
        String crAddr = gi.getStringExtra(MapsActivity.ADDRESS_TAG);
        String crDist = gi.getStringExtra(MapsActivity.DISTANCE_TAG);
        String crRating = gi.getStringExtra(MapsActivity.RATING_TAG);

        String crRateCount = gi.getStringExtra(MapsActivity.RATE_COUNT_TAG);

        String crPaid = gi.getStringExtra(MapsActivity.CATEG_PAID);
        String crDisability = gi.getStringExtra(MapsActivity.CATEG_DISABILITY);
        String crBidet = gi.getStringExtra(MapsActivity.CATEG_BIDET);
        String crLocType = gi.getStringExtra(MapsActivity.CATEG_LOCTYPE);
        ArrayList<String> crToiletries = gi.getStringArrayListExtra(MapsActivity.CATEG_TOILETRIES);
        ArrayList<String> strReviews = gi.getStringArrayListExtra(MapsActivity.REVIEWS_ARRAY);
        ArrayList<Review> reviews = new ArrayList<>();

        Log.d("DATA", "DATA: " + strReviews.toString());

        for(String str : strReviews) {
            reviews.add(gson.fromJson(str, Review.class));
        }

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

        if(crDisability.equalsIgnoreCase("Disabled access"))
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