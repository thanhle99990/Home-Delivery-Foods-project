package com.group.HomeDeliveryFood;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.group.HomeDeliveryFood.Entity.User;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText currentPassword, newPassword, confirmPassword;

    private TextView fullname, phoneNo;
    private ImageView profileImage;
    private Button changePasswordBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        // define id
        currentPassword = findViewById(R.id.edit_text_currentPassword);
        newPassword = findViewById(R.id.edit_text_newPassword);
        confirmPassword = findViewById(R.id.edit_text_confirmPassword);
        profileImage = findViewById(R.id.profile_image);

        fullname = findViewById(R.id.full_name_text);
        phoneNo = findViewById(R.id.text_view_phoneNo);

        changePasswordBtn = findViewById(R.id.change_password_button);

        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);

        // display info
        DocumentReference userRef = FirebaseFirestore.getInstance().collection("User").document(mAuth.getCurrentUser().getUid());
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String name_txt = documentSnapshot.getString("name");
                    String phoneNo_txt = documentSnapshot.getString("phoneNo");
                    String image_txt = documentSnapshot.getString("image");
                    fullname.setText(name_txt);
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
                    Toast.makeText(ChangePasswordActivity.this, "No account", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ChangePasswordActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        // Button listener
        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String currentPassword_txt = currentPassword.getText().toString();
                final String newPassword_txt = newPassword.getText().toString();
                final String confirmPassword_txt = confirmPassword.getText().toString();


            //    DocumentReference userRef = FirebaseFirestore.getInstance().collection("User").document(mAuth.getCurrentUser().getUid());

                if(TextUtils.isEmpty(currentPassword_txt) || TextUtils.isEmpty(currentPassword_txt) || TextUtils.isEmpty(currentPassword_txt)){
                    Toast.makeText(ChangePasswordActivity.this, "Please fill in the inputs", Toast.LENGTH_LONG).show();
                }
                else if(confirmPassword_txt.equals(newPassword_txt) == false){
                    Toast.makeText(ChangePasswordActivity.this, "Your confirm password is wrong, please try again!", Toast.LENGTH_LONG).show();
                }
                else{
                    final FirebaseUser user = mAuth.getCurrentUser();
                    final DocumentReference userRef = FirebaseFirestore.getInstance().collection("User").document(mAuth.getCurrentUser().getUid());
                    userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            User userObject = documentSnapshot.toObject(User.class);
                            if(userObject.getPassword().equals(currentPassword_txt)){
                                // update pass in the firebase auth
                                user.updatePassword(newPassword_txt);

                                // update pass in firecloud
                                Map<String, Object> userMap = new HashMap<>();
                                userMap.put("password",newPassword_txt);
                                userRef.update(userMap);
                                Toast.makeText(ChangePasswordActivity.this, "Password changed", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ChangePasswordActivity.this, ShowInfoActivity.class));
                                finish();
                            }
                            else{
                                Toast.makeText(ChangePasswordActivity.this, "Wrong password", Toast.LENGTH_LONG).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ChangePasswordActivity.this, "Failure", Toast.LENGTH_LONG).show();
                        }
                    });


                }


            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(ChangePasswordActivity.this, ShowInfoActivity.class));
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}