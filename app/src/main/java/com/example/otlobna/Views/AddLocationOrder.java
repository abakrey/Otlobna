package com.example.otlobna.Views;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otlobna.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddLocationOrder extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText mSearch;
    private String id_tayar, name_tayar, phone_tayar, la, lo;
    private Button gotoActivity;
    private float lat, lon;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location_order);

        mSearch = findViewById(R.id.input_search);
        gotoActivity = findViewById(R.id.go);
        imageView = findViewById(R.id.im_magnify);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e( "onClick: ","search" );
                if (mSearch.getText().toString().trim().equals("")) {
                    Toast.makeText(AddLocationOrder.this, "من فضلك ادخل العنوان", Toast.LENGTH_LONG).show();
                } else {
                    geolocation();

                }
            }
        });

        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              if (s.toString().trim().isEmpty()){
                  imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_magnify));
              }else {
                  imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_red));
              }
            }
        });

        Log.e( "on create: ","search" );


        GetInfo();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        init();
    }


    private void GetInfo() {
        id_tayar = getIntent().getExtras().getString("ID");
        name_tayar = getIntent().getExtras().getString("Name");
        phone_tayar = getIntent().getExtras().getString("Phone");

    }

    private void init() {
        mSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event.getAction() == event.ACTION_DOWN ||
                        event.getAction() == event.KEYCODE_ENTER) {

                    geolocation();
                }

                return false;
            }
        });



        gotoActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(la!=null &&lo!=null){
                    Intent intent = new Intent(AddLocationOrder.this, ContentRequestActivity.class);
                    intent.putExtra("ID", id_tayar);
                    intent.putExtra("Name", name_tayar);
                    intent.putExtra("Phone", phone_tayar);
                    intent.putExtra("latitude", la);
                    intent.putExtra("longitude", lo);
                    finish();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(AddLocationOrder.this,"من فضلك ادخل العنوان",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void geolocation() {

        String searchString = mSearch.getText().toString();
        Geocoder geocoder = new Geocoder(AddLocationOrder.this);
        List<Address> list = new ArrayList<>();
        try {

            list = geocoder.getFromLocationName(searchString, 1);

        } catch (IOException e) {
            Log.e("geocoder", "IOException" + e.getMessage());
        }

        if (list.size() > 0) {
            Address address = list.get(0);
            lat = (float) address.getLatitude();
            la = String.valueOf(lat);
            lon = (float) address.getLongitude();
            lo = String.valueOf(lon);
            Toast.makeText(AddLocationOrder.this, la + " : " + lo, Toast.LENGTH_LONG).show();
            // Add a marker in Sydney and move the camera
            LatLng latLng = new LatLng(lat, lon);
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            moveToCurrentLocation(latLng);
            Log.e("address :", address.toString());

        }

    }

    private void moveToCurrentLocation(LatLng currentLocation) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 18));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(20), 2000, null);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
