package com.group.HomeDeliveryFood;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.group.HomeDeliveryFood.Adapter.CategoryRecyclerAdapter;
import com.group.HomeDeliveryFood.Adapter.RestaurantRecyclerAdapter;
import com.group.HomeDeliveryFood.Advantage.NotificationActivity;
import com.group.HomeDeliveryFood.Entity.Category;
import com.group.HomeDeliveryFood.Entity.Food;
import com.group.HomeDeliveryFood.Entity.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity implements RestaurantRecyclerAdapter.OnClickItemListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference categoryRef = db.collection("Categories");
    CollectionReference restaurantRef = db.collection("Restaurants");
    CollectionReference adRef = db.collection("Advertisements");
    CollectionReference foodRef = db.collection("Foods");

    ViewFlipper viewFlipper;
    RecyclerView mainCategoryRecycler;
    CategoryRecyclerAdapter mainRecyclerAdapter;

    TextView name,type,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        getSupportActionBar().setTitle("Restaurants");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.love:
//                        //startActivity(new Intent(getApplicationContext(),ListLoveRestaurants.class));
//                        //overridePendingTransition(0,0);
//                        return true;
//                    case R.id.home:
//                        return true;
//                    case R.id.about:
//                        //startActivity(new Intent(getApplicationContext(),Information.class));
//                        //overridePendingTransition(0,0);
//                        return true;
//                    case R.id.cart:
//                        //startActivity(new Intent(getApplicationContext(),ListRestaurantCart.class));
//                        //overridePendingTransition(0,0);
//                        return true;
//
//                }
//                return false;
//            }
//        });

        viewFlipper = findViewById(R.id.viewflipper);
        ActionViewFlipper();


//        });
        foodRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                 final List<Food> foodList = new ArrayList<>();
                 final Restaurant[] restaurant = new Restaurant[1];
                 final List<Category> categoryList = new ArrayList<>();
                 final List<Category> duplicateCategoryList = new ArrayList<>();

                for (DocumentSnapshot documentSnapshot : value) {
                    final String food_id = documentSnapshot.getId();
                    final String food_name = documentSnapshot.getString("name");
                    final String food_category = documentSnapshot.getString("categories");
                    final Long food_unitPrice = documentSnapshot.getLong("unitPrice");
                    final String food_image = documentSnapshot.getString("image");
                    final String food_restaurantId = documentSnapshot.getString("restaurantId");

                    final String[] restaurant_id = new String[1];
                    final String[] restaurant_name = new String[1];
                    final String[] restaurant_address = new String[1];
                    final String[] restaurant_image = new String[1];
                    if (food_restaurantId.isEmpty() == false) {
                        restaurantRef.document(food_restaurantId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (value.exists() && value != null) {

                                    restaurant_id[0] =value.getString("id");
                                    restaurant_name[0] = value.getString("name");
                                    restaurant_address[0] = value.getString("address");
                                    restaurant_image[0] = value.getString("image");

                                    Log.d("dtb", food_name + " data: " + restaurant_name[0] + " " + restaurant_address[0]);
                                    restaurant[0] = new Restaurant(restaurant_id[0],restaurant_name[0], restaurant_image[0], restaurant_address[0]);
                                    restaurant[0].setRestaurantId(food_restaurantId);
                                    foodList.add(new Food(food_id,food_name, food_unitPrice, food_category, food_image, restaurant[0]));
                                    //                                String restaurant_name = value.getString("name");
                                    //                                String restaurant_address = value.getString("address"); String restaurant_image = value.getString("image");
                                    //                                restaurantList.add(new Restaurant(restaurant_name,restaurant_address,restaurant_image));
                                } else {
                                    Log.d("dtb", "not found restaurant");
                                }
                            }
                        });
                    } else {
                        Log.d("dtb", "not found restaurant");
                    }

                }
//                for(Food food : foodList){
//                    Log.d("dtb1", food.getCategory() + " " + food.getName() + " " + food.getRestaurant().getName() + " "  );
//                }
                categoryRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(DocumentSnapshot documentSnapshot : value){
                            final List<Restaurant> categoryItemList = new ArrayList<>();
                            List<Restaurant> newCategoryItemList = new ArrayList<>();
                            String category_name = documentSnapshot.getString("name");
                            String category_id = documentSnapshot.getId();
//                            Log.d("dtb", category_id);
//                            for(Food food : foodList){
//                                Log.d("dtb", food.getCategory() + " " + food.getName() + " " + food.getRestaurant().getName() + " "  );
//                            }
                            for(Food food : foodList){
                                if(food.getCategory() != null){
                                    if(food.getCategory().equals(category_id) && categoryItemList.contains(food.getRestaurant()) == false ){
                                        categoryItemList.add(food.getRestaurant());
//                                        Log.d("dtb", category_id + " " + food.getRestaurant().getName() );
                                    }
                                }
                                else{
//                                    Log.d("dtb", category_id + " " + food.getRestaurant().getName() );
                                }

                            }

                            categoryList.add(new Category(category_name, categoryItemList));


                        }


                        setMainCategoryRecycler(categoryList);
                    }
                });

            }



        });




//       categoryItemList.add(new Restaurant("https://www.google.com/imgres?imgurl=https%3A%2F%2Fkichi.com.vn%2Fwp-content%2Fuploads%2Fsites%2F14%2F2018%2F11%2Flogo-kichi.png&imgrefurl=https%3A%2F%2Fkichi.com.vn%2F&tbnid=vi_4TewghJYv1M&vet=12ahUKEwjQ9vyWirztAhW6yosBHYe4CmwQMygSegUIARC_AQ..i&docid=02SzbdbpWnXtlM&w=1481&h=1844&q=kichi%20kichi&client=firefox-b-d&ved=2ahUKEwjQ9vyWirztAhW6yosBHYe4CmwQMygSegUIARC_AQ", "Kichi", "Ngon vl"));
 //       categoryItemList.add(new Restaurant("data:image/jpeg;base64,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", "Doki Doki", "Ngon vl"));
 //       categoryItemList.add(new Restaurant("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRgSBo9aaPVvfYjQp57J3yKskITB5fCBiC-sEUfY9A_jj2x3fbvjTdmc9dSiOdvDQ5PCfA0zE-vQk0wpfn3ayVJF31TMZKTPov85w&usqp=CAU&ec=45732302", "Hanuri", "Ngon vl"));
//        categoryItemList.add(new Restaurant("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQtcZr_FLKyCqMx7Ni_4bJWySBjiyFphzM1WTZNCldHAXUhLsTUnu6AijrrHX2m-KuzFsFlEqLKhwOWCYAUvvJ1E9v-LlNe6qEHQw&usqp=CAU&ec=45732302", "Texas", "Ngon vl"));
//

//        List<Restaurant> categoryItemList2 = new ArrayList<>();
//        categoryItemList.add(new Restaurant("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT9GOG1NjkOnzFjxZdyG04SUflAy0vjZB7xxQ&usqp=CAU", "KingBBQ", "Ngon vl"));
//        categoryItemList.add(new Restaurant("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT9GOG1NjkOnzFjxZdyG04SUflAy0vjZB7xxQ&usqp=CAU", "KingBBQ", "Ngon vl"));
//        categoryItemList.add(new Restaurant("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT9GOG1NjkOnzFjxZdyG04SUflAy0vjZB7xxQ&usqp=CAU", "KingBBQ", "Ngon vl"));
//        categoryItemList.add(new Restaurant("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT9GOG1NjkOnzFjxZdyG04SUflAy0vjZB7xxQ&usqp=CAU", "KingBBQ", "Ngon vl"));
//

//        List<Category> allCategoryList = new ArrayList<>();
//        allCategoryList.add(new Category("Nhà hàng", categoryItemList));
//        allCategoryList.add(new Category("Restaurants", categoryItemList2));
        //allCategoryList.add(new AllCategory("Đá xay", categoryItemList3));
        //allCategoryList.add(new AllCategory("Cà phê", categoryItemList4));


    }

    private List<Restaurant> removeDuplicates(List<Restaurant> itemCategoryList) {
        // Create a new ArrayList
        List<Restaurant> newList = new ArrayList<>();
        // Traverse through the first list
        for (Restaurant element : itemCategoryList) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }

    private void ActionViewFlipper() {
     //   final ArrayList<String> mangquangcao = new ArrayList<>();

        adRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            ArrayList<String> mangquangcao = new ArrayList<>();
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentSnapshot documentSnapshot : value){
                    mangquangcao.add(documentSnapshot.getString("image"));
                }
                for (String ad : mangquangcao) {
                    flipImages(ad);
                }
//
            }
        });
//        mangquangcao.add("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSg8ccI_XXpD-1WUVYkxzjlVtLivPYTXleNhA&usqp=CAU");
//        mangquangcao.add("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQG3657TiQ4hoQeAMbuAkshhaVOqbueLcIosA&usqp=CAU");
//        mangquangcao.add("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR2WTo59pfuBuXgdRBAe9Lj1OzOmGx_zNoJ4Q&usqp=CAU");
//        mangquangcao.add("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRcAsP5pYkELDzEge1gCxx-kopLHxpmuMVaxQ&usqp=CAU");

//        for (int i = 0; i < mangquangcao.size(); i++) {
//            ImageView imageView = new ImageView(getApplicationContext());
//         //   Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
//            Picasso.get().load(mangquangcao.get(i)).into(imageView);
//
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            viewFlipper.addView(imageView);
//        }
//        viewFlipper.setFlipInterval(5000);
//        viewFlipper.setAutoStart(true);
//        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
//        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
//        viewFlipper.setInAnimation(animation_slide_in);
//        viewFlipper.setOutAnimation(animation_slide_out);
    }

    public void flipImages(String imageUrl) {
        ImageView imageView = new ImageView(getApplicationContext());
        //   Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
        Picasso.get().load(imageUrl).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        viewFlipper.startFlipping();
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);


    }
    private void setMainCategoryRecycler(List<Category> allCategoryList) {
        mainCategoryRecycler = findViewById(R.id.category_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mainCategoryRecycler.setLayoutManager(layoutManager);
        mainRecyclerAdapter = new CategoryRecyclerAdapter(this, allCategoryList,this);
        mainCategoryRecycler.setAdapter(mainRecyclerAdapter);

    }


    @Override
    public void onViewClick(String name, String description, String imageUrl, String restaurantId) {
        Intent intent=new Intent(this,RestaurantMenuActivity.class);
      //  intent.putExtra("restaurantId",restaurantId);
        intent.putExtra("name",name);
        intent.putExtra("description",description);
        intent.putExtra("image",imageUrl);
        intent.putExtra("restaurantId",restaurantId);
        startActivity(intent);
        finish();
     //   Toast.makeText(this,"View button selected",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_navigation,menu);

        MenuItem item = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mainRecyclerAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mainRecyclerAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.cart:
                Toast.makeText(this,"Item 2 selected",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,ShowRestaurantsActivity.class);
                startActivity(intent);
                return true;
            case R.id.love:
                Toast.makeText(this,"Sub Item 1 selected",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.about:
                intent = new Intent(this,ShowInfoActivity.class);
                startActivity(intent);
                return true;
            case R.id.home:
                startActivity(new Intent(HomePageActivity.this, MainActivity.class));
                finish();
            case R.id.notification:
                startActivity(new Intent(HomePageActivity.this, NotificationActivity.class));
                finish();
            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
