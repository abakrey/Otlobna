package com.example.otlobna.Views;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.otlobna.Basics.Validation;
import com.example.otlobna.Model.Object.Order;
import com.example.otlobna.Model.Response.RequestOrderResponse;
import com.example.otlobna.R;
import com.example.otlobna.network.ApiClient;
import com.example.otlobna.network.ApiInterface;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateOrderActivity extends AppCompatActivity {
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
    private boolean EditOrderCheck = true;
    private float latt, lng;

    @Override
    protected void onResume() {
        super.onResume();
        CountDownTimer countDownTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                setOrderIntentInfo();
            }
        };
        setOrderIntentInfo();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_order);

        Order order = (Order) getIntent().getExtras().getSerializable("orderob");
        mLat = getIntent().getExtras().getString("latitude");
        latt = Float.parseFloat(mLat);
        mLon = getIntent().getExtras().getString("longitude");
        lng = Float.parseFloat(mLon);
        PassedIntentOrder = order;
        InitComponent();

        Log.e("try pars", "" + order.getAddress_Cutsomer());

    }

    private void setOrderIntentInfo() {
        customerName.setText(PassedIntentOrder.getName_Customer().trim());
        customerAddress.setText(PassedIntentOrder.getAddress_Cutsomer().trim());
        orderPrice.setText(PassedIntentOrder.getPrice_Place().trim());
        tayarPrice.setText(PassedIntentOrder.getPrice_Tayar().trim());
        customerPhone.setText(PassedIntentOrder.getPhone_Customer().trim());
        details.setText(PassedIntentOrder.getDetails().trim());

    }

    private void InitComponent() {

        requesrOrder_btn = findViewById(R.id.Order);
        progressDialog = new ProgressDialog(UpdateOrderActivity.this);
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
        pDialog = new ProgressDialog(UpdateOrderActivity.this);

        requesrOrder_btn.setText("تعديل الطلب");
        requesrOrder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check ui empity
                updateOrderFunction();
            }
        });
    }

    private void updateOrderFunction() {
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
        if (TextUtils.isEmpty(name) && name.equals(PassedIntentOrder.getName_Customer())) {
            customerName.setError("هذة الخانه متطلبه");
            focusView = customerName;
            cancel = true;
        } else if (TextUtils.isEmpty(address) && address.equals(PassedIntentOrder.getAddress_Cutsomer())) {
            customerAddress.setError("هذة الخانه متطلبه");
            focusView = customerAddress;
            cancel = true;
        } else if (TextUtils.isEmpty(oPrice) && orderPrice.equals(PassedIntentOrder.getPrice_Place())) {
            orderPrice.setError("هذة الخانه متطلبه");
            focusView = orderPrice;
            cancel = true;
        } else if (TextUtils.isEmpty(tPrice) && tayarPrice.equals(PassedIntentOrder.getPrice_Tayar())) {
            tayarPrice.setError("هذة الخانه متطلبه");
            focusView = tayarPrice;
            cancel = true;
        } else if (TextUtils.isEmpty(Details) && details.equals(PassedIntentOrder.getDetails())) {
            details.setError("هذة الخانه متطلبه");
            focusView = details;
            cancel = true;
        } else if (TextUtils.isEmpty(phone) && customerPhone.equals(PassedIntentOrder.getPhone_Customer())) {
            customerPhone.setError("هذة الخانه متطلبه");
            focusView = customerPhone;
            cancel = true;
        } else if (cancel) {
            focusView.requestFocus();
        } else {
            if (Validation.isOnline(UpdateOrderActivity.this)) {
                pDialog.setTitle("جارى تعديل الطلب...");
                pDialog.setMessage("من فضلك انتظر حتى نقوم ب تعديل الطلب الخاص بك");
                pDialog.show();
                pDialog.setCanceledOnTouchOutside(false);
                pDialog.setCancelable(false);

                UpdateOrderById(name, phone, address, oPrice, tPrice, Details, latt, lng);
            } else {
                pDialog.dismiss();
                Toast.makeText(UpdateOrderActivity.this, "من فضلك اتصل بالانترنت", Toast.LENGTH_LONG).show();
            }
        }


    }

    private void UpdateOrderById(final String Naame, final String Phone, final String Address, final String OPrice, final String TPrice, final String Details, final float lat, final float lon) {
        Log.e("update delivery order: ", "run");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.updateOrderById(
                Integer.parseInt(PassedIntentOrder.getId()),
                PassedIntentOrder.getName_Place(),
                PassedIntentOrder.getAddress_Place(),
                PassedIntentOrder.getPhone_Place(),
                Float.parseFloat(PassedIntentOrder.getLongitude_Place()),
                Float.parseFloat(PassedIntentOrder.getLatitude_Place()),
                Float.parseFloat(OPrice),
                Naame,
                Address,
                Phone,
                lon,
                lat,
                Integer.parseInt(PassedIntentOrder.getId_tayar()),
                PassedIntentOrder.getName_Tayar(),
                PassedIntentOrder.getPhone_Tayar(),
                Float.parseFloat(TPrice), Details,
                Float.parseFloat(PassedIntentOrder.getCurrentLon()),
                Float.parseFloat(PassedIntentOrder.getCurrentLat()),
                Integer.parseInt(PassedIntentOrder.getId_mkan()));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        pDialog.dismiss();
                        Log.e("onResponse111111: ", response + "000");

                        String Result = response.body().string();

                        Gson gson = new Gson();
                        RequestOrderResponse generalResponse = gson.fromJson(Result, RequestOrderResponse.class);

                        if (generalResponse.getError() == 0) {
                            Toast.makeText(UpdateOrderActivity.this, "تم تعديل طلبك بنجاح", Toast.LENGTH_LONG).show();
                            finish();


//                                     deliveryOrder.notifyDataSetChanged();
                        } else {

                            Toast.makeText(UpdateOrderActivity.this, "حدث خطا حاول مرة اخرى", Toast.LENGTH_LONG).show();
                        }
                        Log.e("update delivery order", Result + "");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("update delivery order", response.errorBody() + "");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.e("update delivery order", t.getMessage() + "");
                pDialog.dismiss();

                Toast.makeText(UpdateOrderActivity.this, "حدث خطا حاول مرة اخرى لاحقا", Toast.LENGTH_LONG).show();


            }
        });
    }


}
