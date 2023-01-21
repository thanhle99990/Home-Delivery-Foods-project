package com.group.HomeDeliveryFood;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VerifyPhoneNo extends AppCompatActivity {

    private String verificationCodeBySystem;
    private Button verifyBtn;
    private EditText phoneNoEnteredByTheUser;
    private ProgressBar progressBar;
    private TextView test;
    private String email;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference restaurantRef = db.collection("Restaurants");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_no);

        verifyBtn = findViewById(R.id.verifyBtn);
        phoneNoEnteredByTheUser = findViewById(R.id.verificationCodeEnteredByUser);
        progressBar = findViewById(R.id.progressBar);
        test = findViewById(R.id.test);

        progressBar.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();

        email =  getIntent().getStringExtra("email");

        String phoneNo = getIntent().getStringExtra("phoneNo");
        sendVerificationCodeToUser(phoneNo);

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = phoneNoEnteredByTheUser.getText().toString();
                if(code.isEmpty() || code.length() < 6){
                    phoneNoEnteredByTheUser.setError("Wrong OTP...");
                    phoneNoEnteredByTheUser.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
//                String name = getIntent().getStringExtra("name");
//                String password = getIntent().getStringExtra("password");
//                String email = getIntent().getStringExtra("email");
//                String phoneNo = getIntent().getStringExtra("phoneNo");
//                saveUser(email, password, name, phoneNo);


            }
        });

    }


    private void sendVerificationCodeToUser(String phoneNo) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84" + phoneNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCodeBySystem = s;
        }


        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerifyPhoneNo.this, "Error", Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String verificationCodeByUser) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, verificationCodeByUser);

        if(email == null){
            signInWithPhoneAuthCredential(credential);
        }
        else{
            registerTheUserByCredentials(credential);
        }



    }

    private void registerTheUserByCredentials(PhoneAuthCredential credential) {

        final String name = getIntent().getStringExtra("name");
        final String password = getIntent().getStringExtra("password");
        final String email = getIntent().getStringExtra("email");
        final String phoneNo = getIntent().getStringExtra("phoneNo");
        final String role = getIntent().getStringExtra("role");
        final String defaultImage = "data:image/jpeg;base64,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";
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(VerifyPhoneNo.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if(user != null){
                        String uid = user.getUid();
                        test.setText(uid);
                        Map<String, Object> newUser = new HashMap<>();
                        newUser.put("email", email);
                        newUser.put("name", name);
                        newUser.put("password", password);
                        newUser.put("phoneNo", phoneNo);
                        newUser.put("image", defaultImage);
                        newUser.put("role",role);

                        CollectionReference userRef = FirebaseFirestore.getInstance().collection("User");
                        userRef.document(uid).set(newUser);

  //                      newUser.put("role", "Restaurant");
                        Toast.makeText(VerifyPhoneNo.this, "Success", Toast.LENGTH_SHORT).show();
                        if(role.equals("Restaurant")){
                            startActivity(new Intent(VerifyPhoneNo.this, InfoInputRestaurant.class));
                            finish();

                        }
                        else{
                            startActivity(new Intent(VerifyPhoneNo.this, MainActivity.class));
                            finish();
                        }
//                        Toast.makeText(VerifyPhoneNo.this, "Success", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(VerifyPhoneNo.this, "No account", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(VerifyPhoneNo.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(VerifyPhoneNo.this, "Login success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(VerifyPhoneNo.this, MainActivity.class));
                            finish();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(VerifyPhoneNo.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(email == null){
            //        signInWithPhoneAuthCredential(credential);

                    startActivity(new Intent(VerifyPhoneNo.this, LoginActivity.class));
                    finish();
                }
                else{
                    String role = getIntent().getStringExtra("role");
                    if(role.equals("Restaurant")){
                        startActivity(new Intent(VerifyPhoneNo.this, RegisterRestautrantActivity.class));
                        finish();
                    }
                    else if(role.equals("Customer")){
                        startActivity(new Intent(VerifyPhoneNo.this, RegisterActivity.class));
                        finish();
                    }
                    else{
                        startActivity(new Intent(VerifyPhoneNo.this, StartActivity.class));
                        finish();
                    }

                }


            default:
                return super.onOptionsItemSelected(item);
        }

    }



}

