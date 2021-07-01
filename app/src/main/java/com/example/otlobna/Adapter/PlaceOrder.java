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

public class PlaceOrder extends RecyclerView.Adapter<PlaceOrder.placeOrderViewHolder> {
    private Context context;
    private ArrayList<Order> Orders;
    private OnItemClickListener mListener;
    private String Type;

    public PlaceOrder(Context context, ArrayList<Order> orders, String Type) {
        this.context = context;
        Orders = orders;
        this.Type = Type;
    }

    @NonNull
    @Override
    public placeOrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order_place, viewGroup, false);
        placeOrderViewHolder orderViewHolder = new placeOrderViewHolder(view, mListener);
        return orderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull placeOrderViewHolder placeOrderViewHolder, int i) {
        placeOrderViewHolder.Details.setText(Orders.get(i).getDetails());
        placeOrderViewHolder.Person.setText(Orders.get(i).getName_Customer());
        placeOrderViewHolder.Location.setText(Orders.get(i).getAddress_Cutsomer());
        placeOrderViewHolder.Type.setText(Orders.get(i).getType());
        placeOrderViewHolder.Phone.setText(Orders.get(i).getPhone_Customer());
        Type = Orders.get(i).getType();
        if (Type.equals("انهاء")) {
            placeOrderViewHolder.DeleteOrder.setVisibility(View.GONE);
            placeOrderViewHolder.EditOrder.setVisibility(View.GONE);
            placeOrderViewHolder.Time.setText(Orders.get(i).getEnd_Time());
        } else if (Type.equals("استلم")) {
            placeOrderViewHolder.DeleteOrder.setVisibility(View.GONE);
            placeOrderViewHolder.EditOrder.setVisibility(View.GONE);
            placeOrderViewHolder.Time.setText(Orders.get(i).getTake_Time());
        } else if (Type.equals("مطلوب")) {
            placeOrderViewHolder.DeleteOrder.setVisibility(View.VISIBLE);
            placeOrderViewHolder.EditOrder.setVisibility(View.VISIBLE);
            placeOrderViewHolder.Time.setText(Orders.get(i).getStart_Time());
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
        void onDeliveryClick(int position);

        void onEditClick(int position);

        void OnMapClick(int position);

        void OnPhoneclick(int position);

        void OnEditOrderClick(int position);

        void OnDeleteOrderClick(int position);
    }

    public static class placeOrderViewHolder extends RecyclerView.ViewHolder {
        TextView Person, Details, Location, Map, Delivery, Type, Phone, Time, EditOrder, DeleteOrder;

        public placeOrderViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            Person = itemView.findViewById(R.id.CustomerRequest);
            Details = itemView.findViewById(R.id.OrderRequest);
            Type = itemView.findViewById(R.id.TypeRequest);
            Location = itemView.findViewById(R.id.LocationRequest);
            Time = itemView.findViewById(R.id.TimeRequest);
            Phone = itemView.findViewById(R.id.PhoneRequest);
            Map = itemView.findViewById(R.id.MapRequest);
            Delivery = itemView.findViewById(R.id.DeliveryRequest);
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
            Delivery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeliveryClick(position);
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
