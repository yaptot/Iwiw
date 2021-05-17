package com.mobdeve.yapr.iwiw;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Restroom {
    private ArrayList<Double> coordinates;
    private ArrayList<String> filters;
    private String name;
    private boolean paid;
    private int rating;

    public ArrayList<Double> getCoordinates() {
        return coordinates;
    }

    public ArrayList<String> getFilters() {
        return filters;
    }

    public String getName() {
        return name;
    }

    public boolean isPaid() {
        return paid;
    }

    public int getRating() {
        return rating;
    }
}
