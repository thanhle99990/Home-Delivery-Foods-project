package com.group.HomeDeliveryFood;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UpdateFoodActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    private EditText editTextName;
    private EditText editTextDescription;
    private EditText editTextImage;
    private EditText editTextunitPrice;
    private EditText editTextCategory;
    private Spinner spinnerCategory;
    private Button updateBtn;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference foodRef = db.collection("Foods");
    private CollectionReference categoryRef = db.collection("Categories");


    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food);
        // define id
        editTextName= findViewById(R.id.edit_text_name);
        editTextDescription = findViewById(R.id.edit_text_description);
        editTextImage = findViewById(R.id.edit_text_image);
        editTextunitPrice = findViewById(R.id.edit_text_price);
       editTextCategory=findViewById(R.id.edit_text_category);
        spinnerCategory = findViewById(R.id.spinner_category);



        updateBtn = findViewById(R.id.button_save);

        final Intent intent=getIntent();
        id = intent.getStringExtra("foodId");
        ArrayList<String> itemCategory = new ArrayList<>();
        categoryRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<String> itemCategory = new ArrayList<>();
                for(DocumentSnapshot documentSnapshot : value){
                    String category_name = documentSnapshot.getString("name");
                    itemCategory.add(category_name);
                }
                spinnerCategory.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,itemCategory));
            }
        });

        foodRef.document(id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                editTextName.setText(value.getString("name"));
                editTextDescription.setText(value.getString("description"));
                editTextImage.setText(value.getString("image"));
                editTextunitPrice.setText(String.valueOf(value.getLong("unitPrice")));
                spinnerCategory.setSelection(Integer.parseInt(value.getString("categories")));
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodRef.document(id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        String txt_name = editTextName.getText().toString();
                        String txt_description = editTextDescription.getText().toString();
                        String txt_image = editTextImage.getText().toString();
                        String txt_unitPrice = editTextunitPrice.getText().toString();
                        foodRef.document(id).update("name",txt_name,
                                "description", txt_description,
                                "image",txt_image,
                                "unitPrice", Long.parseLong(txt_unitPrice),
                                "categories", String.valueOf(spinnerCategory.getSelectedItemPosition()));
                        Toast.makeText(getApplicationContext(),"Update successful", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(UpdateFoodActivity.this, Restaurant_Handler_Main.class));

                    }
                });
            }
        });


//
//        String unit;
//        materialRef.document(mAuth.getCurrentUser().getUid()).collection("materialList").document(id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                editTextName.setText(value.getString("name"));
//                editTextSupplier.setText(value.getString("supplier"));
//                editTextImage.setText(value.getString("image"));
//                editTextunitPrice.setText(String.valueOf(value.getLong("unitPrice")));
//                editTextQuantity.setText(String.valueOf(value.getLong("quantity")));
//                String unit = value.getString("unit");
////                for(int i = 0; i <= spinnerUnit.getScrollBarSize(); i++){
////                    if(itemUnit.get(i).equals(unit)){
////                        spinnerUnit.setSelection(i);
////                    }
////                }
//
//            }
//        });
//
//        updateBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String txt_name = editTextName.getText().toString();
//                String txt_supplier = editTextSupplier.getText().toString();
//                String txt_image = editTextImage.getText().toString();
//                String txt_quantity = editTextQuantity.getText().toString();
//                String txt_unitPrice = editTextunitPrice.getText().toString();
//                String txt_unit = spinnerUnit.getSelectedItem().toString();
//                materialRef.document(mAuth.getCurrentUser().getUid()).collection("materialList").document(intent.getStringExtra("materialId")).update("name",txt_name,
//                        "supplier", txt_supplier,
//                        "image",txt_image,
//                        "quantity",Long.parseLong(txt_quantity),
//                        "unitPrice", Long.parseLong(txt_unitPrice),
//                        "unit",txt_unit);
//                Toast.makeText(getApplicationContext(),"Update successful", Toast.LENGTH_LONG).show();
//                startActivity(new Intent(UpdateMaterialActivity.this, ShowRestaurantMaterialActivity.class));
//
//
//            }
//        });


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(UpdateFoodActivity.this, Restaurant_Handler_Main.class));
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}