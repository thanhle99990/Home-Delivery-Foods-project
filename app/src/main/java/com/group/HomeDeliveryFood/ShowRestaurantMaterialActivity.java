package com.group.HomeDeliveryFood;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.group.HomeDeliveryFood.Adapter.RecyclerMaterialAdapter;
import com.group.HomeDeliveryFood.Entity.Material;

import java.util.ArrayList;

public class ShowRestaurantMaterialActivity extends AppCompatActivity implements RecyclerMaterialAdapter.OnClickItemListener{
    private static final String TAG = "MainActivity";

    //vars
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference materialRef = db.collection("Materials");

    private RecyclerMaterialAdapter adapter;
   // private ArrayList<Material> materials = new ArrayList<>();

    FloatingActionButton addBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_restaurant_material_activity);


     //   initRecyclerView();
        addBtn=findViewById(R.id.button_add_item);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowRestaurantMaterialActivity.this,AddMaterialActivity.class);
                startActivity(intent);

            }
        });

        materialRef.document(mAuth.getCurrentUser().getUid()).collection("materialList").addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<Material> materials = new ArrayList<>();
                for(DocumentSnapshot documentSnapshot : value){
                    String material_id = documentSnapshot.getId();
                    String material_name = documentSnapshot.getString("name");
                    String material_supplier = documentSnapshot.getString("supplier");
                    String material_unit = documentSnapshot.getString("unit");
                    Long material_quantity = documentSnapshot.getLong("quantity");
                    Long material_unitPrice = documentSnapshot.getLong("unitPrice");
                    String material_image = documentSnapshot.getString("image");
                    Material material = new Material(material_id,material_name,material_supplier,material_unitPrice,material_image,material_quantity,material_unit);
                    materials.add(material);
                }
                initRecyclerView(materials);
            }
        });



    }

    private void initRecyclerView(ArrayList<Material> materials) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerMaterialAdapter(this,materials,this);
        recyclerView.setAdapter(adapter);
    }




    @Override
    public void onItemClick(int position,String status,String amount,String materialId) {
        if(status.equals("")){
            //Toast.makeText(this,amount,Toast.LENGTH_LONG).show();
            Intent intent=new Intent(this,UpdateMaterialActivity.class);
            intent.putExtra("materialId",materialId);
            startActivity(intent);

        }else{
            MiniMaterialSheet bottomSheet = new MiniMaterialSheet(position,status,amount,materialId);
            bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_menu , menu);

        MenuItem item = menu.findItem(R.id.search_bar);

        SearchView searchView = (SearchView)item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(ShowRestaurantMaterialActivity.this, MainActivity.class));
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}

