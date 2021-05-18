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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

// 1: Define the basic adapter and ViewHolder
class CategAdapter extends RecyclerView.Adapter<CategAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        // views set as each row is rendered
        public LinearLayout ll_CategTab;
        public TextView tvCategory;


        // Constructor
        public ViewHolder(View itemView) {
            super(itemView);

            ll_CategTab = (LinearLayout) itemView.findViewById(R.id.ll_CategTab);
            tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
        }
    }

    // 2: Pass each data through a custom adapter Constructor
    private ArrayList<String> categList;
    private Activity activity;

    public CategAdapter(ArrayList<String> cList, Activity activity) {
        this.categList = cList;
        this.activity = activity;
    }

    // 3: Implement (3) methods for adapter;
    // (1) "onCreateViewHolder - to inflate the item layout and create the holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout -> use the xml file layouted for an Order row
        View categListView = inflater.inflate(R.layout.category, parent, false);
        // Return a new holder instance
        return new ViewHolder(categListView);
    }

    // (2) "onBindViewHolder - to set the view attributes based on the data"
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get data based on position
        String strCateg = categList.get(position);


        switch(position){
            case 0: holder.tvCategory.setTextColor(Color.parseColor("#fd3a4a"));
                holder.ll_CategTab.setBackground(ContextCompat.getDrawable(holder.ll_CategTab.getContext(), R.drawable.shape_categ_paid));
                break;

            case 1: holder.tvCategory.setTextColor(Color.parseColor("#49c6e5"));
                holder.ll_CategTab.setBackground(ContextCompat.getDrawable(holder.ll_CategTab.getContext(), R.drawable.shape_categ_disability));
                break;

            case 2: holder.tvCategory.setTextColor(Color.parseColor("#f2c649"));
                holder.ll_CategTab.setBackground(ContextCompat.getDrawable(holder.ll_CategTab.getContext(), R.drawable.shape_categ_bidet));
                break;

            case 3: holder.tvCategory.setTextColor(Color.parseColor("#66ddaa"));
                holder.ll_CategTab.setBackground(ContextCompat.getDrawable(holder.ll_CategTab.getContext(), R.drawable.shape_categ_loc_type));
                break;

            default: holder.tvCategory.setTextColor(Color.parseColor("#d891ef"));
                holder.ll_CategTab.setBackground(ContextCompat.getDrawable(holder.ll_CategTab.getContext(), R.drawable.shape_categ_toiletries));
                break;
        }


        // Set category based on the Order Row views and data
        holder.tvCategory.setText(strCateg);
    }

    // (3) "getItemCount - to determine the number of items"
    @Override
    public int getItemCount() {
        return categList.size();
    }
}
