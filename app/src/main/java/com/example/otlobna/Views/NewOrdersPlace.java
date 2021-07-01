package com.example.otlobna.Views;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otlobna.Adapter.PlaceOrder;
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
public class NewOrdersPlace extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Order> Orders;
    private View view;
    private PlaceOrder placeOrder;
    private Button addOrder;

    public NewOrdersPlace() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Validation.isOnline(getActivity())) {
            SetNewOrdersInRecyclerView();
        } else {
            Toast.makeText(getActivity(), "من فضلك اتصل بالانترنت", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_orders_place, container, false);
        recyclerView = view.findViewById(R.id.recyclerviewContent);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        Orders = new ArrayList<>();

        addOrder = view.findViewById(R.id.addOrder);

        addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AvailableTayarActivity.class);
                startActivity(intent);
            }
        });

        recyclerView.setHasFixedSize(true);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy >= 5) {
                    addOrder.setVisibility(View.GONE);
                } else if (dy <= -5) {
                    addOrder.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
    }

    private void SetNewOrdersInRecyclerView() {
        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        int Id = Integer.parseInt(Preferance.getID(getActivity()));
        Call<ResponseBody> callNew = service.NewOrderPlace(Id);
        callNew.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String Respone = response.body().string();
                    Gson gson = new Gson();
                    GeneralResponse generalResponse = gson.fromJson(Respone, GeneralResponse.class);

                    if (generalResponse.getError() == 0) {
                        OrderResponse orderResponse = gson.fromJson(Respone, OrderResponse.class);
                        Orders = orderResponse.getData();
                        placeOrder = new PlaceOrder(getActivity(), Orders, "استلم");
                        recyclerView.setAdapter(placeOrder);
                        placeOrder.notifyDataSetChanged();
                        placeOrder.setOnItemClickListener(new PlaceOrder.OnItemClickListener() {
                            @Override
                            public void onDeliveryClick(int position) {
                                ShowOfDelivery(position);
                            }

                            @Override
                            public void onEditClick(int position) {
                                // No Any Editing in the Side Place ;))
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
                                Order order = Orders.get(position);
                                Intent intent = new Intent(getActivity(), UpdateLocationMap.class);
                                intent.putExtra("orderob", order);
                                startActivity(intent);
                            }

                            @Override
                            public void OnDeleteOrderClick(int position) {
                                DeleteOrderById(Integer.parseInt(Orders.get(position).getId()), Integer.parseInt(Orders.get(position).getId_tayar()), position);
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "لا يوجد طلبات جديدة", Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void ShowOfDelivery(int position) {
        final TextView Person, Location, Phone;
        final Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.item_person_details);
        dialog.setTitle("معلومات عن الطيار");

        Person = dialog.findViewById(R.id.Person);
        Location = dialog.findViewById(R.id.Location);
        Phone = dialog.findViewById(R.id.Phone);

        Person.setText(Orders.get(position).getName_Tayar());
        Phone.setText(Orders.get(position).getPhone_Tayar());

        Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallingPhone(Phone.getText().toString().trim());
            }
        });

        Location.setVisibility(View.INVISIBLE);
        dialog.show();
    }

    private void CallingPhone(String phone) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phone));

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }

    private void DeleteOrderById(int id_order, int id_tayar, final int position) {
        Log.e("delete place order: ", "run");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.deleteOrderById(id_order, id_tayar);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String Result = response.body().string();
                    if (Result.equals("{\"error\":0,\"message\":\"Data is Deleted successfully and this tayar is on now\"}")) {
                        Orders.remove(position);
                        placeOrder.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), "حدث خطا حاول مرة اخرى", Toast.LENGTH_LONG).show();
                    }
                    Log.e("delete place order", Result + "");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("delete place order", t.getMessage() + "");
            }
        });
    }


}
