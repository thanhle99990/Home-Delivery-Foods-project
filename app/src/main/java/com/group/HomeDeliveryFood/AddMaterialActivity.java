package com.group.HomeDeliveryFood;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class AddMaterialActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference foodRef = db.collection("Foods");
    private CollectionReference categoryRef = db.collection("Categories");
    private CollectionReference materialRef = db.collection("Materials");

    private EditText editTextName;
    private EditText editTextSupplier;
    private EditText editTextImage;
    private EditText editTextunitPrice;
    private EditText editTextQuantity;
    private Spinner spinnerUnit;
    private Button saveBtn,updateBtn;

    private String restaurantId;


    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material);
        setTitle("Add Item");
        editTextName= findViewById(R.id.edit_text_name);
        editTextSupplier = findViewById(R.id.edit_text_supplier);
        editTextImage = findViewById(R.id.edit_text_image);
        editTextunitPrice = findViewById(R.id.edit_text_unitPrice);
        editTextQuantity=findViewById(R.id.edit_text_quantity);
        spinnerUnit=findViewById(R.id.spinner_unit);

        ArrayList<String> itemUnit = new ArrayList<>();
        itemUnit.add("kg");
    //    itemUnit.add("g");
        itemUnit.add("unit");
        itemUnit.add("piece");

        spinnerUnit.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,itemUnit));

        //     restaurantId = mAuth.getCurrentUser().getUid();

        saveBtn=findViewById(R.id.button_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                saveMaterial();
            }
        });
//        updateBtn=findViewById(R.id.button_update);
//        updateBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateMaterial();
//            }
//        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.restaurant_handler,menu);
        return true;
    }

    private void saveMaterial() {
        String restaurantId = mAuth.getCurrentUser().getUid();
        String unitPrice = editTextunitPrice.getText().toString();
        String materialName=editTextName.getText().toString();
        String materialSupplier=editTextSupplier.getText().toString();
       // String materialRequiredAmount=editTextRequiredAmount.getText().toString();
        String materialQuantity=editTextQuantity.getText().toString();
        String materialImage=editTextImage.getText().toString();
        String materialUnit = spinnerUnit.getSelectedItem().toString();

        if (materialName.trim().isEmpty()  || materialQuantity.trim().isEmpty()) {
            Toast.makeText(this, "Please insert all info", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, Object> material = new HashMap<>();
        material.put("name",materialName);
        material.put("unit",materialUnit);
        material.put("image",materialImage);
        material.put("supplier",materialSupplier);
        material.put("quantity", Long.parseLong(materialQuantity));
        material.put("unitPrice", Long.parseLong((unitPrice)));
        materialRef.document(restaurantId).collection("materialList").add(material);
        Toast.makeText(this, "Material added", Toast.LENGTH_SHORT).show();

        finish();
    }
    private void updateMaterial(){

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(AddMaterialActivity.this, ShowRestaurantMaterialActivity.class));
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
