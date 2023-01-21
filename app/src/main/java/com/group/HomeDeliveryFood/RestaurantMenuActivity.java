package com.group.HomeDeliveryFood;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.group.HomeDeliveryFood.Adapter.FoodRecyclerAdapter;
import com.group.HomeDeliveryFood.Adapter.MenuRestaurantAdapter;
import com.group.HomeDeliveryFood.Advantage.RatingBarActivity;
import com.group.HomeDeliveryFood.Entity.AllFood;
import com.group.HomeDeliveryFood.Entity.Comment;
import com.group.HomeDeliveryFood.Entity.Food;
import com.group.HomeDeliveryFood.Entity.RestaurantSection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RestaurantMenuActivity extends AppCompatActivity implements FoodRecyclerAdapter.OnClickItemListener {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private CollectionReference restaurantRef = db.collection("Restaurants");
    private CollectionReference foodRef = db.collection("Foods");
    private CollectionReference categoryRef = db.collection("Categories");
    private CollectionReference userRef = db.collection("User");
    private CollectionReference commentRef = db.collection("Comments");
    private RecyclerView mainCategoryRecycler;
    private MenuRestaurantAdapter mainRecyclerAdapter;

    private ImageView image;
    private FloatingActionButton addCommentBtn;
    private RatingBar ratingBar;
    private TextView name,type,address,workTime;
    private String restaurantIdIntent,imageUrlIntent,nameIntent,addressIntent,workTimeIntent;
    private Float ratingIntent;



    List<AllFood> categoryList = new ArrayList<>();
    List<Comment> commentsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);


        Intent intent = getIntent();
        restaurantIdIntent = intent.getStringExtra("restaurantId");
        nameIntent = intent.getStringExtra("name");
        addressIntent=intent.getStringExtra("description");
        imageUrlIntent=intent.getStringExtra("image");
        workTimeIntent=intent.getStringExtra("workTime");
        ratingIntent=intent.getFloatExtra("ratingNumber",0);


        name=findViewById(R.id.restaurant_name);
        address=findViewById(R.id.restaurant_address);
        image=findViewById(R.id.image_restaurant);
        workTime=findViewById(R.id.work_time);
        ratingBar=findViewById(R.id.ratingBar);

        addCommentBtn=findViewById(R.id.button_add_comment);
        addCommentBtn.hide();
        commentRef.document(restaurantIdIntent).collection("CommentList").document(mAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(!value.exists()){
                    addCommentBtn.show();
                    addCommentBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            userRef.document(mAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    Intent intent = new Intent(RestaurantMenuActivity.this, RatingBarActivity.class);
                                    intent.putExtra("restaurantID",restaurantIdIntent);
                                    intent.putExtra("imgUser",value.getString("image"));
                                    intent.putExtra("nameUser",value.getString("name"));
                                    intent.putExtra("idUser",mAuth.getCurrentUser().getUid());
                                    startActivity(intent);
                                }

                            });
                            //         startActivity(intent);

                        }
                    });
                }
            }
        });



        name.setText(nameIntent);
        address.setText(addressIntent);
        workTime.setText(workTimeIntent);
        ratingBar.setRating(ratingIntent);
        Glide.with(this)
                .asBitmap()
                .load(imageUrlIntent)
                .into(image);

        // here we will add some dummy data to our model class

        // here we will add data to category item model class

        // added in first category
//        List<Food> categoryItemList = new ArrayList<>();
//        categoryItemList.add(new Food("Tra sua tran chau", (long) 55000,"Tra sua","https://media.officedepot.com/image/upload/b_rgb:FFFFFF,c_pad,dpr_1.0,f_auto,h_1665,q_auto,w_1250/c_pad,h_1665,w_1250/v1/products/208255/208255_p?pgw=1&pgwact=1","ngocdeptrai"));
//        categoryItemList.add(new Food("Tra dao", (long) 55000,"Tra trai cay","https://www.google.com/imgres?imgurl=https%3A%2F%2Fcafedidong.vn%2Fwp-content%2Fuploads%2F2018%2F09%2FTr%25C3%25A0-%25C4%2591%25C3%25A0o.jpg&imgrefurl=https%3A%2F%2Fcafedidong.vn%2Fshop%2Ftra-dao%2F&tbnid=eiG-FvkJTRlxJM&vet=12ahUKEwiTk_2y_MztAhVKxIsBHa5IDEgQMygKegUIARDyAQ..i&docid=CQat2NFGg7n3IM&w=1404&h=1404&q=tra%20dao&ved=2ahUKEwiTk_2y_MztAhVKxIsBHa5IDEgQMygKegUIARDyAQ","ngocdeptrai"));
//        categoryItemList.add(new Food("Tra tao", (long) 55000,"Tra trai cay","https://www.google.com/imgres?imgurl=http%3A%2F%2Fcongthucphache.com%2Fwp-content%2Fuploads%2F2019%2F12%2Ftra-trai-cay-tao.jpg&imgrefurl=https%3A%2F%2Fcongthucphache.com%2Fcong-thuc-pha-che-tra-trai-cay-tao%2F&tbnid=BnZ1d6lcVzCMbM&vet=12ahUKEwipyO68_MztAhUVJqYKHWuABvcQMygPegUIARC8AQ..i&docid=dhyfaZ2LKGpdlM&w=900&h=900&q=tra%20tao&ved=2ahUKEwipyO68_MztAhUVJqYKHWuABvcQMygPegUIARC8AQ","ngocdeptrai"));
//        categoryItemList.add(new Food("Matcha da xay", (long) 55000,"Da xay","https://www.google.com/imgres?imgurl=http%3A%2F%2Fmrkong.vn%2Fwp-content%2Fuploads%2F2019%2F10%2FIMG_8863-FILEminimizer-e1570000573713.jpg&imgrefurl=http%3A%2F%2Fmrkong.vn%2Fsan-pham%2Fmatcha-da-xay-2%2F&tbnid=V4ehtqbjcEAhkM&vet=12ahUKEwi5zIfR_MztAhUJ35QKHdVlAG4QMygIegUIARDIAQ..i&docid=deqNtFm1AfWhHM&w=768&h=1152&q=matcha%20da%20xay&ved=2ahUKEwi5zIfR_MztAhUJ35QKHdVlAG4QMygIegUIARDIAQ","ngocdeptrai"));
//

        foodRef.addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                final List<Food> categoryItemList = new ArrayList<>();
                final List<RestaurantSection> sectionList = new ArrayList<>();
                for(DocumentSnapshot documentSnapshot : value){
                    String food_id = documentSnapshot.getId();
                    String food_restaurantId = documentSnapshot.getString("restaurantId");
                    String food_name = documentSnapshot.getString("name");
                    Long food_unitPrice = documentSnapshot.getLong("unitPrice");
                    String food_category = documentSnapshot.getString("category");
                    String food_image = documentSnapshot.getString("image");
                    String food_description = documentSnapshot.getString("description");

                    if(food_restaurantId.equals(restaurantIdIntent)){
                        categoryItemList.add(new Food(food_id,food_name,food_unitPrice,food_category,food_image,food_description));
                //    }
//                        Log.d("Test size","CAI LOZZ MA MAY CAI SIZE MAT DAY: "+categoryItemList.size());
//                        Toast.makeText(getApplicationContext(), "Test size " + categoryItemList.size(), Toast.LENGTH_LONG).show();
                }}
                sectionList.add(new RestaurantSection("Menu", categoryItemList));
                commentRef.document(restaurantIdIntent).collection("CommentList").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        List<Comment> commentList = new ArrayList<>();
                        for(DocumentSnapshot documentSnapshot : value){
                            String comment_userId = documentSnapshot.getId();
                            String comment_userName = documentSnapshot.getString("name");
                            String comment_userImg = documentSnapshot.getString("image");
                            Float comment_rateValue = Float.valueOf(documentSnapshot.getString("rateValue"));
                            String comment_review = documentSnapshot.getString("review");
                            String comment_status = documentSnapshot.getString("status");
                            Timestamp timestamp = (Timestamp) documentSnapshot.getData().get("currentDate");
                            final Date comment_date = timestamp.toDate();

                            Comment comment = new Comment(restaurantIdIntent,comment_userId,comment_userName,comment_userImg, comment_rateValue,comment_review,comment_status,comment_date);
                            commentList.add(comment);
                            commentsList=commentList;
//                            v
                            //commentsList= (ArrayList<Comment>) commentList;
                        }
//                        Toast.makeText(getApplicationContext(), "Comment size " + commentList.size(), Toast.LENGTH_LONG).show();
                        setMainCategoryRecycler(sectionList,commentsList);

                    }
                });
            }


        });

//        commentRef.document(mAuth.getCurrentUser().getUid()).collection("CommentList").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                List<Comment> commentList = new ArrayList<>();
//                for(DocumentSnapshot documentSnapshot : value){
//                    String comment_userId = documentSnapshot.getId();
//                    String comment_userName = documentSnapshot.getString("name");
//                    String comment_userImg = documentSnapshot.getString("image");
//                    Long comment_rateValue = documentSnapshot.getLong("rateValue");
//                    String comment_review = documentSnapshot.getString("review");
//                    String comment_status = documentSnapshot.getString("status");
//                    Timestamp timestamp = (Timestamp) documentSnapshot.getData().get("currentDate");
//                    final Date comment_date = timestamp.toDate();
//
//                    Comment comment = new Comment(mAuth.getCurrentUser().getUid(),comment_userId,comment_userName,comment_userImg, comment_rateValue,comment_review,comment_status,comment_date);
//                    commentList.add(comment);
//                    commentsList=commentList;
//                    //commentsList= (ArrayList<Comment>) commentList;
//                }
//
//            }
//        });
        //Toast.makeText(this,"Size: "+categoryList.size()+" Comment size:"+commentsList.size(),Toast.LENGTH_LONG).show();
        //setMainCategoryRecycler(categoryList,commentsList);

//        List<AllFood> allCategoryList = new ArrayList<>();
//        allCategoryList.add(new AllFood("Trà Sữa", categoryItemList));
        //allCategoryList.add(new AlllItem("Trà trái cây", categoryItemList2));
        //allCategoryList.add(new AllCategory("Đá xay", categoryItemList3));
        //allCategoryList.add(new AllCategory("Cà phê", categoryItemList4));





    }

    private void setMainCategoryRecycler(List<RestaurantSection> allCategoryList, List<Comment> commentsList){

        mainCategoryRecycler = findViewById(R.id.main_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mainCategoryRecycler.setLayoutManager(layoutManager);
        mainRecyclerAdapter = new MenuRestaurantAdapter(getApplicationContext(), allCategoryList,commentsList,this);
        mainCategoryRecycler.setAdapter(mainRecyclerAdapter);

    }

    @Override
    public void onItemClick(String idItem, String nameItem,long priceItem,String desItem,String imgItem) {
        MiniFoodSheet bottomSheet = new MiniFoodSheet(restaurantIdIntent,name.getText().toString(), address.getText().toString(),imageUrlIntent,idItem,nameItem, priceItem,desItem,imgItem);
        bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
    }

    @Override
    public void onViewItemClick(String id, String name,String description,long price,String imageUrl) {
        Intent intent=new Intent(this,Infor_Food.class);
        intent.putExtra("foodId", id);
        intent.putExtra("name",name);
        intent.putExtra("description",description);
        intent.putExtra("price",price);
        intent.putExtra("imageUrl",imageUrl);
        intent.putExtra("nameRestaurant",nameIntent);
        intent.putExtra("addressRestaurant",addressIntent);
        intent.putExtra("urlRestaurant",imageUrlIntent);
        intent.putExtra("idRestaurant",restaurantIdIntent);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_navigation,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.love:
                //Toast.makeText(this,"Cart selected",Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(this,ListRestaurantCart.class);
                //startActivity(intent);
                return true;
            case R.id.cart:
                Toast.makeText(this,"Item 2 selected",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,ShowRestaurantsActivity.class);
                startActivity(intent);

                return true;
            case R.id.about:
                intent = new Intent(this,ShowInfoActivity.class);
                startActivity(intent);
                return true;
            case R.id.home:
                intent = new Intent(this,HomePageActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}