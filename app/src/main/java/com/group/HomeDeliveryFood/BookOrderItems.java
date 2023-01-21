package com.group.HomeDeliveryFood;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.group.HomeDeliveryFood.Entity.ItemOrders;
import com.group.HomeDeliveryFood.Entity.Order;

import java.util.ArrayList;
import java.util.Date;

public class BookOrderItems extends AppCompatActivity {
    Button acceptBtn, declineBtn;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private CollectionReference orderRef = db.collection("Order");
    private CollectionReference ordersRef = db.collection("Orders");

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.book_order_items_layout);

        acceptBtn = findViewById(R.id.accept);
        declineBtn = findViewById(R.id.decline);

        Intent intent = getIntent();
        // Order order= (Order) intent.getExtras().getParcelable("order");
        final String orderId = intent.getStringExtra("orderId");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final LinearLayout cartLayout = findViewById(R.id.cart);
        orderRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                final ArrayList<ItemOrders> itemOrders = new ArrayList<>();
                for(DocumentSnapshot documentSnapshot : value){
                    String order_foodId = documentSnapshot.getString("foodId");
                    String order_foodName = documentSnapshot.getString("foodName");
                    Long order_foodQuantity = documentSnapshot.getLong("quantity");
                    Long order_foodTotalAmount = documentSnapshot.getLong("totalAmount");
                    String order_orderId = documentSnapshot.getString("orderId");
                    //    Toast.makeText(getApplicationContext(), order_foodId + " " + order_foodQuantity , Toast.LENGTH_LONG).show();
                    itemOrders.add(new ItemOrders(order_foodId,order_foodName,order_orderId,order_foodQuantity,order_foodTotalAmount));

                }
                if(itemOrders.size() == 0){
                    Toast.makeText(getApplicationContext(), "no order item", Toast.LENGTH_LONG).show();
                }
                else {
                  //  Toast.makeText(getApplicationContext(), "have item", Toast.LENGTH_LONG).show();

                    ordersRef.document(mAuth.getCurrentUser().getUid()).collection("Orders").addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            final ArrayList<Order> orders = new ArrayList<>();
                            final ArrayList<ItemOrders> newItemOrders = new ArrayList<>();
                            for (final DocumentSnapshot documentSnapshot : value) {
                                final String order_address = documentSnapshot.getString("address");
                                final String order_customerId = documentSnapshot.getString("customerId");
                                Timestamp timestamp = (Timestamp) documentSnapshot.getData().get("orderDate");
                                final Date order_date = timestamp.toDate();
                                final Long order_billAmount = documentSnapshot.getLong("billAmount");
                                String order_status = documentSnapshot.getString("status");
                                disableButton(order_status);
                                String order_id = documentSnapshot.getId();
                                String order_customerName = documentSnapshot.getString("customerName");
                                String order_customerPhoneNo = documentSnapshot.getString("customerPhoneNo");
                                for (ItemOrders order : itemOrders) {
                                    if (order.getOrderId().equals(order_id)) {
                                        newItemOrders.add(order);
                                        //    Toast.makeText(getApplicationContext(), order.getIdItem(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                            for (ItemOrders itemOrder : newItemOrders) {
                                LinearLayout linearLayout = new LinearLayout(getApplicationContext());
                                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                                TextView name = new TextView(getApplicationContext());
                                TextView quantity = new TextView(getApplicationContext());

                                name.setText(itemOrder.getNameItem());
                                quantity.setText(Long.toString(itemOrder.getQuantityItem()));

                                name.setTextSize(40);
                                quantity.setTextSize(40);

                                linearLayout.addView(name);
                                linearLayout.addView(quantity);

                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                        200, Gravity.CENTER);
                                layoutParams.setMargins(20, 50, 20, 50);
                                linearLayout.setLayoutParams(layoutParams);

                                name.setLayoutParams(new TableLayout.LayoutParams(0,
                                        ActionBar.LayoutParams.WRAP_CONTENT, 1));
                                quantity.setLayoutParams(new TableLayout.LayoutParams(0,
                                        ActionBar.LayoutParams.WRAP_CONTENT, 1));

                                name.setGravity(Gravity.CENTER);
                                quantity.setGravity(Gravity.CENTER);

                                cartLayout.addView(linearLayout);

                            }
                            newItemOrders.clear();
                        }

                    });

                }

            }
        });




        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Confirmed order", Toast.LENGTH_LONG).show();
                ordersRef.document(mAuth.getCurrentUser().getUid()).collection("Orders").document(orderId).update("status","confirmed");
                startActivity(new Intent(BookOrderItems.this, BookOrdersActivity.class));
                finish();
            }
        });
        declineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Order rejected", Toast.LENGTH_LONG).show();
                ordersRef.document(mAuth.getCurrentUser().getUid()).collection("Orders").document(orderId).delete();
                startActivity(new Intent(BookOrderItems.this, BookOrdersActivity.class));
                finish();
            }
        });


    }

    public void disableButton(String orderStatus){
        if(orderStatus.equals("confirmed")){
            acceptBtn.setVisibility(View.GONE);
            declineBtn.setVisibility(View.GONE);
        }


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(BookOrderItems.this, BookOrdersActivity.class));
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}