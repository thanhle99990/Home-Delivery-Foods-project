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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    private EditText phoneNo;
    private EditText password;
    private Button loginBtn;

    private FirebaseAuth mAuth;
    private String verificationCodeBySystem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // define id
        phoneNo = findViewById(R.id.edit_text_phoneNo);
        password = findViewById(R.id.edit_text_password);
        loginBtn = findViewById(R.id.loginBtn);

        mAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String txt_phoneNo = phoneNo.getText().toString();
                final String txt_password = password.getText().toString();

                if(TextUtils.isEmpty(txt_phoneNo) && TextUtils.isEmpty(txt_password)){
                    Toast.makeText(LoginActivity.this ,"Please input your phone number and password", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(txt_password)){
                    Toast.makeText(LoginActivity.this, "Please input your password", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(txt_phoneNo)){
                    Toast.makeText(LoginActivity.this, "Please input your phone number", Toast.LENGTH_SHORT).show();
                }
                else if (txt_phoneNo.length() < 10 || txt_phoneNo.length() > 11){
                    Toast.makeText(LoginActivity.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                }
                else{
//                    LoginUser(txt_email,txt_password);
             //       nextActivity(txt_phoneNo, txt_password);
                    // check number phone exist
                    CollectionReference userRef = FirebaseFirestore.getInstance().collection("User");
                    Query queryPhoneNo = userRef.whereEqualTo("phoneNo",txt_phoneNo);
                    queryPhoneNo.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot documentSnapshot : task.getResult()){
                                    String phoneNoExist = documentSnapshot.getString("phoneNo");
                                    String password = documentSnapshot.getString("password");
                                    if(phoneNoExist.equals(txt_phoneNo)){
                                        if(txt_password.equals(password)){
                                            nextActivity(txt_phoneNo, txt_password);
                                        }
                                        else{
                                            Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_LONG).show();
                                        }
                                    }


                                }
                            }
                            if(task.getResult().size() == 0){
                                Toast.makeText(LoginActivity.this, "Phone number is not existed, please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }
            }
        });
    }
    private void nextActivity(String phoneNo, String password) {
        Intent intent = new Intent(getApplicationContext(), VerifyPhoneNo.class);
        intent.putExtra("password", password);
        intent.putExtra("phoneNo", phoneNo);
        startActivity(intent);
    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(LoginActivity.this, StartActivity.class));
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}