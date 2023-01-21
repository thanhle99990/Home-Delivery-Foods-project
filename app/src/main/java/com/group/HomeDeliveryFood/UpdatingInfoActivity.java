package com.group.HomeDeliveryFood;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdatingInfoActivity extends AppCompatActivity {

    private EditText fullname1,email;
    private TextView fullname2,phoneNo;

    private ImageView profileImage;
    private Button updateBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updating_info);


        fullname1 = findViewById(R.id.edit_text_fullname);
        fullname2 = findViewById(R.id.full_name_text);
        email = findViewById(R.id.edit_text_email);
        phoneNo = findViewById(R.id.text_view_phoneNo);
        profileImage = findViewById(R.id.profile_image);
        updateBtn = findViewById(R.id.update_button);

        mAuth = FirebaseAuth.getInstance();


        // info display
        DocumentReference userRef = FirebaseFirestore.getInstance().collection("User").document(mAuth.getCurrentUser().getUid());
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String name_txt = documentSnapshot.getString("name");
                    String email_txt = documentSnapshot.getString("email");
                    String phoneNo_txt = documentSnapshot.getString("phoneNo");
                    String image_txt = documentSnapshot.getString("image");
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

                }
                else{
                    Toast.makeText(UpdatingInfoActivity.this, "No account", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdatingInfoActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });


        //
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname1_txt = fullname1.getText().toString();
                String email_txt = email.getText().toString();

                if(isEmailValid(email_txt)) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    DocumentReference userRef = FirebaseFirestore.getInstance().collection("User").document(mAuth.getCurrentUser().getUid());
                    HashMap<String, Object> userUpdate = new HashMap<>();
                    userUpdate.put("name", fullname1_txt);
                    userUpdate.put("email", email_txt);
                    userRef.update(userUpdate);
                    Toast.makeText(UpdatingInfoActivity.this, "Info updated", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(UpdatingInfoActivity.this, ShowInfoActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(UpdatingInfoActivity.this, "Email is invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(UpdatingInfoActivity.this, ShowInfoActivity.class));
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}