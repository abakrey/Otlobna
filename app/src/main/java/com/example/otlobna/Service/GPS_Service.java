package com.example.otlobna.Service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.otlobna.Views.DataLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;
import static java.security.AccessController.getContext;

public class GPS_Service extends Service {

    private LocationListener listener;
    private LocationManager locationManager;
    private FusedLocationProviderClient client;
    private DataLocation Location;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {

//        RequestPermissions();

        Location = new DataLocation();
        client = LocationServices.getFusedLocationProviderClient(GPS_Service.this);
        GetCurrentLocation();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new mainTask(), 0, 5000);


        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Intent i = new Intent("location_update");

                i.putExtra("Log" , location.getLongitude());
                i.putExtra("Lat" , location.getLatitude());
                sendBroadcast(i);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        //noinspection MissingPermission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, listener);

    }

    private class mainTask extends TimerTask {
        public void run() {
            GetCurrentLocation();
            Log.e(TAG, "run: ");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            //noinspection MissingPermission
            locationManager.removeUpdates(listener);
        }
    }

    private void GetCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(GPS_Service.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(android.location.Location location) {
                if (location != null) {
                    Location.setLatitude((float) location.getLatitude());
                    Location.setLongitude((float) location.getLongitude());

                    Intent i = new Intent("location_update");
                    i.putExtra("Log" , location.getLongitude());
                    i.putExtra("Lat" , location.getLatitude());
                    sendBroadcast(i);
                }
            }
        });
    }


}