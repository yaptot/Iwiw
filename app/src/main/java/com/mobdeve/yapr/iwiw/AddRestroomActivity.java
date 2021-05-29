package com.mobdeve.yapr.iwiw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AddRestroomActivity extends AppCompatActivity {
    // TAG declarations
    public static final String ADD_RESTROOM_ACTIVITY = "AddRestroomActivity";

    // Component declarations
    private EditText tvRestroomName;
    private ImageView imvPaid;
    private ImageView imvDisability;
    private ImageView imvBidet;
    private AutoCompleteTextView actvLocType;
    private Button btnAddRestroom;
    private SwitchMaterial swTissue;
    private SwitchMaterial swSoap;
    private SwitchMaterial swNapkin;

    // var declarations
    private static final String[] LOC_TYPES = new String[] {
      "Mall", "Restaurant", "Park", "Others"
    };

    // for category icon buttons
    private boolean isPaid;
    private boolean isDisability;
    private boolean isBidet;

    // for switch state of sw category buttons
    private String strTissue;
    private String strSoap;
    private String strNapkin;

    // for Firestore db
    private FirebaseAuth mAuth; // Firebase authentication
    private FirebaseUser currUser; // Current user logged in (if any)
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restroom);

        initComponents();

        // populating dropdown loc_type
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.loc_type_item, LOC_TYPES);
        actvLocType.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // listener for Paid toggle btn
        imvPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPaid) {
                    isPaid = true;
                    imvPaid.setImageResource(R.drawable.ic_dollar_sign);
                }
                else {
                    isPaid = false;
                    imvPaid.setImageResource(R.drawable.ic_dollar_sign_grey);
                }
            }
        });

        // listener for Disability toggle btn
        imvDisability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isDisability) {
                    isDisability = true;
                    imvDisability.setImageResource(R.drawable.ic_disability);
                }
                else {
                    isDisability = false;
                    imvDisability.setImageResource(R.drawable.ic_disability_grey);
                }
            }
        });

        // listener for Bidet toggle btn
        imvBidet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isBidet) {
                    isBidet = true;
                    imvBidet.setImageResource(R.drawable.ic_bidet);
                }
                else {
                    isBidet = false;
                    imvBidet.setImageResource(R.drawable.ic_bidet_grey);
                }
            }
        });


        // listener for 'Add Restroom' btn
        btnAddRestroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check string values
                String crName = tvRestroomName.getText().toString();
                String strPaid, strDisability, strBidet;
                String strLocType = actvLocType.getText().toString();
                ArrayList<String> strToiletriesList = new ArrayList<>();

                // DATA VALIDATION before inserting into db
                if(!isPaid) strPaid = "Paid";
                else strPaid = "Free";

                if(!isDisability) strDisability = "No disabled";
                else strDisability = "Disability access";

                if(!isBidet) strBidet = "No bidet";
                else strBidet = "Bidet";

                if (swTissue.isChecked()) strToiletriesList.add("Tissue");
                if (swSoap.isChecked()) strToiletriesList.add("Soap");
                if (swNapkin.isChecked()) strToiletriesList.add("Napkin");

                if (crName.isEmpty())
                    Toast.makeText(AddRestroomActivity.this, "Please input restroom name", Toast.LENGTH_SHORT).show();
                else if (strLocType.isEmpty())
                    Toast.makeText(AddRestroomActivity.this, "Please select a location type", Toast.LENGTH_SHORT).show();
                else {
                    // TEST LOGS
                    Log.d(ADD_RESTROOM_ACTIVITY, "ADD: crName - " + crName);
                    Log.d(ADD_RESTROOM_ACTIVITY, "ADD: crCategPaid - " + strPaid);
                    Log.d(ADD_RESTROOM_ACTIVITY, "ADD: crCategDisability - " + strDisability);
                    Log.d(ADD_RESTROOM_ACTIVITY, "ADD: crCategBidet - " + strBidet);
                    Log.d(ADD_RESTROOM_ACTIVITY, "ADD: crLocType - " + strLocType);
                    for (String str : strToiletriesList) {
                        Log.d(ADD_RESTROOM_ACTIVITY, "ADD: crToiletries - " + str);
                    }
                    Log.d(ADD_RESTROOM_ACTIVITY, "ADD: latitude - " + MapsActivity.currentLocation.getLongitude());
                    Log.d(ADD_RESTROOM_ACTIVITY, "ADD: longitude - " + MapsActivity.currentLocation.getLongitude());

                    // Restroom collection
                    CollectionReference restroomDB = db.collection("restrooms");
                    // Put data in a Restroom object
                    Map<String, Object> restroomObj = new HashMap<>();
                    restroomObj.put("categ_bidet", strBidet);
                    restroomObj.put("categ_disability", strDisability);
                    restroomObj.put("categ_loc_type", strLocType);
                    restroomObj.put("categ_paid", strPaid);
                    restroomObj.put("categ_toiletries", strToiletriesList);
                    restroomObj.put("latitude", MapsActivity.currentLocation.getLatitude());
                    restroomObj.put("longitude", MapsActivity.currentLocation.getLongitude());
                    restroomObj.put("name", crName);
                    restroomObj.put("reviews", new ArrayList<>());

                    // Add the Restroom data to Firestore db
                    restroomDB.add(restroomObj)
                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(ADD_RESTROOM_ACTIVITY,"Restroom added!");
                                        finish();
                                    }

                                    else
                                        Log.d(ADD_RESTROOM_ACTIVITY, "Restroom adding failed.");
                                }
                            });
                }
            }
        });

    }

    private void initComponents (){
        this.tvRestroomName = findViewById(R.id.add_etRestroomName);
        this.imvPaid = findViewById(R.id.add_imvPaid);
        this.imvDisability = findViewById(R.id.add_imvDisability);
        this.imvBidet = findViewById(R.id.add_imvBidet);
        this.actvLocType = findViewById(R.id.add_actvLocType);
        this.swTissue = findViewById(R.id.add_swTissue);
        this.swSoap = findViewById(R.id.add_swSoap);
        this.swNapkin = findViewById(R.id.add_swNapkin);
        this.btnAddRestroom = findViewById(R.id.btnAddRestroom);
    }


}