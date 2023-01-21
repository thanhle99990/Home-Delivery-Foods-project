package com.group.HomeDeliveryFood.Cart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.group.HomeDeliveryFood.CheckoutActivity;
import com.group.HomeDeliveryFood.Entity.Food;
import com.group.HomeDeliveryFood.HomePageActivity;
import com.group.HomeDeliveryFood.MainActivity;
import com.group.HomeDeliveryFood.R;

import java.util.Iterator;
import java.util.Set;

public class CartView extends AppCompatActivity {
    private int position;
    //ArrayList<Food> foods;
    //CardViewAdapter adapter;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_cart_view);

        //foods=new ArrayList<>();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(MainActivity.cart.size()!=0){
            Intent intent=getIntent();
            position=intent.getIntExtra("position",0);
            FoodCart cart = MainActivity.cart.get(position).foodCart;

            LinearLayout cartLayout = findViewById(R.id.cart);

            TextView text=findViewById(R.id.text_viewCart);
            text.setText("Menu: "+MainActivity.cart.get(position).getName());

            TextView textTotal=findViewById(R.id.text_total);
            textTotal.setText("Total:"+cart.getValue());

            Log.e("size",""+cart.getSize());

            Set<Food> products=cart.getProducts();

            Iterator iterator = products.iterator();
            while(iterator.hasNext()){
                Food product =(Food) iterator.next();
                product.setUnitPrice((long) cart.getQuantity(product));
                //foods.add(product);
                LinearLayout linearLayout=new LinearLayout(this);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                TextView name=new TextView(this);
                TextView quantity=new TextView(this);
                TextView unitPrice = new TextView(this);
                TextView totalPrice = new TextView(this);

                name.setText(product.getName());
         //       quantity.setText(Integer.toString(cart.getQuantity(product)));
                unitPrice.setText(Long.toString(product.getUnitPrice()));
                totalPrice.setText(Long.toString(cart.getValue()));

                name.setTextSize(40);
                quantity.setTextSize(40);
                unitPrice.setTextSize(40);
                totalPrice.setTextSize(40);

                linearLayout.addView(name);
                linearLayout.addView(quantity);
                linearLayout.addView(unitPrice);
                linearLayout.addView(totalPrice);

                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        200, Gravity.CENTER);
                layoutParams.setMargins(20,50,20,50);
                linearLayout.setLayoutParams(layoutParams);

                name.setLayoutParams(new TableLayout.LayoutParams(0,
                        ActionBar.LayoutParams.WRAP_CONTENT,1));
                quantity.setLayoutParams(new TableLayout.LayoutParams(0,
                        ActionBar.LayoutParams.WRAP_CONTENT,1));
                unitPrice.setLayoutParams(new TableLayout.LayoutParams(0,
                        ActionBar.LayoutParams.WRAP_CONTENT,1));
                totalPrice.setLayoutParams(new TableLayout.LayoutParams(0,
                        ActionBar.LayoutParams.WRAP_CONTENT,1));

                name.setGravity(Gravity.CENTER);
                quantity.setGravity(Gravity.CENTER);
                unitPrice.setGravity(Gravity.CENTER);
                totalPrice.setGravity(Gravity.CENTER);

                cartLayout.addView(linearLayout);
            }

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cart_item_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.clear_items:
                Toast.makeText(this,"Clear items",Toast.LENGTH_SHORT).show();
                MainActivity.cart.remove(position);
                Intent intent = new Intent(this, HomePageActivity.class);
                startActivity(intent);
                return true;
            case R.id.pay_items:
                Toast.makeText(this,"Paid items",Toast.LENGTH_SHORT).show();
                intent = new Intent(this, CheckoutActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}