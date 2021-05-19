package com.mobdeve.yapr.iwiw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.firestore.core.Query;

public class SearchResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Intent gi = getIntent();

        // intent from KEYWORD_QUERY
        String strSearch = gi.getStringExtra(SearchActivity.KEYWORD_QUERY);
        // -- search if keyword exists from (1) name and (2) each Restroom category


        // intent from CATEG_QUERY
        String strCateg = gi.getStringExtra(SearchActivity.CATEG_QUERY);
        // put code ID for each categ? (e.g. 1 - Paid, 2 - Free...)

        // pass to rv Adapter when query is finished
    }

    public void firebaseSearch(String strKey) {
        Query searchRestrooms = db.collection("restrooms").startAt(strKey).endAt(strKey + "\\uf8ff"); // --what is this unicode?


    }
}