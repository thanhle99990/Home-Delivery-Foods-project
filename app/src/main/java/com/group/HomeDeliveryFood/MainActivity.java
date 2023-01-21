package com.group.HomeDeliveryFood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.group.HomeDeliveryFood.Entity.Restaurant;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView hello;

    private Button showInfoBtn;
    private Button logoutBtn;
    private Button homepageBtn;
    private Button materialBtn;
    private Button checkoutBtn;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef = db.collection("User");
    public static ArrayList<Restaurant> cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cart=new ArrayList<>();

        hello = findViewById(R.id.text_hello_user);
        logoutBtn = findViewById(R.id.logoutBtn);
        showInfoBtn = findViewById(R.id.showInfoBtn);
        homepageBtn = findViewById(R.id.homepageBtn);
        materialBtn = findViewById(R.id.materialBtn);
        checkoutBtn = findViewById(R.id.checkoutBtn);

        visibleButton();




        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Toast.makeText(MainActivity.this,"Logout successful",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,StartActivity.class));
                finish();
            }
        });

        showInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShowInfoActivity.class));
                finish();
            }
        });
        homepageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roleNextActivity();

            }
        });

        materialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShowRestaurantMaterialActivity.class));
                finish();
            }
        });
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BookOrdersActivity.class));
                finish();
            }
        });
//        CollectionReference userRef = FirebaseFirestore.getInstance().collection("User");
//        Query queryEmail = userRef.whereEqualTo("name", mAuth.getCurrentUser().getEmail().toString());
//        queryEmail.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
////                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
////                        String emailExist = documentSnapshot.getString("email");
////                        String name = documentSnapshot.getString("name");
////                        hello.setText("Hello, " + name);
////
////                    }
//                }
//            }
//        });
        DocumentReference userRef = FirebaseFirestore.getInstance().collection("User").document(mAuth.getCurrentUser().getUid());
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String name = documentSnapshot.getString("name");
                    hello.setText("Hello, " + name);
                }
                else{
                    Toast.makeText(MainActivity.this, "No account", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });


    }
    private void visibleButton(){
        String userId = mAuth.getCurrentUser().getUid();
        userRef.document(userId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()) {
                    String userRole = value.getString("role");
                    if (userRole.equals("Restaurant") || userRole.equals("restaurant")) {
                        homepageBtn.setText("Menu");

                    } else {
                        materialBtn.setVisibility(View.GONE);
                        checkoutBtn.setVisibility(View.GONE);
                    }
                }
                else{

                }
            }
        });
    }

    private void roleNextActivity() {
        String userId = mAuth.getCurrentUser().getUid();
        userRef.document(userId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()) {
                    String userRole = value.getString("role");
                    if (userRole.equals("Restaurant") || userRole.equals("restaurant")) {
                        startActivity(new Intent(MainActivity.this, Restaurant_Handler_Main.class));
                        finish();
                    } else {
                        startActivity(new Intent(MainActivity.this, HomePageActivity.class));
                        finish();
                    }
                }
                else{
                    startActivity(new Intent(MainActivity.this, StartActivity.class));
                    finish();
                }
            }
        });

    }

}
