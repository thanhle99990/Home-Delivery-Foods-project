package com.group.HomeDeliveryFood;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ShowInfoActivity extends AppCompatActivity {
    private TextView fullname1, fullname2, role, email, phoneNo ;

    private Button updateBtn, changePasswordBtn;

    private ImageView profileImage;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);

        // define id
        fullname1 = findViewById(R.id.text_view_name);
        fullname2 = findViewById(R.id.text_view_fullname);
        email = findViewById(R.id.text_view_mail);
        phoneNo = findViewById(R.id.text_view_phoneNo);

        updateBtn = findViewById(R.id.update_button);
        changePasswordBtn = findViewById(R.id.change_password_button);
        profileImage = findViewById(R.id.profile_image);
        mAuth = FirebaseAuth.getInstance();
        role = findViewById(R.id.role_text);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowInfoActivity.this, UpdatingInfoActivity.class));
                finish();
            }
        });

        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowInfoActivity.this, ChangePasswordActivity.class));
                finish();
            }
        });
        DocumentReference userRef = FirebaseFirestore.getInstance().collection("User").document(mAuth.getCurrentUser().getUid());
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String name_txt = documentSnapshot.getString("name");
                    String email_txt = documentSnapshot.getString("email");
                    String phoneNo_txt = documentSnapshot.getString("phoneNo");
                    String image_txt = documentSnapshot.getString("image");
                    String role_txt = documentSnapshot.getString("role");
                    fullname1.setText(name_txt);
                    fullname2.setText(name_txt);
                    email.setText(email_txt);
                    phoneNo.setText(phoneNo_txt);
//                    Glide.with(ShowInfoActivity.this)
//                            .load(image_txt)
//                            .into(profileImage);
                  //  Picasso.with(ShowInfoActivity.this).load(image_txt).fit().centerCrop().into(profileImage);
                    Glide.with(getApplicationContext())
                            .asBitmap()
                            .load(image_txt)
                            .into(profileImage);
                    
                    role.setText(role_txt);

            }
                else{
                    Toast.makeText(ShowInfoActivity.this, "No account", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ShowInfoActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list,menu);
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
                return true;
            case R.id.about:
                Intent intent = new Intent(this,ShowInfoActivity.class);
                startActivity(intent);
            case R.id.home:
                intent = new Intent(ShowInfoActivity.this,MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}