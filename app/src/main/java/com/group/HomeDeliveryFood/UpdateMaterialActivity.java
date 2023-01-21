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

import java.util.ArrayList;

public class UpdateMaterialActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    private EditText editTextName;
    private EditText editTextSupplier;
    private EditText editTextImage;
    private EditText editTextunitPrice;
    private EditText editTextQuantity;
    private Spinner spinnerUnit;
    private Button updateBtn;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference materialRef = db.collection("Materials");


    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_material);
        // define id
        editTextName= findViewById(R.id.edit_text_name);
        editTextSupplier = findViewById(R.id.edit_text_supplier);
        editTextImage = findViewById(R.id.edit_text_image);
        editTextunitPrice = findViewById(R.id.edit_text_unitPrice);
        editTextQuantity=findViewById(R.id.edit_text_quantity);
        spinnerUnit=findViewById(R.id.spinner_unit);
        final ArrayList<String> itemUnit = new ArrayList<>();
        itemUnit.add("kg");
        //    itemUnit.add("g");
        itemUnit.add("unit");
        itemUnit.add("piece");
        spinnerUnit.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,itemUnit));

        updateBtn = findViewById(R.id.button_update);

        final Intent intent=getIntent();
        id = intent.getStringExtra("materialId");
        Toast.makeText(this,id,Toast.LENGTH_LONG).show();

        String unit;
        materialRef.document(mAuth.getCurrentUser().getUid()).collection("materialList").document(id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                editTextName.setText(value.getString("name"));
                editTextSupplier.setText(value.getString("supplier"));
                editTextImage.setText(value.getString("image"));
                editTextunitPrice.setText(String.valueOf(value.getLong("unitPrice")));
                editTextQuantity.setText(String.valueOf(value.getLong("quantity")));
                String unit = value.getString("unit");
//                for(int i = 0; i <= spinnerUnit.getScrollBarSize(); i++){
//                    if(itemUnit.get(i).equals(unit)){
//                        spinnerUnit.setSelection(i);
//                    }
//                }

            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_name = editTextName.getText().toString();
                String txt_supplier = editTextSupplier.getText().toString();
                String txt_image = editTextImage.getText().toString();
                String txt_quantity = editTextQuantity.getText().toString();
                String txt_unitPrice = editTextunitPrice.getText().toString();
                String txt_unit = spinnerUnit.getSelectedItem().toString();
                materialRef.document(mAuth.getCurrentUser().getUid()).collection("materialList").document(intent.getStringExtra("materialId")).update("name",txt_name,
                        "supplier", txt_supplier,
                        "image",txt_image,
                        "quantity",Long.parseLong(txt_quantity),
                        "unitPrice", Long.parseLong(txt_unitPrice),
                        "unit",txt_unit);
                Toast.makeText(getApplicationContext(),"Update successful", Toast.LENGTH_LONG).show();
                startActivity(new Intent(UpdateMaterialActivity.this, ShowRestaurantMaterialActivity.class));


            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(UpdateMaterialActivity.this, ShowRestaurantMaterialActivity.class));
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}