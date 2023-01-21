package com.group.HomeDeliveryFood;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.group.HomeDeliveryFood.Adapter.MenuHandlerAdapter;
import com.group.HomeDeliveryFood.Entity.Food;

import java.util.ArrayList;

public class Restaurant_Handler_Main extends AppCompatActivity implements MenuHandlerAdapter.OnClickItemListener{
    private MenuHandlerAdapter adapter;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference foodRef = db.collection("Foods");
    FloatingActionButton addBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant__handler__main);

        final String restautantId = mAuth.getCurrentUser().getUid();
//        foods.add(new Food("1","Xuc xich bony", (long) 13000,"1","https://www.google.com/imgres?imgurl=https%3A%2F%2Fsalt.tikicdn.com%2Fcache%2Fw1200%2Fts%2Fproduct%2F60%2F97%2Fba%2Fdd2904f856cb8239986de4939391457b.jpg&imgrefurl=https%3A%2F%2Ftiki.vn%2Fp-nnie-xuc-xich-muc-1-nang-hop-12-cay-x-45g-p52691167.html&tbnid=f-xaMgAV1PbwPM&vet=12ahUKEwjm4Jqmz8_tAhWOAKYKHR6lBI8QMygIegUIARDAAQ..i&docid=uYcuOkpS_bIhXM&w=1200&h=1200&q=x%C3%BAc%20x%C3%ADch%20ponnie&client=firefox-b-d&ved=2ahUKEwjm4Jqmz8_tAhWOAKYKHR6lBI8QMygIegUIARDAAQ","Mlem mlem"));
//        foods.add(new Food("2","Com chien duong chau", (long) 30000,"2","https://www.google.com/imgres?imgurl=https%3A%2F%2Fameovat.com%2Fwp-content%2Fuploads%2F2016%2F05%2Fcach-lam-com-chien-duong-chau-600x481.jpg&imgrefurl=https%3A%2F%2Fameovat.com%2Fcach-lam-com-chien-duong-chau-ngon-tuyet-tai-nha&tbnid=RWVoQBMhjvex8M&vet=12ahUKEwimhJe6z8_tAhVC5pQKHaHCCu0QMygFegUIARC3AQ..i&docid=vqFKUQtXU_itXM&w=600&h=481&q=c%C6%A1m%20chi%C3%AAn%20d%C6%B0%C6%A1ng%20ch%C3%A2u%20c%C3%A1ch%20l%C3%A0m&client=firefox-b-d&ved=2ahUKEwimhJe6z8_tAhVC5pQKHaHCCu0QMygFegUIARC3AQ","Mlem mlem"));
//        foods.add(new Food("3","Mi xao gion", (long) 13000,"2","https://www.google.com/imgres?imgurl=https%3A%2F%2Fi.ytimg.com%2Fvi%2FZPi2wdg_he8%2Fmaxresdefault.jpg&imgrefurl=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3DZPi2wdg_he8&tbnid=bye-BU19-sdnmM&vet=12ahUKEwi5s__Hz8_tAhXiGKYKHfYpDOIQMygBegUIARCkAQ..i&docid=B2_bkTe4-o0QyM&w=1280&h=720&q=m%C3%AC%20x%C3%A0o%20gi%C3%B2n%20h%E1%BA%A3i%20s%E1%BA%A3n&client=firefox-b-d&ved=2ahUKEwi5s__Hz8_tAhXiGKYKHfYpDOIQMygBegUIARCkAQ","Mlem mlem"));
        foodRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<Food> foods = new ArrayList<>();
                for(DocumentSnapshot documentSnapshot : value){
                    String food_restaurantId = documentSnapshot.getString("restaurantId");
                    String food_id = documentSnapshot.getId();
                    String food_category = documentSnapshot.getString("categories");
                    String food_name = documentSnapshot.getString("name");
                    Long food_unitPrice = documentSnapshot.getLong("unitPrice");
                    String food_image = documentSnapshot.getString("image");
                    String food_description = documentSnapshot.getString("description");
                    if(food_restaurantId.equals(restautantId)){
                        Food food = new Food(food_id,food_name,food_unitPrice,food_category,food_image,food_description);
                        foods.add(food);
                    }
                }
                setUpRecyclerView(foods);
            }
        });

//        setUpRecyclerView();
        addBtn=findViewById(R.id.button_add_item);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Restaurant_Handler_Main.this,AddFoodToMenuActivity.class);
                startActivity(intent);

            }
        });
    }
    private void setUpRecyclerView(final ArrayList<Food> foods) {
        adapter = new MenuHandlerAdapter(this,foods,this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                        foods.remove(viewHolder.);
//                        adapter.notifyDataSetChanged();
//                Toast.makeText(Restaurant_Handler_Main.this, viewHolder.getAdapterPosition() + "", Toast.LENGTH_LONG).show();
                adapter.remove(viewHolder.getAdapterPosition());

            }
        }).attachToRecyclerView(recyclerView);
    }
    @Override
    protected void onStart() {
        super.onStart();
        //adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        //adapter.stopListening();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.restaurant_handler,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.book:
                Toast.makeText(this,"Book selected",Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(this,ListRestaurantCart.class);
                //startActivity(intent);
                return true;
            case R.id.menu:
                return true;
            case android.R.id.home:
                startActivity(new Intent(Restaurant_Handler_Main.this, MainActivity.class));
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(String foodId) {
        Intent intent = new Intent(this,UpdateFoodActivity.class);
        intent.putExtra("foodId",foodId);
        Toast.makeText(this,foodId+"",Toast.LENGTH_LONG).show();
        startActivity(intent);

    }
}