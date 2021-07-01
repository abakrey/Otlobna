package com.example.otlobna.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.otlobna.Interface.ItemClickListener;
import com.example.otlobna.R;


public class AvailableTayarHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView category_name;
    public ImageView tayar_img;
    public Button choose_tayar_btn;
    private ItemClickListener itemClickListener;


    public AvailableTayarHolder(@NonNull View tayar_itemView) {
        super(tayar_itemView);
        choose_tayar_btn = tayar_itemView.findViewById(R.id.row_available_tayar_btn);
        category_name = tayar_itemView.findViewById(R.id.row_available_tayar_name);
        tayar_img = tayar_itemView.findViewById(R.id.row_available_tayar_img);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

}
