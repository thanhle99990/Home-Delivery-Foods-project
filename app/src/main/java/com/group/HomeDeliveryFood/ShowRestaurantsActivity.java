package com.group.HomeDeliveryFood;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group.HomeDeliveryFood.Adapter.ShowRestaurantAdapter;
import com.group.HomeDeliveryFood.Cart.CartView;
import com.group.HomeDeliveryFood.Entity.Restaurant;

import java.util.ArrayList;

public class ShowRestaurantsActivity extends AppCompatActivity implements ShowRestaurantAdapter.OnClickItemListener {
    private static final String TAG = "MainActivity";

    //vars
    private ArrayList<Restaurant> restaurants = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_restaurant_activity);

        Toast.makeText(this,""+MainActivity.cart.size(),Toast.LENGTH_LONG).show();
        restaurants=MainActivity.cart;


        initRecyclerView();


    }


    private void initRecyclerView(){


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        ShowRestaurantAdapter adapter = new ShowRestaurantAdapter(this,restaurants,this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(int position) {
        Intent intent=new Intent(this, CartView.class);
        intent.putExtra("position",position);
        startActivity(intent);
    }
}
