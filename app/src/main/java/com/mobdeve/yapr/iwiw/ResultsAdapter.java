package com.mobdeve.yapr.iwiw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;

// 1: Define the basic adapter and ViewHolder
class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        // views set as each row is rendered
        public TextView sr_tvAddress;
        public ImageView sr_imvArrow;
        public TextView sr_tvLocDistance;
        public TextView sr_tvRatings;
        public TextView sr_tvRateCount;


        // Constructor
        public ViewHolder(View itemView) {
            super(itemView);

            this.sr_tvAddress = itemView.findViewById(R.id.sr_tvAddress);
            this.sr_imvArrow = itemView.findViewById(R.id.sr_imvArrow);
            this.sr_tvLocDistance = itemView.findViewById(R.id.sr_tvLocDistance);
            this.sr_tvAddress = itemView.findViewById(R.id.sr_tvAddress);
            this.sr_tvRatings = itemView.findViewById(R.id.sr_tvRatings);
            this.sr_tvRateCount = itemView.findViewById(R.id.sr_tvRateCount);
        }
    }

    // 2: Pass each data through a custom adapter Constructor
    private ArrayList<RestroomDist> restrooms;
    private Activity activity;

    public ResultsAdapter(ArrayList<RestroomDist> restrooms, Activity activity) {
        this.restrooms = restrooms;
        this.activity = activity;
    }

    // 3: Implement (3) methods for adapter;
    // (1) "onCreateViewHolder - to inflate the item layout and create the holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout -> use the xml file layouted for an Order row
        View searchResultsView = inflater.inflate(R.layout.result_row, parent, false);
        // Return a new holder instance
        return new ViewHolder(searchResultsView);
    }

    // (2) "onBindViewHolder - to set the view attributes based on the data"
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get data based on position
        RestroomDist restroom = restrooms.get(position);

        holder.sr_tvAddress.setText(restroom.getRestroom().getName());
        holder.sr_tvLocDistance.setText(String.format("%.2f", restroom.getDistance()) + " m");
        holder.sr_tvRatings.setText(String.valueOf(restroom.getRestroom().getRating()));

        holder.sr_imvArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigate to <View Restroom Details activity>
                Intent i = new Intent(v.getContext(), ViewRestRmActivity.class);

                i.putExtra(MapsActivity.ADDRESS_TAG, restroom.getRestroom().getName());
                i.putExtra(MapsActivity.DISTANCE_TAG, String.format("%.2f", restroom.getDistance()));
                i.putExtra(MapsActivity.RATING_TAG, String.valueOf(restroom.getRestroom().getRating()));

                i.putExtra(MapsActivity.CATEG_PAID, restroom.getRestroom().getCateg_paid());
                i.putExtra(MapsActivity.CATEG_DISABILITY, restroom.getRestroom().getCateg_disability());
                i.putExtra(MapsActivity.CATEG_BIDET, restroom.getRestroom().getCateg_bidet());
                i.putExtra(MapsActivity.CATEG_LOCTYPE, restroom.getRestroom().getCateg_loc_type());
                i.putExtra(MapsActivity.CATEG_TOILETRIES, restroom.getRestroom().getCateg_toiletries());
//                i.putExtra(MapsActivity.RATE_COUNT_TAG, );
                // putExtra() for filters

                v.getContext().startActivity(i);
            }
        });
    }

    // (3) "getItemCount - to determine the number of items"
    @Override
    public int getItemCount() {
        return restrooms.size();
    }
}