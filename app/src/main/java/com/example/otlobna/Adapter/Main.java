package com.example.otlobna.Adapter;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.otlobna.R;
import com.example.otlobna.Views.MapsActivity;

import java.util.ArrayList;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Resturant> companies = new ArrayList<>();

        ArrayList<Details> googleProduct = new ArrayList<>();
        googleProduct.add(new Details("A class Cafe & Restaurant"));
        googleProduct.add(new Details("Beni-Seuf"));


        Resturant google = new Resturant("A Class ", googleProduct);

        companies.add(google);

        ArrayList<Details> microsoftProduct = new ArrayList<>();
        microsoftProduct.add(new Details("Lamera"));
        microsoftProduct.add(new Details("Lamera Cafe & Restaurant"));


        Resturant microsoft = new Resturant("Lamera", microsoftProduct);
        companies.add(microsoft);

        DetailsAdapter adapter = new DetailsAdapter(companies);
        recyclerView.setAdapter(adapter);

    }
   public void mapFunc(View view){
        Intent intent =new Intent(this, MapsActivity.class);
        startActivity(intent);
   }

}
