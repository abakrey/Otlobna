package com.example.otlobna.Adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.otlobna.Model.Object.Tayar;
import com.example.otlobna.R;
import com.example.otlobna.ViewHolder.AvailableTayarHolder;
import com.example.otlobna.Views.ContentRequestActivity;

import java.util.ArrayList;

public class AvailableTayarAdapter extends RecyclerView.Adapter<AvailableTayarHolder> {

    private Context context;
    private ArrayList<Tayar> tayars;
    private int resource =  R.id.available_tayar_recycler;
    private LayoutInflater layoutInflater;

    public AvailableTayarAdapter(Context context, ArrayList<Tayar> tayars) {
        this.context = context;
        this.tayars = tayars;
   }

    @NonNull
    @Override
    public AvailableTayarHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View row = layoutInflater.inflate(R.layout.row_available_tayar, parent, false);
        AvailableTayarHolder availableTayarHolder = new AvailableTayarHolder(row);

        return availableTayarHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableTayarHolder availableTayarHolder, int position) {
        final Tayar tayar = tayars.get(position);
        availableTayarHolder.category_name.setText(tayar.getDepartment() + "");
        availableTayarHolder.choose_tayar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContentRequestActivity.class);
                intent.putExtra("Name_Tayar", tayar.getName() + "");
                intent.putExtra("Phone_Tayar", tayar.getName() + "");
                intent.putExtra("id_tayar", tayar.getName() + "");
                context.startActivity(intent);
            }
        });
        Log.e("jhgfds", tayar.getName() + "");
        //uri = Uri.parse(tayar.getImage());

    }

    @Override
    public int getItemCount() {
        return tayars.size();
    }
}
