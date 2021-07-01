package com.example.otlobna.Views;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.otlobna.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TrackingMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    DataLocation CurrentLocation;
    DataLocation CustomerLocation;
    DataLocation PlaceLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        CurrentLocation = new DataLocation();
        CustomerLocation = new DataLocation();
        PlaceLocation = new DataLocation();

        CurrentLocation.setLongitude((Float) getIntent().getExtras().get("CurrentLocationLong"));
        CurrentLocation.setLatitude((Float) getIntent().getExtras().get("CurrentLocationLong"));

        CustomerLocation.setLongitude((Float) getIntent().getExtras().get("CustomerLocationLog"));
        CustomerLocation.setLatitude((Float) getIntent().getExtras().get("CustomerLocationLat"));

        PlaceLocation.setLongitude((Float) getIntent().getExtras().get("PlaceLocationLong"));
        PlaceLocation.setLatitude((Float) getIntent().getExtras().get("PlaceLocationLat"));


        Log.e("Tracking Map: ", CurrentLocation.getLongitude() + " " + CurrentLocation.getLatitude() + "\n" +
                CustomerLocation.getLongitude() + " " + CustomerLocation.getLatitude() + "\n" +
                PlaceLocation.getLongitude() + " " + PlaceLocation.getLatitude() + "\n");

//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new mainTask(), 0, 5000);
    }


//    private class mainTask extends TimerTask {
//        public void run() {
////            mMap.clear();
//            Log.e(TAG, "Tracking map: ");
//        }
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
//        LatLng sydney0 = new LatLng(-34.99, 154.99);
//        LatLng sydney1 = new LatLng(-34.10, 152.10);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.addMarker(new MarkerOptions().position(sydney0).title("Marker in Sydney"));
//        mMap.addMarker(new MarkerOptions().position(sydney1).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        int height = 70;
        int width = 70;

        BitmapDrawable bitmapdrawCurrent = (BitmapDrawable) getResources().getDrawable(R.drawable.delivery_icon);
        Bitmap bCurrent = bitmapdrawCurrent.getBitmap();
        Bitmap smallMarkerCurrent = Bitmap.createScaledBitmap(bCurrent, width, height, false);

        LatLng CurrL = new LatLng(CurrentLocation.getLongitude(), CurrentLocation.getLatitude());

        mMap.addMarker(new MarkerOptions()
                .position(CurrL)
                .title("Current Location")
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarkerCurrent))
        );

        BitmapDrawable bitmapdrawUser = (BitmapDrawable) getResources().getDrawable(R.drawable.order_icon1);
        Bitmap bUser = bitmapdrawUser.getBitmap();
        Bitmap smallMarkerUser = Bitmap.createScaledBitmap(bUser, width, height, false);

        LatLng UserL = new LatLng(CustomerLocation.getLongitude(), CustomerLocation.getLatitude());

        mMap.addMarker(new MarkerOptions()
                .position(UserL)
                .title("Customer Location")
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarkerUser))
        );

        BitmapDrawable bitmapdrawPlace = (BitmapDrawable) getResources().getDrawable(R.drawable.resturant_icon);
        Bitmap bPlace = bitmapdrawPlace.getBitmap();
        Bitmap smallMarkerPlace = Bitmap.createScaledBitmap(bPlace, width, height, false);

        LatLng PlaceL = new LatLng(PlaceLocation.getLongitude(), PlaceLocation.getLatitude());

        mMap.addMarker(new MarkerOptions()
                .position(PlaceL)
                .title("Place Location")
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarkerPlace))
        );

        CameraUpdate zoom = CameraUpdateFactory.zoomTo(12);

//        map.moveCamera(center);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(UserL));
        mMap.animateCamera(zoom);


//        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.resturant_icon);
//        MarkerOptions markerOptions = new MarkerOptions().position(sydney)
//                .title("Marker in Sydney")
//                .snippet("snippet snippet snippet snippet snippet...")
//                .icon(icon);
//        mMap.addMarker(markerOptions);
//
//        BitmapDescriptor icon0= BitmapDescriptorFactory.fromResource(R.drawable.order_icon1);
//        LatLng sydney0 = new LatLng(-34.99, 154.99);
//        MarkerOptions markerOptions0 = new MarkerOptions().position(sydney0)
//                .title("Marker in Sydney")
//                .snippet("snippet snippet snippet snippet snippet...")
//                .icon(icon0);
//        mMap.addMarker(markerOptions0);
//
//        BitmapDescriptor icon1 = BitmapDescriptorFactory.fromResource(R.drawable.tyar_icpn);
//        LatLng sydney1 = new LatLng(-34.10, 152.10);
//        MarkerOptions markerOptions1 = new MarkerOptions().position(sydney1)
//                .title("Marker in Sydney")
//                .snippet("snippet snippet snippet snippet snippet...")
//                .icon(icon1);
//        mMap.addMarker(markerOptions1);


    }
}
