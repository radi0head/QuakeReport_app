package com.example.android.quakereport;

public class EarthquakeItemClass {
    private double mMagnitude;
    private String mLocation;
    private long mDate;

    public EarthquakeItemClass(double Magnitude, String Location, long Date){
        mMagnitude=Magnitude;
        mLocation=Location;
        mDate=Date;
    }

    public double getmMagnitude() {
        return mMagnitude;
    }

    public long getmDate() {
        return mDate;
    }

    public String getmLocation() {
        return mLocation;
    }
}
