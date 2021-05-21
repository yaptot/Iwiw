package com.mobdeve.yapr.iwiw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {
    // TAG declarations
    private static final String SR_ACTIVITY = "Search_Results_Activity";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    // dependency declarations
    private FirebaseFirestore db = FirebaseFirestore.getInstance(); // Database Instance
    private FusedLocationProviderClient fusedLocationClient; // Location Services

    // Component declaration
    private TextView tvSearchResults;

    // for RecyclerView component
    private RecyclerView rvSearchResults;
    private ResultsAdapter adapter;


    // var declarations
    ArrayList<Restroom> restroomList;
    ArrayList<RestroomDist> restroomDistArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        // intent from Search Activity
        Intent gi = getIntent();
        String strSearch = gi.getStringExtra(SearchActivity.QUERY_TAG);
        int cID = gi.getIntExtra(SearchActivity.CATEG_ID, 1);

        // instantiate arrays, get last location of user
        restroomList = new ArrayList<>();
        restroomDistArray = new ArrayList<>();

        // check if user used the (1) searchbar or (2) filters
        boolean isKeyword = gi.getBooleanExtra(SearchActivity.IS_KEYWORD, true);
        if (isKeyword) firebaseSearch(strSearch);
        else firebaseSearchCateg(strSearch, cID);

        // pass to rv Adapter when query is finished
        rvSearchResults = findViewById(R.id.rvSearchResults);
        adapter = new ResultsAdapter(restroomDistArray, this);

        this.tvSearchResults = findViewById(R.id.tvSearchResults);
        tvSearchResults.append(" " + "\"" + strSearch + "\"");
    }

    public void firebaseSearch(String strKey) {
        // Creates a reference to the [ restrooms ] collection in Firebase
        CollectionReference restroomDB = db.collection("restrooms");
        // Query to be done within the collection
        Query searchRestrooms = restroomDB.orderBy("name").startAt(strKey).endAt(strKey + "\uf8ff"); // --what is this unicode?

        searchRestrooms.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    Log.d(SR_ACTIVITY, task.getResult().toString());

                    // Add each restroom to the restrooms ArrayList
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        restroomList.add(document.toObject(Restroom.class));
                        Log.d(SR_ACTIVITY, document.get("name").toString());
                    }

                    // for each restroom, compute distance (restroom loc - current loc)
                    for (Restroom restroom : restroomList){
                        computeLocDist(restroom);
                    }

                    for (RestroomDist r : restroomDistArray)
                        Log.d(SR_ACTIVITY, "Results: name - " + r.getRestroom().getName() + "// dist - " + r.getDistance());

                    rvSearchResults.setAdapter(adapter);
                    rvSearchResults.setLayoutManager(new LinearLayoutManager(SearchResultsActivity.this, LinearLayoutManager.VERTICAL, false));

                } else
                    Log.d(SR_ACTIVITY, "Query: No keyword exists in restrooms db");
            }
        });

// -- search if keyword exists from (1) name and (2) each Restroom category

        // sort by Restrooms distance
    }

    public void firebaseSearchCateg(String strCateg, int categID) {
        // Creates a reference to the [ restrooms ] collection in Firebase
        CollectionReference restroomDB = db.collection("restrooms");
        Query searchRestrooms;

        // search Restrooms based on category
        switch (categID){
            case 1: searchRestrooms = restroomDB.whereEqualTo("categ_bidet", strCateg); break;
            case 2: searchRestrooms = restroomDB.whereEqualTo("categ_disability", strCateg); break;
            case 3: searchRestrooms = restroomDB.whereEqualTo("categ_loc_type", strCateg); break;
            case 4: searchRestrooms = restroomDB.whereEqualTo("categ_paid", strCateg); break;
            case 5: searchRestrooms = restroomDB.whereArrayContains("categ_toiletries", strCateg); break;
            default: searchRestrooms = restroomDB.whereEqualTo("name", strCateg); break;
        }

        // get results in db
        searchRestrooms.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    // Add each restroom to the restrooms ArrayList
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        restroomList.add(document.toObject(Restroom.class));
                    }

                    // for each restroom, compute distance (restroom loc - current loc)
                    for (Restroom restroom : restroomList){
                        computeLocDist(restroom);
                    }

                    for (RestroomDist r : restroomDistArray)
                        Log.d(SR_ACTIVITY, "Results: name - " + r.getRestroom().getName() + "// dist - " + r.getDistance());

                    rvSearchResults.setAdapter(adapter);
                    rvSearchResults.setLayoutManager(new LinearLayoutManager(SearchResultsActivity.this, LinearLayoutManager.VERTICAL, false));
                } else
                    Log.d(SR_ACTIVITY, "Query: No searched restroom category");
            }
        });
    }

    public void computeLocDist (Restroom restroom) {
        float[] results = new float[1]; // --distance in meters
        Location.distanceBetween(restroom.getLatitude(), restroom.getLongitude(), MapsActivity.currentLocation.getLatitude(), MapsActivity.currentLocation.getLongitude(), results);

        RestroomDist restroomDist = new RestroomDist(results[0], restroom);
        restroomDistArray.add(restroomDist);
    }

}