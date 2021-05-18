package com.mobdeve.yapr.iwiw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.RestrictionsManager;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobdeve.yapr.iwiw.databinding.ActivityMapsBinding;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnMyLocationButtonClickListener {

    private GoogleMap mMap; //Map
    private ActivityMapsBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance(); // Database Instance
    private FusedLocationProviderClient fusedLocationClient; // Location Services
    private LocationCallback locationCallback;

    private ArrayList<Restroom> restrooms = new ArrayList<>(); // Restrooms available
    Location currentLocation; // Current Location

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Instantiate Location Services
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLastLoc();

        // Instantiate Callback Function for lcoation updates
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    currentLocation = location;
                }
            }
        };

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        // Get all restrooms from Firestore
        db.collection("restrooms").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            // When the query is complete
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                // If there are results
                if (task.isSuccessful()) {
                    // Add each restroom to the restrooms ArrayList
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        restrooms.add(document.toObject(Restroom.class));
                    }

                    callMap(mapFragment);
                } else
                    Log.d("query", "NO RESTROOMS");
            }
        });
    }

    // Gets the last location of the user
    public void getLastLoc() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        currentLocation = location;
                        if (location != null) {
                            // Logic to handle location object
                            Log.d("null location", "Location is null");
                        }
                    }
                });
    }

    private void callMap(SupportMapFragment mapFragment) {
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        float dist = 0; // Stores the distance of the closest restroom to the user
        Restroom closest = new Restroom(); // Stores the information of the closest restroom to the user


        for (Restroom restroom : restrooms) {
            LatLng point = new LatLng(restroom.getCoordinates().get(1), restroom.getCoordinates().get(0));

            // Computes the distance of the restroom to the user
            float[] results = new float[1];
            Location.distanceBetween(restroom.getCoordinates().get(1), restroom.getCoordinates().get(0), currentLocation.getLatitude(), currentLocation.getLongitude(), results);
            if (dist == 0 || results[0] < dist) {
                dist = results[0];
                closest = restroom;
            }

            // Add a marker to the map
            mMap.addMarker(
                    new MarkerOptions()
                            .position(point)
                            .title(restroom.getName())
                            .icon(bitmapDescriptorFromVector(this, R.drawable.ic_marker))).setTag(restroom);
        }

        // Creates a popup window to initially show the closest restroom to the user
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View restroomPopup = inflater.inflate(R.layout.restroom_popup, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        final PopupWindow popupWindow = new PopupWindow(restroomPopup, width, height);

        popupWindow.showAtLocation(getWindow().getDecorView().getRootView(), Gravity.BOTTOM, 0, 300);

        // Get the components inside the popup window
        TextView tvAddress = restroomPopup.findViewById(R.id.tvAddress);
        TextView tvLocDistance = restroomPopup.findViewById(R.id.tvLocDistance);
        TextView tvRatings = restroomPopup.findViewById(R.id.tvRatings);
        TextView tvRateCount = restroomPopup.findViewById(R.id.tvRateCount);

        tvAddress.setText(closest.getName());
        tvLocDistance.setText(String.valueOf(dist));
        tvRatings.setText(String.valueOf(closest.getRating()));
        // TODO : how to count users yada -- tvRateCount.setText();
        // TODO : categ filters (recyclerview)


        // Marker onClick Listener
        mMap.setOnMarkerClickListener(marker -> {
            Restroom restroom = (Restroom) marker.getTag();

            Log.d("info", restroom.getName());

            tvAddress.setText(restroom.getName());
            float[] results = new float[1];
            Location.distanceBetween(restroom.getCoordinates().get(1), restroom.getCoordinates().get(0), currentLocation.getLatitude(), currentLocation.getLongitude(), results);

            tvLocDistance.setText(String.valueOf(results[0]));
            tvRatings.setText(String.valueOf(restroom.getRating()));

            return true;
        });

        enableMyLocation();
    }

    // Convert vector to bitmap
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    // Updates location when it changes
    private void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }



}