package com.example.otlobna.Views;

public class DataLocation {
    private float longitude  , latitude ;

    public DataLocation() {

    }

    public DataLocation( float latitude , float longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
}
