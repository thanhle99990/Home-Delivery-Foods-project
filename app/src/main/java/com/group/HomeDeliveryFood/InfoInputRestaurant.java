package com.group.HomeDeliveryFood;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class InfoInputRestaurant extends AppCompatActivity {

    private EditText restaurantName, restaurantAddress, restaurantImageLink;
    private Button submitBtn,clearBtn;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference restaurantRef = db.collection("Restaurants");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_restaurant_input);

        restaurantName = findViewById(R.id.edit_text_name);
        restaurantAddress = findViewById(R.id.edit_text_address);
        restaurantImageLink = findViewById(R.id.edit_text_image_link);
        submitBtn = findViewById(R.id.button_submit);
        clearBtn = findViewById(R.id.button_clear);


        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantName.getText().clear();
                restaurantAddress.getText().clear();
                restaurantImageLink.getText().clear();
                Toast.makeText(InfoInputRestaurant.this, "Cleared", Toast.LENGTH_LONG).show();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_restaurantName = restaurantName.getText().toString();
                String txt_restaurantAddress = restaurantAddress.getText().toString();
                String txt_restaurantImageLink = restaurantImageLink.getText().toString();
                String restaurantId = mAuth.getCurrentUser().getUid();

                if(TextUtils.isEmpty(txt_restaurantName) || TextUtils.isEmpty(txt_restaurantAddress)){
                    Toast.makeText(InfoInputRestaurant.this, "Empty, please input your info!", Toast.LENGTH_SHORT).show();
                }
                else {
                    HashMap<String, Object> newRestaurant = new HashMap<>();
                    newRestaurant.put("name", txt_restaurantName);
                    newRestaurant.put("address", txt_restaurantAddress);
                    newRestaurant.put("image", txt_restaurantImageLink);
                    restaurantRef.document(restaurantId).set(newRestaurant);
                    Toast.makeText(InfoInputRestaurant.this, "Save successful", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(InfoInputRestaurant.this, MainActivity.class));
                    finish();
                }
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "Please input your info!", Toast.LENGTH_LONG).show();
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
