package com.example.otlobna.Views;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.otlobna.Basics.Validation;
import com.example.otlobna.Model.Object.Order;
import com.example.otlobna.Model.PreferanceData.Preferance;
import com.example.otlobna.Model.Response.RequestOrderResponse;
import com.example.otlobna.R;
import com.example.otlobna.network.ApiClient;
import com.example.otlobna.network.ApiInterface;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContentRequestActivity extends AppCompatActivity {

    Button requesrOrder_btn;
    ProgressDialog progressDialog;
    ConnectivityManager connectivityManager;
    private String id_tayar;
    private String name_tayar;
    private String phone_tayar;
    private String mLat;
    private String mLon;
    private FusedLocationProviderClient client;
    private EditText customerName, customerAddress, orderPrice, tayarPrice, details, customerPhone;
    private ProgressDialog pDialog;
    private DataLocation Location;
    private Order PassedIntentOrder;
    private float latt, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_request);
            GetIntentInfo();
            InitComponent();
        Location = new DataLocation();
        client = LocationServices.getFusedLocationProviderClient(ContentRequestActivity.this);
        pDialog = new ProgressDialog(ContentRequestActivity.this);

        RequestPermissions();
        GetCurrentLocation();
    }


    private void GetIntentInfo() {

        id_tayar = getIntent().getExtras().getString("ID");
        name_tayar = getIntent().getExtras().getString("Name");
        phone_tayar = getIntent().getExtras().getString("Phone");
        mLat = getIntent().getExtras().getString("latitude");
        latt = Float.parseFloat(mLat);
        mLon = getIntent().getExtras().getString("longitude");
        lng = Float.parseFloat(mLon);

        Toast.makeText(this, mLat + " : " + mLon, Toast.LENGTH_LONG).show();


    }

    private void InitComponent() {

        requesrOrder_btn = findViewById(R.id.Order);
        progressDialog = new ProgressDialog(ContentRequestActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        customerName = findViewById(R.id.CustomerName);
        customerAddress = findViewById(R.id.CustomerAddress);
        orderPrice = findViewById(R.id.orPrice);
        tayarPrice = findViewById(R.id.tyPrice);
        details = findViewById(R.id.details);
        customerPhone = findViewById(R.id.CustPhone);
        requesrOrder_btn = findViewById(R.id.Order);

        requesrOrder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderFunction();
            }
        });

    }

    private void orderFunction() {
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Creating Order...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);

        String name = customerName.getText().toString().trim();
        String address = customerAddress.getText().toString().trim();
        String oPrice = orderPrice.getText().toString().trim();
        String tPrice = tayarPrice.getText().toString().trim();
        String Details = details.getText().toString().trim();
        String phone = customerPhone.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        customerName.setError(null);
        customerAddress.setError(null);
        customerPhone.setError(null);
        details.setError(null);
        orderPrice.setError(null);
        tayarPrice.setError(null);
        if (TextUtils.isEmpty(name)) {
            customerName.setError("هذة الخانه متطلبه");
            focusView = customerName;
            cancel = true;
        } else if (TextUtils.isEmpty(address)) {
            customerAddress.setError("هذة الخانه متطلبه");
            focusView = customerAddress;
            cancel = true;
        } else if (TextUtils.isEmpty(oPrice)) {
            orderPrice.setError("هذة الخانه متطلبه");
            focusView = orderPrice;
            cancel = true;
        } else if (TextUtils.isEmpty(tPrice)) {
            tayarPrice.setError("هذة الخانه متطلبه");
            focusView = tayarPrice;
            cancel = true;
        } else if (TextUtils.isEmpty(Details)) {
            details.setError("هذة الخانه متطلبه");
            focusView = details;
            cancel = true;
        } else if (TextUtils.isEmpty(phone)) {
            customerPhone.setError("هذة الخانه متطلبه");
            focusView = customerPhone;
            cancel = true;
        } else if (cancel) {
            focusView.requestFocus();
        } else {
            if (Validation.isOnline(ContentRequestActivity.this)) {
                pDialog.setTitle("جارى انشاء طلب جديد ...");
                pDialog.setMessage("من فضلك انتظر حتى نقوم ب انشاء الطلب الخاص بك");
                pDialog.show();
                pDialog.setCanceledOnTouchOutside(false);
                pDialog.setCancelable(false);

                SetUpRequestOrder(name, phone, address, oPrice, tPrice, Details, latt, lng);
            } else {
                Toast.makeText(ContentRequestActivity.this, "من فضلك اتصل بالانترنت", Toast.LENGTH_LONG).show();
            }
        }


    }

    private void EndSignUpFunction(String message) {
        customerName.setText("");
        customerAddress.setText("");
        orderPrice.setText("");
        tayarPrice.setText("");
        customerPhone.setText("");
        details.setText("");
        pDialog.dismiss();
        Toast.makeText(ContentRequestActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private void SetUpRequestOrder(final String Naame, final String Phone, final String Address, final String OPrice, final String TPrice, final String Details, final float lat, final float lng) {

        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            progressDialog.show();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<ResponseBody> call = apiService.requestOrder(
                    Preferance.getName(ContentRequestActivity.this),
                    Preferance.getAddress(ContentRequestActivity.this),
                    Preferance.getPhoneOne(ContentRequestActivity.this),
                    Float.parseFloat(Preferance.getLONGITUDE(ContentRequestActivity.this)),
                    Float.parseFloat(Preferance.getLatitiude(ContentRequestActivity.this)),
                    OPrice, Naame, Address, Phone, lng, lat, Integer.parseInt(id_tayar)
                    , name_tayar, phone_tayar, TPrice, Details, Location.getLongitude(), Location.getLatitude(),
                    Integer.parseInt(Preferance.getID(ContentRequestActivity.this)));

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.isSuccessful()) {
                        try {

                            String Result = response.body().string();
                            Gson gson = new Gson();
                            RequestOrderResponse requestOrderResponse = gson.fromJson(Result, RequestOrderResponse.class);
                            if (requestOrderResponse.getError() == 0) {
                                EndSignUpFunction("تم ارسال الطلب بنجاح");
                            } else {
                                EndSignUpFunction(requestOrderResponse.getMessage());
                            }
                        } catch (IOException e) {
                            progressDialog.dismiss();
                            Toast.makeText(ContentRequestActivity.this, "حدث خطأ ما حاول فى وقت اخر!", Toast.LENGTH_LONG).show();
                            finish();
                            e.printStackTrace();
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(ContentRequestActivity.this, t.getMessage() + "", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });

        } else {
            Toast.makeText(ContentRequestActivity.this, "قم بفتح الانترنت ثم حاول مرة اخرى!", Toast.LENGTH_LONG).show();
        }
    }

    private void GetCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(ContentRequestActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<android.location.Location>() {
            @Override
            public void onSuccess(android.location.Location location) {
                if (location != null) {
                    Location.setLatitude((float) location.getLatitude());
                    Location.setLongitude((float) location.getLongitude());
                }
            }
        });
    }

    private void RequestPermissions() {
        ActivityCompat.requestPermissions(ContentRequestActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }




}
