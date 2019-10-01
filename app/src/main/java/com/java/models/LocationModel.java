package com.java.models;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class LocationModel {

    @SerializedName("location")
    private String mLocationName;

    @SerializedName("latitude")
    private double mLat;

    @SerializedName("longitude")
    private double mLon;

    public LocationModel(String location, double lat, double lon) {
        this.mLocationName = location;
        this.mLat = lat;
        this.mLon = lon;
    }

    public String getLocationName() {
        return mLocationName;
    }

    public void setLocationName(String mLocationName) {
        this.mLocationName = mLocationName;
    }

    public double getLat() {
        return mLat;
    }

    public void setLat(double mLat) {
        this.mLat = mLat;
    }

    public double getLon() {
        return mLon;
    }

    public void setLon(double mLon) {
        this.mLon = mLon;
    }

    @NonNull
    @Override
    public String toString() {
        return mLocationName;
    }
}
