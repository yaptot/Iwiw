package com.mobdeve.yapr.iwiw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewRestRmActivity extends AppCompatActivity {
    // TAG declarations

    // component declarations
    private TextView tvAddress;
    private TextView tvLocDistance;
    private TextView tvRatings;
    private TextView tvRateCount;

    private ImageView imvStar1;
    private ImageView imvStar2;
    private ImageView imvStar3;
    private ImageView imvStar4;
    private ImageView imvStar5;

    private ImageView imvPaid;
    private ImageView imvDisability;
    private ImageView imvBidet;

    private TextView tvLocType;
    private TextView tvToiletries;

    // var declarations


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrestrm);

        this.tvAddress = findViewById(R.id.view_tvAddress);
        this.tvLocDistance = findViewById(R.id.view_tvLocDistance);
        this.tvRatings = findViewById(R.id.view_tvRatings);
        this.tvRateCount = findViewById(R.id.view_tvRateCount);

        this.imvStar1 = findViewById(R.id.view_star1);
        this.imvStar2 = findViewById(R.id.view_star2);
        this.imvStar3 = findViewById(R.id.view_star3);
        this.imvStar4 = findViewById(R.id.view_star4);
        this.imvStar5 = findViewById(R.id.view_star5);

        this.imvPaid = findViewById(R.id.view_imvPaid);
        this.imvDisability = findViewById(R.id.view_imvDisability);
        this.imvBidet = findViewById(R.id.view_imvBidet);

        this.tvLocType = findViewById(R.id.view_tvLocType);
        this.tvToiletries = findViewById(R.id.view_tvToiletries);

        Intent gi = getIntent();
        // retrieve restroom details and set components
        String crAddr = gi.getStringExtra(MapsActivity.ADDRESS_TAG);
        String crDist = gi.getStringExtra(MapsActivity.DISTANCE_TAG);
        String crRating = gi.getStringExtra(MapsActivity.RATING_TAG);
        String crRateCount = gi.getStringExtra(MapsActivity.RATE_COUNT_TAG);

        // get filters
//        gi.getStringExtra(MapsActivity.CATEG_LOCTYPE);
//        gi.getStringExtra(MapsActivity.CATEG_PAID);
//        gi.getStringExtra(MapsActivity.CATEG_DISABILITY);
//        gi.getStringExtra(MapsActivity.CATEG_BIDET);

    }
}