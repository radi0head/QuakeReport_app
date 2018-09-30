package com.example.android.quakereport;

public class EarthquakeItemClass {
    private double mMagnitude;
    private String mLocation;
    private long mDate;
    private String mUrl;

    public EarthquakeItemClass(double Magnitude, String Location, long Date,String url){
        mMagnitude=Magnitude;
        mLocation=Location;
        mDate=Date;
        mUrl=url;
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

    public String getmUrl() {
        return mUrl;
    }
}
