package com.example.otlobna.Views;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.otlobna.Model.PreferanceData.Preferance;
import com.example.otlobna.Model.Response.LoginResponse;
import com.example.otlobna.R;
import com.example.otlobna.network.ApiClient;
import com.example.otlobna.network.ApiInterface;
import com.example.otlobna.Views.DataLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.Locale;

 import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    TextView txt_name, txt_location_info;
    TextView edt_email, edt_pasword, edt_name, edt_phone;
    ProgressDialog generalProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        inistialization();
        SetData();
    }

    private void inistialization() {

        txt_name = findViewById(R.id.txt_name);
        txt_location_info = findViewById(R.id.txt_location_info);

        edt_email = findViewById(R.id.edt_email);
        edt_pasword = findViewById(R.id.edt_password);
        edt_name = findViewById(R.id.edt_name);
        edt_phone = findViewById(R.id.edt_phone);


    }

    private void SetData() {
        txt_name.setText(Preferance.getName(ProfileActivity.this));
        txt_location_info.setText(Preferance.getAddress(ProfileActivity.this));
        edt_email.setText(Preferance.getEmail(ProfileActivity.this));
        edt_pasword.setText(Preferance.getPassword(ProfileActivity.this));
        edt_name.setText(Preferance.getName(ProfileActivity.this));
        edt_phone.setText(Preferance.getPhoneOne(ProfileActivity.this));

        if (Preferance.getImage(ProfileActivity.this).equals("")) {

        }
    }

    private void UpdatingProfile() {

    }


   /* public void updateProfile(View view) {
        Log.e("updateProfile", "updateProfile: ");

        String name = edt_name.getText().toString();
        String email = edt_email.getText().toString();
        String password = edt_pasword.getText().toString();
        String phone = edt_phone.getText().toString();
        String ssd = edt_ssd.getText().toString();
        String address = edt_address.getText().toString();

        Log.e("updateProfile: ", name + " " + email + " " + password + " " + phone + " " + ssd + " " + address);


//        final ProgressDialog progressDialog;


        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            generalProgressDialog = new ProgressDialog(ProfileActivity.this);
            generalProgressDialog.setIndeterminate(true);
            generalProgressDialog.setMessage("Updating...");
            generalProgressDialog.setCancelable(false);
            generalProgressDialog.setCanceledOnTouchOutside(false);
            generalProgressDialog.show();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

//            Call<ResponseBody> call = apiService.updateTayarProfile(tayarId, name, email, password, null, null,
//                    null, "wait API", address, phone, null);

            Call<ResponseBody> call = apiService.updateTayarProfile(1, name, email, password,
                    "مطعم", "1.666", "1.333", "img.png", address, phone, "00000000000");

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    try {

                    if (response.isSuccessful()) {
//                        progressDialog.dismiss();
                        Log.e("onResponse: if ", response.message());
                        Toast.makeText(ProfileActivity.this, "updated successful", Toast.LENGTH_SHORT).show();
                        getData("1");
                    } else {
                        generalProgressDialog.dismiss();
                        Log.e("onResponse: else", response.isSuccessful() + "--");
                        Toast.makeText(ProfileActivity.this, response.isSuccessful() + "--", Toast.LENGTH_SHORT).show();

                    }
//                        String Result = response.body().string();
//                        Gson gson = new Gson();
//                        LoginResponse loginResponse = gson.fromJson(Result, LoginResponse.class);
//
//                        setData(loginResponse);
//                        new android.os.Handler().postDelayed(
//                                new Runnable() {
//                                    public void run() {
//                                        progressDialog.dismiss();
//                                    }
//                                }, 1000);
//
//                    } catch (IOException e) {
//                        progressDialog.dismiss();
//
//                        e.printStackTrace();
//                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("onFailure: ", t.getMessage());
                    generalProgressDialog.dismiss();
                    Toast.makeText(ProfileActivity.this, t.getMessage() + "", Toast.LENGTH_LONG).show();

                }
            });

        } else {
            Snackbar.make(txt_name, R.string.noInternet, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }*/


//    private void RequestPermissions() {
//        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//    }
}
