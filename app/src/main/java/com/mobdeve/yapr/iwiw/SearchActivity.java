package com.mobdeve.yapr.iwiw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputLayout;

public class SearchActivity extends AppCompatActivity {
    // TAG declarations
    public static final String SEARCH_ACTIVITY = "SearchActivity";
    public static final String QUERY_TAG = "Search Keyword";
    public static final String IS_KEYWORD = "Keyword or Category Filter";
    public static final String CATEG_ID = "Category ID";

    // component declarations
    private TextInputLayout layoutEtSearch;
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

    // TEST
    private Button test_btnReg;
    private Button test_btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initComponents();

        // listener for Search icon
        layoutEtSearch.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strSearch = etSearch.getText().toString();
                // navigate to <Search Results activity>
                Intent i = new Intent(SearchActivity.this, SearchResultsActivity.class);
                i.putExtra(SearchActivity.QUERY_TAG, strSearch);
                i.putExtra(SearchActivity.IS_KEYWORD, true);
                startActivity(i);
            }
        });

        // listener for pressing the "enter" key
        etSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String strSearch = etSearch.getText().toString();
                    Intent i = new Intent(SearchActivity.this, SearchResultsActivity.class);
                    i.putExtra(SearchActivity.QUERY_TAG, strSearch);
                    i.putExtra(SearchActivity.IS_KEYWORD, true);
                    startActivity(i);
                    return true;
                }
                return false;
            }
        });

        // Category references (CATEG_ID, QUERY_TAG)
        // 1 - Bidet
        // 2 - Disability
        // 3 - Location Type
        // 4 - Paid
        // 5 - Toiletries

        // listeners for Bidet tab
        ll_categBidet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigate to <Search Results activity>
                Intent i = new Intent(SearchActivity.this, SearchResultsActivity.class);
                i.putExtra(SearchActivity.QUERY_TAG, "Bidet");
                i.putExtra(SearchActivity.IS_KEYWORD, false);
                i.putExtra(SearchActivity.CATEG_ID, 1);
                startActivity(i);
            }
        });
        ll_categNoBidet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigate to <Search Results activity>
                Intent i = new Intent(SearchActivity.this, SearchResultsActivity.class);
                i.putExtra(SearchActivity.QUERY_TAG, "No bidet");
                i.putExtra(SearchActivity.IS_KEYWORD, false);
                i.putExtra(SearchActivity.CATEG_ID, 1);
                startActivity(i);
            }
        });

        // listeners for Disability tab
        ll_categDisability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigate to <Search Results activity>
                Intent i = new Intent(SearchActivity.this, SearchResultsActivity.class);
                i.putExtra(SearchActivity.QUERY_TAG, "Disability access");
                i.putExtra(SearchActivity.IS_KEYWORD, false);
                i.putExtra(SearchActivity.CATEG_ID, 2);
                startActivity(i);
            }
        });
        ll_categNoDisability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigate to <Search Results activity>
                Intent i = new Intent(SearchActivity.this, SearchResultsActivity.class);
                i.putExtra(SearchActivity.QUERY_TAG, "No disabled");
                i.putExtra(SearchActivity.IS_KEYWORD, false);
                i.putExtra(SearchActivity.CATEG_ID, 2);
                startActivity(i);
            }
        });

        // listeners for Location Type tab
        ll_categMall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigate to <Search Results activity>
                Intent i = new Intent(SearchActivity.this, SearchResultsActivity.class);
                i.putExtra(SearchActivity.QUERY_TAG, "Mall");
                i.putExtra(SearchActivity.IS_KEYWORD, false);
                i.putExtra(SearchActivity.CATEG_ID, 3);
                startActivity(i);
            }
        });
        ll_categRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigate to <Search Results activity>
                Intent i = new Intent(SearchActivity.this, SearchResultsActivity.class);
                i.putExtra(SearchActivity.QUERY_TAG, "Restaurant");
                i.putExtra(SearchActivity.IS_KEYWORD, false);
                i.putExtra(SearchActivity.CATEG_ID, 3);
                startActivity(i);
            }
        });
        ll_categPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigate to <Search Results activity>
                Intent i = new Intent(SearchActivity.this, SearchResultsActivity.class);
                i.putExtra(SearchActivity.QUERY_TAG, "Park");
                i.putExtra(SearchActivity.IS_KEYWORD, false);
                i.putExtra(SearchActivity.CATEG_ID, 3);
                startActivity(i);
            }
        });
        ll_categOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigate to <Search Results activity>
                Intent i = new Intent(SearchActivity.this, SearchResultsActivity.class);
                i.putExtra(SearchActivity.QUERY_TAG, "Others");
                i.putExtra(SearchActivity.IS_KEYWORD, false);
                i.putExtra(SearchActivity.CATEG_ID, 3);
                startActivity(i);
            }
        });

        // listeners for Paid/Free tab
        ll_categPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigate to <Search Results activity>
                Intent i = new Intent(SearchActivity.this, SearchResultsActivity.class);
                i.putExtra(SearchActivity.QUERY_TAG, "Paid");
                i.putExtra(SearchActivity.IS_KEYWORD, false);
                i.putExtra(SearchActivity.CATEG_ID, 4);
                startActivity(i);
            }
        });
        ll_categFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigate to <Search Results activity>
                Intent i = new Intent(SearchActivity.this, SearchResultsActivity.class);
                i.putExtra(SearchActivity.QUERY_TAG, "Free");
                i.putExtra(SearchActivity.IS_KEYWORD, false);
                i.putExtra(SearchActivity.CATEG_ID, 4);
                startActivity(i);
            }
        });

        // listeners for Toiletries tab
        ll_categTissue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigate to <Search Results activity>
                Intent i = new Intent(SearchActivity.this, SearchResultsActivity.class);
                i.putExtra(SearchActivity.QUERY_TAG, "Tissue");
                i.putExtra(SearchActivity.IS_KEYWORD, false);
                i.putExtra(SearchActivity.CATEG_ID, 5);
                startActivity(i);
            }
        });
        ll_categSoap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigate to <Search Results activity>
                Intent i = new Intent(SearchActivity.this, SearchResultsActivity.class);
                i.putExtra(SearchActivity.QUERY_TAG, "Soap");
                i.putExtra(SearchActivity.IS_KEYWORD, false);
                i.putExtra(SearchActivity.CATEG_ID, 5);
                startActivity(i);
            }
        });
        ll_categNapkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigate to <Search Results activity>
                Intent i = new Intent(SearchActivity.this, SearchResultsActivity.class);
                i.putExtra(SearchActivity.QUERY_TAG, "Napkin");
                i.putExtra(SearchActivity.IS_KEYWORD, false);
                i.putExtra(SearchActivity.CATEG_ID, 5);
                startActivity(i);
            }
        });
    }

    public void initComponents() {
        this.layoutEtSearch = findViewById(R.id.layoutEtSearch);
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