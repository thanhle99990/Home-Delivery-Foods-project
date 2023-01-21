package com.group.HomeDeliveryFood.Advantage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group.HomeDeliveryFood.R;
import com.group.HomeDeliveryFood.RestaurantMenuActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class RatingBarActivity extends AppCompatActivity {
    TextView rateCount,showRating;
    EditText review;
    Button submit;
    RatingBar ratingBar;
    Float rateValue;
    String temp;

    String restaurantID,nameUser,imgUser,idUser;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef = db.collection("User");
    private CollectionReference commentsRef = db.collection("Comments");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_bar);


        final Intent intent=getIntent();
        restaurantID=intent.getStringExtra("restaurantID");
        nameUser=intent.getStringExtra("nameUser");
        imgUser=intent.getStringExtra("imgUser");
        idUser=intent.getStringExtra("idUser");


        rateCount=findViewById(R.id.rateCount);
        ratingBar =findViewById(R.id.ratingBar);
        review=findViewById(R.id.review);
        submit=findViewById(R.id.submitBtn);
        showRating=findViewById(R.id.showRating);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rateValue = rating;
                if(rateValue<=1 && rateValue>=0){
                    rateCount.setText("Bad "+rateValue+"/5");
                }else if(rateValue<=2 && rateValue>1){
                    rateCount.setText("OK "+rateValue+"/5");
                }else if(rateValue<=3 && rateValue>2){
                    rateCount.setText("Good "+rateValue+"/5");
                }else if(rateValue<=4 && rateValue>3){
                    rateCount.setText("Very Good "+ rateValue +"/5");
                }else if(rateValue<=5 && rateValue>4){
                    rateCount.setText("Best "+rateValue+"/5");
                }

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp=rateCount.getText().toString();
                showRating.setText("Your rating: "+ temp +" "+review.getText());

            //    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
             //   Comment comment=new Comment(restaurantID,idUser,nameUser,imgUser,rateValue,review.getText().toString(),temp,currentDate);
                Date currentTime = Calendar.getInstance().getTime();
                HashMap<String, Object> newComment = new HashMap<>();
                newComment.put("name", nameUser );
                newComment.put("image", imgUser );
                newComment.put("review", review.getText().toString());
                newComment.put("status", temp );
                newComment.put("currentDate", currentTime);
                newComment.put("rateValue",String.valueOf((Float)ratingBar.getRating()));
                commentsRef.document(restaurantID).collection("CommentList").document(idUser).set(newComment);
                review.setText("");
                ratingBar.setRating(0)  ;
                rateCount.setText("");
                submit.setActivated(false);


                Intent intent = getIntent();
                String name = intent.getStringExtra("name");
                String description = intent.getStringExtra("description");
                String imageUrl = intent.getStringExtra("image");
                String restaurantId = intent.getStringExtra("restaurantId");

                Intent intent1=new Intent(RatingBarActivity.this,RestaurantMenuActivity.class);
                //  intent.putExtra("restaurantId",restaurantId);
                intent1.putExtra("name",name);
                intent1.putExtra("description",description);
                intent1.putExtra("image",imageUrl);
                intent1.putExtra("restaurantId",restaurantId);
                startActivity(intent1);
                finish();
            }
        });
    }
}
