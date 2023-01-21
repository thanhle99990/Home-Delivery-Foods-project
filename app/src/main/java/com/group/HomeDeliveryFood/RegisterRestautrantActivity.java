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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterRestautrantActivity extends AppCompatActivity {

 //   private EditText username;
    private EditText password;
    private EditText confirmPassword;
    private EditText email;
    private EditText name;
    private EditText phoneNumber;

    private Button registerBtn;
    private Button clearBtn;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // define id
    //    username = findViewById(R.id.edit_text_username);
        password = findViewById(R.id.edit_text_password);
        confirmPassword = findViewById(R.id.edit_text_confirm_password);
        email = findViewById(R.id.edit_text_email);
        phoneNumber = findViewById(R.id.edit_text_phone);
        name = findViewById(R.id.edit_text_name);

        registerBtn = findViewById(R.id.button_submit);
        clearBtn = findViewById(R.id.button_clear);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);

        //firebase auth define
//        auth =FirebaseAuth.getInstance();
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
       //         username.getText().clear();
                password.getText().clear();
                confirmPassword.getText().clear();
                email.getText().clear();
                phoneNumber.getText().clear();
                name.getText().clear();

            }
        });
        //Click Listener
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//

//                final String txt_username = username.getText().toString();
                final String txt_password = password.getText().toString();
                final String txt_confirmPassword = confirmPassword.getText().toString();
                final String txt_email = email.getText().toString();
                final String txt_phoneNumber = phoneNumber.getText().toString();
                final String txt_name = name.getText().toString();


                if(TextUtils.isEmpty(txt_name)|| TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_confirmPassword) || TextUtils.isEmpty(txt_phoneNumber)){
                    Toast.makeText(RegisterRestautrantActivity.this, "Empty, please input your info!", Toast.LENGTH_SHORT).show();
               //     password.setError("This field can not be blank");
                }

//                else if(txt_password.length() < 6){
//                    Toast.makeText(RegisterActivity.this, "Your password is too short", Toast.LENGTH_SHORT).show();
//                }
                else if (txt_phoneNumber.length() < 10 || txt_phoneNumber.length() > 11){
                    Toast.makeText(RegisterRestautrantActivity.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                }
                else if (txt_password.equals(txt_confirmPassword) == false){
                    Toast.makeText(RegisterRestautrantActivity.this, "Wrong confirm password", Toast.LENGTH_SHORT).show();
                }
                else if(!isEmailValid(txt_email)){
                    Toast.makeText(RegisterRestautrantActivity.this, "Email is invalid", Toast.LENGTH_SHORT).show();
                }
                else{
                    CollectionReference userRef = FirebaseFirestore.getInstance().collection("User");
////                    Query queryEmail = userRef.whereEqualTo("email", txt_email);
////                    queryEmail.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
////                        @Override
////                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
////                            if(task.isSuccessful()){
////                                for(DocumentSnapshot documentSnapshot : task.getResult()){
////                                    String emailExist = documentSnapshot.getString("email");
////                                    if(emailExist.equals(txt_email)){
////                                        Toast.makeText(RegisterActivity.this, "Account existed, please try again", Toast.LENGTH_SHORT).show();
////                                    }
////
////                                }
////                            }
////                            if(task.getResult().size() == 0){
////                                nextActivity(txt_email,txt_password,txt_name, txt_phoneNumber);
////                            }
////                        }
////                    });
//
//
                    // check number phone exist
                Query queryPhoneNo = userRef.whereEqualTo("phoneNo",txt_phoneNumber);
                queryPhoneNo.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot documentSnapshot : task.getResult()){
                                String phoneNoExist = documentSnapshot.getString("phoneNo");
                                if(phoneNoExist.equals(txt_phoneNumber)){
                                    Toast.makeText(RegisterRestautrantActivity.this, "Phone number existed, please try again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        if(task.getResult().size() == 0){
                             nextActivity(txt_email,txt_password,txt_name, txt_phoneNumber);

                        }
                    }
                });
            }
            }


        });
    }
    private void nextActivity( String email,String password, String name, String phoneNo) {
        Intent intent = new Intent(getApplicationContext(), VerifyPhoneNo.class);
        intent.putExtra("password", password);
        intent.putExtra("email", email);
        intent.putExtra("phoneNo", phoneNo);
        intent.putExtra("name", name);
        intent.putExtra("role", "Restaurant");
        startActivity(intent);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(RegisterRestautrantActivity.this, StartActivity.class));
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    boolean isEmailValid(CharSequence email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}