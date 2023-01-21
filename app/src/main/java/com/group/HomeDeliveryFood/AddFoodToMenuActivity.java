package com.group.HomeDeliveryFood;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class AddFoodToMenuActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference foodRef = db.collection("Foods");
    private CollectionReference categoryRef = db.collection("Categories");

    private EditText editTextName;
    private EditText editTextDescription;
    private EditText editTextImage;
  //  private EditText editTextCategory;
    private EditText editTextPrice;
    private Spinner spinnerCategory;
    private Button saveBtn;

    private String restaurantId;


    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_to_menu);
        setTitle("Add Item");
        editTextName= findViewById(R.id.edit_text_name);
        editTextDescription = findViewById(R.id.edit_text_description);
        editTextImage = findViewById(R.id.edit_text_image);
  //      editTextCategory = findViewById(R.id.edit_text_category);
        editTextPrice=findViewById(R.id.edit_text_price);
        spinnerCategory = findViewById(R.id.spinner_category);

        ArrayList<String> itemCategory = new ArrayList<>();
        categoryRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<String> itemCategory = new ArrayList<>();
                for(DocumentSnapshot documentSnapshot : value){
//                    String category_id = documentSnapshot.getId();
//                    itemCategory.add(category_id);
                    String category_name = documentSnapshot.getString("name");
                    itemCategory.add(category_name);
                }
                spinnerCategory.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,itemCategory));
            }
        });
      //  spinnerCategory.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,itemCategory));

   //     restaurantId = mAuth.getCurrentUser().getUid();

        saveBtn=findViewById(R.id.button_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                saveFood();
            }
        });

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
                //Intent intent = new Intent(this,ListRestaurantCart.class);
                //startActivity(intent);
                return true;
            case R.id.menu:
                Toast.makeText(this,"Book selected",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void saveFood() {
        String restaurantId = mAuth.getCurrentUser().getUid();
        String foodName = editTextName.getText().toString();
        String foodDescription = editTextDescription.getText().toString();
        String foodImage = editTextImage.getText().toString();
      //  String foodCategory = editTextCategory.getText().toString();
  //      String foodCategory = spinnerCategory.getSelectedItem().toString();
        String foodCategory = String.valueOf(spinnerCategory.getSelectedItemPosition());
        long price= Long.parseLong(editTextPrice.getText().toString());

        if (foodName.trim().isEmpty()  || foodCategory.trim().isEmpty() || price==0) {
            Toast.makeText(this, "Please insert all info", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, Object> food = new HashMap<>();
        food.put("name",foodName);
        food.put("description",foodDescription);
        food.put("image",foodImage);
        food.put("categories",foodCategory);
        food.put("restaurantId", restaurantId);
        food.put("unitPrice", price);
        foodRef.add(food);
        Toast.makeText(this, "Food added added", Toast.LENGTH_SHORT).show();

        finish();
    }

}