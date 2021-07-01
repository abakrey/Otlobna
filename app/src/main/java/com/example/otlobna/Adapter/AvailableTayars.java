package com.example.otlobna.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.otlobna.Model.Object.Tayar;
import com.example.otlobna.R;

import java.util.ArrayList;

public class AvailableTayars  extends RecyclerView.Adapter<AvailableTayars.AvailableViewHolder> {
    private OnItemClickListener mListener ;
    private ArrayList<Tayar>tayars ;
    private Context context ;

    public AvailableTayars(ArrayList<Tayar> tayars, Context context) {
        this.tayars  =tayars;
        this.context = context;
    }

    @NonNull
    @Override
    public AvailableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_available_tayar ,viewGroup , false);
        AvailableViewHolder orderViewHolder= new AvailableViewHolder(view , mListener);
        return orderViewHolder ;
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableViewHolder availableViewHolder, int i) {
        availableViewHolder.category_name.setText(tayars.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return tayars.size();
    }

    public static class AvailableViewHolder extends RecyclerView.ViewHolder {
        public TextView category_name;
        public Button choose_tayar_btn;

        public AvailableViewHolder(@NonNull View itemView , final OnItemClickListener listener) {
            super(itemView);
            choose_tayar_btn = itemView.findViewById(R.id.row_available_tayar_btn);
            category_name = itemView.findViewById(R.id.row_available_tayar_name);
            choose_tayar_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            listener.onEditClick(position );
                        }
                    }
                }
            });
        }
    }

    public interface OnItemClickListener
    {
        void onEditClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener ;
    }

}
