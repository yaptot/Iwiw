package com.mobdeve.yapr.iwiw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class SearchActivity extends AppCompatActivity {
    // TAG declarations
    public static final String SEARCH_ACTIVITY = "Search Activity";
    public static final String KEYWORD_QUERY = "Search Keyword";
    public static final String CATEG_QUERY = "Search Filter";

    // component declarations
    private Button btnSearch;
    private EditText etSearch;
    private LinearLayout ll_categFree;
    private LinearLayout ll_categPaid;
    private LinearLayout ll_categDisability;
    private LinearLayout ll_categNoDisability;
    private LinearLayout ll_categBidet;
    private LinearLayout ll_categNoBidet;
    private LinearLayout ll_categMall;
    private LinearLayout ll_categRestaurant;
    private LinearLayout ll_categPark;
    private LinearLayout ll_categOthers;
    private LinearLayout ll_categTissue;
    private LinearLayout ll_categSoap;
    private LinearLayout ll_categNapkin;

    // local var declarations



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initComponents();

        // listener for Search btn
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strSearch = etSearch.getText().toString();
                // navigate to <Search Results activity>
                Intent i = new Intent(SearchActivity.this, SearchResultsActivity.class);
                i.putExtra(SearchActivity.KEYWORD_QUERY, strSearch);
                startActivity(i);
            }
        });

        // listeners for each category tab
        ll_categFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigate to <Search Results activity>
                Intent i = new Intent(SearchActivity.this, SearchResultsActivity.class);
                i.putExtra(SearchActivity.CATEG_QUERY, "Free");
                startActivity(i);
            }
        });
    }

    public void initComponents() {
        this.btnSearch = findViewById(R.id.search_btnSearch);
        this.etSearch = findViewById(R.id.etSearch);
        this.ll_categFree = findViewById(R.id.ll_categFree);
        this.ll_categPaid = findViewById(R.id.ll_categPaid);
        this.ll_categDisability = findViewById(R.id.ll_categDisability);
        this.ll_categNoDisability = findViewById(R.id.ll_categNoDisability);
        this.ll_categBidet = findViewById(R.id.ll_categBidet);
        this.ll_categNoBidet = findViewById(R.id.ll_categNoBidet);
        this.ll_categMall = findViewById(R.id.ll_categMall);
        this.ll_categRestaurant = findViewById(R.id.ll_categRestaurant);
        this.ll_categPark = findViewById(R.id.ll_categPark);
        this.ll_categOthers = findViewById(R.id.ll_categOthers);
        this.ll_categTissue = findViewById(R.id.ll_categTissue);
        this.ll_categSoap = findViewById(R.id.ll_categSoap);
        this.ll_categNapkin = findViewById(R.id.ll_categNapkin);
    }
}