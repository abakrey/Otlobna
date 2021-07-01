package com.example.otlobna.Views;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otlobna.Adapter.DeliveryOrder;
import com.example.otlobna.Basics.Validation;
import com.example.otlobna.Model.Object.Order;
import com.example.otlobna.Model.PreferanceData.Preferance;
import com.example.otlobna.Model.Response.GeneralResponse;
import com.example.otlobna.Model.Response.OrderResponse;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class OldOrdersDelivery extends Fragment {

    private RecyclerView recyclerView ;
    private ArrayList<Order> Orders ;
    private View view ;
    private DeliveryOrder deliveryOrder ;

    public OldOrdersDelivery() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_old_orders_delivery, container, false);
        recyclerView = view.findViewById(R.id.recyclerviewContent);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        Orders = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        if (Validation.isOnline(getActivity()))
        {
            SetEndOrdersInRecyclerView();
        }
        else
        {
            Toast.makeText(getActivity(),"من فضلك اتصل بالانترنت", Toast.LENGTH_LONG).show();
        }
        return view ;
    }

    private void SetEndOrdersInRecyclerView()
    {
        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        int ID = Integer.parseInt(Preferance.getID(getActivity()));
        Call<ResponseBody> call = service.EndOrderDelivery(ID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String Respone = response.body().string();
                    Gson gson = new Gson();
                    GeneralResponse generalResponse = gson.fromJson(Respone , GeneralResponse.class);

                    if (generalResponse.getError() == 0)
                    {
                        OrderResponse orderResponse = gson.fromJson(Respone , OrderResponse.class);
                        Orders = orderResponse.getData();
                        deliveryOrder = new DeliveryOrder(getActivity() , Orders );
                        recyclerView.setAdapter(deliveryOrder);
                        deliveryOrder.setOnItemClickListener(new DeliveryOrder.OnItemClickListener() {
                            @Override
                            public void onPlaceClick(int position) {
                                ShowOfPlace(position);
                            }

                            @Override
                            public void onEditClick(int position) {
                               // No Editing in the end Order .
                            }

                            @Override
                            public void OnMapClick(int position) {
                                DataLocation CurrentLocation = new DataLocation();
                                DataLocation CustomerLocation = new DataLocation();
                                DataLocation PlaceLocation = new DataLocation();

                                CurrentLocation.setLatitude(Float.parseFloat(Orders.get(position).getCurrentLat()));
                                CurrentLocation.setLongitude(Float.parseFloat(Orders.get(position).getCurrentLon()));

                                CustomerLocation.setLongitude(Float.parseFloat(Orders.get(position).getLongitude_Customer()));
                                CustomerLocation.setLatitude(Float.parseFloat(Orders.get(position).getLatitude_Customer()));

                                PlaceLocation.setLatitude(Float.parseFloat(Orders.get(position).getLatitude_Place()));
                                PlaceLocation.setLongitude(Float.parseFloat(Orders.get(position).getLongitude_Place()));


                                Intent intent = new Intent(getActivity(), TrackingMap.class);

                                intent.putExtra("CurrentLocationLong", CurrentLocation.getLongitude());
                                intent.putExtra("CurrentLocationLat", CurrentLocation.getLatitude());
                                intent.putExtra("CustomerLocationLog", CustomerLocation.getLongitude());
                                intent.putExtra("CustomerLocationLat", CustomerLocation.getLongitude());
                                intent.putExtra("PlaceLocationLong", PlaceLocation.getLongitude());
                                intent.putExtra("PlaceLocationLat", PlaceLocation.getLongitude());


                                Log.e("OnMapClick: ", CurrentLocation.getLongitude() + " " + CurrentLocation.getLatitude() + "\n" +
                                        CustomerLocation.getLongitude() + " " + CustomerLocation.getLatitude() + "\n" +
                                        PlaceLocation.getLongitude() + " " + PlaceLocation.getLatitude() + "\n");
                                startActivity(intent);



                            }

                            @Override
                            public void OnPhoneclick(int position) {
                                CallingPhone(Orders.get(position).getPhone_Customer());
                            }

                            @Override
                            public void OnEditOrderClick(int position) {

                            }

                            @Override
                            public void OnDeleteOrderClick(int position) {

                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(getActivity() , "لا يوجد طلبات سابقه" ,Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    Toast.makeText(getActivity() , e.getMessage() ,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity() , t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ShowOfPlace(int position)
    {
        final TextView Person , Location , Phone ;
        final Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.item_person_details);
        dialog.setTitle("معلومات عن المكان");

        Person = dialog.findViewById(R.id.Person);
        Location = dialog.findViewById(R.id.Location);
        Phone = dialog.findViewById(R.id.Phone);

        Person.setText(Orders.get(position).getName_Place());
        Phone.setText(Orders.get(position).getPhone_Place());
        Location.setText(Orders.get(position).getAddress_Place());

        Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallingPhone(Phone.getText().toString().trim());
            }
        });

        dialog.show();
    }

    private void CallingPhone(String phone)
    {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+phone));

        if (ActivityCompat.checkSelfPermission(getActivity() , Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        startActivity(callIntent);
    }

}
