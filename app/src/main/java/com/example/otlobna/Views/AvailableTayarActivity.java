package com.example.otlobna.Views;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.otlobna.Adapter.AvailableTayarAdapter;
import com.example.otlobna.Adapter.AvailableTayars;
import com.example.otlobna.Model.Object.Tayar;
import com.example.otlobna.Model.Response.AvailbeTayarResponse;
import com.example.otlobna.R;
import com.example.otlobna.network.ApiClient;
import com.example.otlobna.network.ApiInterface;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvailableTayarActivity extends AppCompatActivity {

    RecyclerView availabelTayarRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    AvailableTayars availableTayarAdapter ;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_tayar);

        InitComponent();
        GetAvailableTayar();
    }

    public void InitComponent() {
        availabelTayarRecyclerView = findViewById(R.id.available_tayar_recycler);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        progressDialog = new ProgressDialog(AvailableTayarActivity.this);

        availabelTayarRecyclerView.setLayoutManager(layoutManager);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

    }

    public void GetAvailableTayar() {

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            progressDialog.show();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<ResponseBody> call = apiService.availableTayar();
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {

                        String Result = response.body().string();
                        Log.e("onResponse: ", Result);

                        Gson gson = new Gson();
                        AvailbeTayarResponse availbeTayarResponse = gson.fromJson(Result, AvailbeTayarResponse.class);
                        if(availbeTayarResponse.getError() ==0)
                        {
                            final ArrayList<Tayar> tayars = availbeTayarResponse.getData();
                            availableTayarAdapter = new AvailableTayars( availbeTayarResponse.getData() , AvailableTayarActivity.this);
                            availableTayarAdapter.setOnItemClickListener(new AvailableTayars.OnItemClickListener() {
                                @Override
                                public void onEditClick(int position) {
                                    Intent intent = new Intent(AvailableTayarActivity.this,AddLocationOrder.class);
                                    intent.putExtra("ID" , tayars.get(position).getId());
                                    intent.putExtra("Name" , tayars.get(position).getName());
                                    intent.putExtra("Phone" , tayars.get(position).getPhoneOne());
                                    startActivity(intent);
                                }
                            });
                            availabelTayarRecyclerView.setAdapter(availableTayarAdapter);
                            Log.e("onResponse: ", tayars.size() + "");
                            progressDialog.dismiss();
                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AvailableTayarActivity.this, availbeTayarResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    } catch (IOException e) {
                        progressDialog.dismiss();
                        Toast.makeText(AvailableTayarActivity.this, "حدث خطأ ما حاول فى وقت اخر!", Toast.LENGTH_LONG).show();
                        finish();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("onResponse: ", t.getMessage() + "");
                    Toast.makeText(AvailableTayarActivity.this, "حدث خطأ ما حاول فى وقت اخر!", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        } else {

        }

    }
}
