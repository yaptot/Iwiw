package com.mobdeve.yapr.iwiw;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Restroom {
    private ArrayList<Double> coordinates;
    private String name;
    private double rating;

    private String categ_disability;
    private String categ_loc_type;
    private String categ_paid;
    private String categ_bidet;
    private ArrayList<String> categ_toiletries;

    public ArrayList<Double> getCoordinates() {
        return coordinates;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public String getCateg_disability() {
        return categ_disability;
    }

    public String getCateg_loc_type() {
        return categ_loc_type;
    }

    public String getCateg_paid() {
        return categ_paid;
    }

    public String getCateg_bidet() {
        return categ_bidet;
    }

    public ArrayList<String> getCateg_toiletries() {
        return categ_toiletries;
    }
}
