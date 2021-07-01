package com.example.otlobna.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.otlobna.R;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class DetailsAdapter extends ExpandableRecyclerViewAdapter<ResturantHolder, DetailsHolder> {
    public DetailsAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public ResturantHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.resturantrec, parent, false);
        return new ResturantHolder(v);
    }

    @Override
    public DetailsHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.details, parent, false);
        return new DetailsHolder(v);
    }

    @Override
    public void onBindChildViewHolder(DetailsHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Details product = (Details) group.getItems().get(childIndex);
        holder.bind(product);
    }

    @Override
    public void onBindGroupViewHolder(ResturantHolder holder, int flatPosition, ExpandableGroup group) {
        final Resturant company = (Resturant) group;
        holder.bind(company);
    }
}
