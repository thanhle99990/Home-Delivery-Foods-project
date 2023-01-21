package com.group.HomeDeliveryFood;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group.HomeDeliveryFood.Cart.FoodCart;
import com.group.HomeDeliveryFood.Entity.Food;
import com.group.HomeDeliveryFood.Entity.ItemOrders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class CheckoutActivity extends AppCompatActivity {

    private TextView mTextView;
    EditText nameUser,addressUser;
    TextView totalBill,phoneNumber;
    LinearLayout linearLayoutShow;
    Button buyBtn;
    private int position;
    private String phone;
    ArrayList<ItemOrders> tempItemOrders;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ordersRef = db.collection("Orders");
    private CollectionReference orderRef = db.collection("Order");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    Long billAmount = Long.valueOf(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        tempItemOrders=new ArrayList<>();

        mTextView = (TextView) findViewById(R.id.text);

        // Enables Always-on
        nameUser=findViewById(R.id.edit_text_name);
        addressUser=findViewById(R.id.edit_text_address);
        phoneNumber=findViewById(R.id.text_view_phoneNo);
        phone = mAuth.getCurrentUser().getPhoneNumber();
        phoneNumber.setText(phone);
        totalBill=findViewById(R.id.totalBill_item);
        linearLayoutShow=findViewById(R.id.linear_show_items);

        Intent intent=getIntent();
        position=intent.getIntExtra("position",0);

        FoodCart cart = MainActivity.cart.get(position).foodCart;

        Set<Food> products=cart.getProducts();

        Iterator iterator = products.iterator();
        while(iterator.hasNext()){
            Food product =(Food) iterator.next();
            LinearLayout linearLayout=new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView name=new TextView(this);
            TextView quantity=new TextView(this);
            TextView price=new TextView(this);
            TextView totalPrice=new TextView(this);

            Long number= Long.valueOf(cart.getQuantity(product));

            name.setText(product.getName());
            quantity.setText(number+"");
            price.setText(product.getUnitPrice()+"");
            totalPrice.setText(Long.toString(product.getUnitPrice()*number));

            billAmount += product.getUnitPrice()*number;

            tempItemOrders.add(new ItemOrders(product.getFoodId(),product.getName(),number,product.getUnitPrice()*number));

            name.setTextSize(20);
            quantity.setTextSize(20);
            price.setTextSize(20);
            totalPrice.setTextSize(20);

            linearLayout.addView(name);
            linearLayout.addView(quantity);

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT , Gravity.CENTER);
            layoutParams.setMargins(0,20,0,20);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setWeightSum(4);

            name.setLayoutParams(new TableLayout.LayoutParams(0,
                    ActionBar.LayoutParams.WRAP_CONTENT,1));
            quantity.setLayoutParams(new TableLayout.LayoutParams(0,
                    ActionBar.LayoutParams.WRAP_CONTENT,1));
            price.setLayoutParams(new TableLayout.LayoutParams(0,
                    ActionBar.LayoutParams.WRAP_CONTENT,1));
            totalPrice.setLayoutParams(new TableLayout.LayoutParams(0,
                    ActionBar.LayoutParams.WRAP_CONTENT,1));

            name.setGravity(Gravity.LEFT);
            quantity.setGravity(Gravity.LEFT);
            price.setGravity(Gravity.LEFT);
            totalPrice.setGravity(Gravity.LEFT);

            linearLayoutShow.addView(linearLayout);

        }
        totalBill.setText(MainActivity.cart.get(position).foodCart.m_value+"");

        buyBtn=findViewById(R.id.button_save);
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buy(tempItemOrders);
            }
        });
    }

    private void buy(final ArrayList<ItemOrders> tempItemOrders){
        String nameuser = nameUser.getText().toString();
        String addressuser= addressUser.getText().toString();
        String phoneNo = mAuth.getCurrentUser().getPhoneNumber();
        String customerId = mAuth.getCurrentUser().getUid();
        Date currentTime = Calendar.getInstance().getTime();

        if(nameuser.equals("") || addressuser.equals("") ){
//            Order order=new Order(customerId,nameuser,addressuser,mAuth.getCurrentUser().getPhoneNumber(),MainActivity.cart.get(position).getRestaurantId());
//            order.addItemOrders(this.tempItemOrders);
            Toast.makeText(CheckoutActivity.this,"Please fill in your info",Toast.LENGTH_LONG).show();
        }
        else {


            Intent intent = getIntent();
            position = intent.getIntExtra("position", 0);

            HashMap<String, Object> orders = new HashMap<>();
            orders.put("address", addressuser);
            orders.put("customerId", customerId);
            orders.put("description", nameuser);
            orders.put("customerName", nameuser);
            //   orders.put("restaurantId",MainActivity.cart.get(position).getRestaurantId() );
            orders.put("billAmount", billAmount);
            orders.put("orderDate", currentTime);
            orders.put("customerPhoneNo", phoneNo);
            orders.put("status","waiting");
            ordersRef.document(MainActivity.cart.get(position).getRestaurantId()).collection("Orders").add(orders).continueWith(new Continuation<DocumentReference, Object>() {
                @Override
                public Object then(@NonNull Task<DocumentReference> task) throws Exception {
                    if (task.isSuccessful()) {
                        for (ItemOrders itemOrder : tempItemOrders) {
                            HashMap<String, Object> order = new HashMap<>();
                            order.put("foodId", itemOrder.getIdItem());
                            order.put("foodName", itemOrder.getNameItem());
                            order.put("quantity", itemOrder.getQuantityItem());
                            order.put("totalAmount", itemOrder.getTotalAmount());

                            orderRef.document(task.getResult().getId()).collection("foodList").add(order);

                        }
                        return task.getResult().get();
                    }
                    return null;
                }
            });
            Toast.makeText(this,"Successful",Toast.LENGTH_LONG).show();

            startActivity(new Intent(CheckoutActivity.this, HomePageActivity.class));
            MainActivity.cart.remove(position);
            finish();

        }
//        ordersRef.document(MainActivity.cart.get(position).getRestaurantId()).collection("Orders").add(order).continueWith(new Continuation<DocumentReference, Object>() {
//            @Override
//            public Object then(@NonNull Task<DocumentReference> task) throws Exception {
//                if(task.isSuccessful()) {
//                    for (ItemOrders itemOrder : tempItemOrders) {
//                        HashMap<String, Object> food = new HashMap<>();
//                        food.put("foodId", itemOrder.getIdItem());
//                        food.put("quantity", itemOrder.getQuantityItem());
//                        food.put("totalAmount", itemOrder.getTotalAmount());
//
//                      task.getResult().collection("foodList").add(food);
//
//                    }
//                    return task.getResult().get();
//                }
//                return null;
//            }
//        });
//        ordersRef.add(order).continueWith(new Continuation<DocumentReference, Object>() {
//
//            @Override
//            public Object then(@NonNull Task<DocumentReference> task) throws Exception {
//                if(task.isSuccessful()) {
//                    for (ItemOrders itemOrder : tempItemOrders) {
//                        HashMap<String, Object> food = new HashMap<>();
//                        food.put("foodId", itemOrder.getIdItem());
//                        food.put("quantity", itemOrder.getQuantityItem());
//                        food.put("totalAmount", itemOrder.getTotalAmount());
//
//                      task.getResult().collection("foodList").add(food);
//
//                    }
//                    return task.getResult().get();
//                }
//                return null;
//            }
//        });
//






    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(CheckoutActivity.this, HomePageActivity.class));
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}