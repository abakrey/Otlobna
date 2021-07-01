package com.example.otlobna.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.otlobna.Model.Object.Order;
import com.example.otlobna.R;

import java.util.ArrayList;

public class DeliveryOrder extends RecyclerView.Adapter<DeliveryOrder.DeliveryOrderViewHolder> {

    private Context context;
    private ArrayList<Order> Orders;
    private OnItemClickListener mListener;

    public DeliveryOrder(Context context, ArrayList<Order> orders) {
        this.context = context;
        Orders = orders;
    }

    @NonNull
    @Override
    public DeliveryOrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order_tayar, viewGroup, false);
        DeliveryOrderViewHolder orderViewHolder = new DeliveryOrderViewHolder(view, mListener);
        return orderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryOrderViewHolder deliveryOrderViewHolder, int i) {
        deliveryOrderViewHolder.Details.setText(Orders.get(i).getDetails());
        deliveryOrderViewHolder.Person.setText(Orders.get(i).getName_Customer());
        deliveryOrderViewHolder.Location.setText(Orders.get(i).getAddress_Cutsomer());
        deliveryOrderViewHolder.Type.setText(Orders.get(i).getType());
        deliveryOrderViewHolder.Phone.setText(Orders.get(i).getPhone_Customer());
        deliveryOrderViewHolder.Time.setText(Orders.get(i).getDetails());
        String Type = Orders.get(i).getType();
        if (Type.equals("انهاء")) {
            deliveryOrderViewHolder.DeleteOrder.setVisibility(View.GONE);
            deliveryOrderViewHolder.EditOrder.setVisibility(View.GONE);
            deliveryOrderViewHolder.Time.setText(Orders.get(i).getEnd_Time());
        } else if (Type.equals("استلم")) {
            deliveryOrderViewHolder.DeleteOrder.setVisibility(View.GONE);
            deliveryOrderViewHolder.EditOrder.setVisibility(View.GONE);
            deliveryOrderViewHolder.Time.setText(Orders.get(i).getTake_Time());
        } else if (Type.equals("مطلوب")) {

            deliveryOrderViewHolder.DeleteOrder.setVisibility(View.VISIBLE);
            deliveryOrderViewHolder.EditOrder.setVisibility(View.VISIBLE);
            deliveryOrderViewHolder.Time.setText(Orders.get(i).getStart_Time());
        }

    }

    @Override
    public int getItemCount() {
        return Orders.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onPlaceClick(int position);

        void onEditClick(int position);

        void OnMapClick(int position);

        void OnPhoneclick(int position);

        void OnEditOrderClick(int position);

        void OnDeleteOrderClick(int position);

    }

    public static class DeliveryOrderViewHolder extends RecyclerView.ViewHolder {
        TextView Person, Details, Location, Map, Place, Type, Phone, Time, EditOrder, DeleteOrder;

        DeliveryOrderViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            Person = itemView.findViewById(R.id.CustomerRequest);
            Details = itemView.findViewById(R.id.OrderRequest);
            Type = itemView.findViewById(R.id.TypeRequest);
            Location = itemView.findViewById(R.id.LocationRequest);
            Time = itemView.findViewById(R.id.TimeRequest);
            Phone = itemView.findViewById(R.id.PhoneRequest);
            Map = itemView.findViewById(R.id.MapRequest);
            Place = itemView.findViewById(R.id.MakanRequest);
            EditOrder = itemView.findViewById(R.id.Update_DeliveryRequest);
            DeleteOrder = itemView.findViewById(R.id.Delete_DeliveryRequest);

            Phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnPhoneclick(position);
                        }
                    }
                }
            });
            Map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnMapClick(position);
                        }
                    }
                }
            });
            Place.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onPlaceClick(position);
                        }
                    }
                }
            });

            Type.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onEditClick(position);
                        }
                    }
                }
            });
            EditOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnEditOrderClick(position);
                        }
                    }
                }
            });
            DeleteOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnDeleteOrderClick(position);
                        }
                    }
                }
            });
        }
    }
}
