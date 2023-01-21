package com.group.HomeDeliveryFood;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.group.HomeDeliveryFood.Adapter.BookOrdersRecycler;
import com.group.HomeDeliveryFood.Entity.ItemOrders;
import com.group.HomeDeliveryFood.Entity.Order;

import java.util.ArrayList;
import java.util.Date;

public class BookOrdersActivity extends AppCompatActivity implements BookOrdersRecycler.OnClickItemListener{
    private static final String TAG = "MainActivity";

    //vars




    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ordersRef = db.collection("Orders");
    private CollectionReference orderRef = db.collection("Order");


    @Override
    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_orders_activity);

 //       Toast.makeText(this,""+MainActivity.cart.size(),Toast.LENGTH_LONG).show();



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
                        Toast.makeText(getApplicationContext(), "no item", Toast.LENGTH_LONG).show();
                    }
                    else {
                   //     Toast.makeText(getApplicationContext(), "have item", Toast.LENGTH_LONG).show();

                        ordersRef.document(mAuth.getCurrentUser().getUid()).collection("Orders").addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                final ArrayList<Order> orders = new ArrayList<>();

                                for (final DocumentSnapshot documentSnapshot : value) {
                                    final ArrayList<ItemOrders> newItemOrders = new ArrayList<>();
                                    final String order_address = documentSnapshot.getString("address");
                                    final String order_customerId = documentSnapshot.getString("customerId");
                                    Timestamp timestamp = (Timestamp) documentSnapshot.getData().get("orderDate");
                                    final Date order_date = timestamp.toDate();
                                    final Long order_billAmount = documentSnapshot.getLong("billAmount");
                                    String order_status = documentSnapshot.getString("status");
                                    String order_id = documentSnapshot.getId();
                                    String order_customerName =documentSnapshot.getString("customerName");
                                    String order_customerPhoneNo = documentSnapshot.getString("customerPhoneNo");
                                    for (ItemOrders order : itemOrders) {
                                        if (order.getOrderId().equals(order_id) && order_status.equals("waiting")) {
                                            newItemOrders.add(order);
                                        //    Toast.makeText(getApplicationContext(), order.getIdItem(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    String restaurantId = mAuth.getCurrentUser().getUid();
                                    orders.add(new Order(order_id, order_customerId,order_customerName,order_customerPhoneNo,order_address, restaurantId, newItemOrders, order_date, order_billAmount, order_status));
                                }
                                initRecyclerView(orders);
                            }
                        });
                    }
            }
        });

    }


    private void initRecyclerView(ArrayList<Order> orders){


        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        BookOrdersRecycler adapter = new BookOrdersRecycler(getApplicationContext(),orders,this);
        recyclerView.setAdapter(adapter);




    }


    @Override
    public void onItemClick(Order order) {
        Intent intent = new Intent(this, BookOrderItems.class);

        intent.putExtra("orderId", order.getIdOrder());
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(BookOrdersActivity.this, MainActivity.class));
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
