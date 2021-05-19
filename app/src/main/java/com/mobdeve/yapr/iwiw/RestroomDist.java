package com.mobdeve.yapr.iwiw;

public class RestroomDist {
    float distance;
    Restroom restroom;

    public RestroomDist(float distance, Restroom restroom) {
        this.distance = distance;
        this.restroom = restroom;
    }

    public float getDistance() {
        return distance;
    }

    public Restroom getRestroom() {
        return restroom;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public void setRestroom(Restroom restroom) {
        this.restroom = restroom;
    }
}
