package com.group.HomeDeliveryFood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class Infor_Food extends AppCompatActivity {

    ImageView img;
    TextView proName, proPrice, proDesc;
    Button buyBtn;
    String id,name, desc, image,nameRestaurant,addressRestaurant,urlRestaurant,idRestaurant;
    Long price;
 //   int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor__food);

        Intent i = getIntent();
        name = i.getStringExtra("name");
        image=i.getStringExtra("imageUrl");
        price = i.getLongExtra("price",0);
        desc = i.getStringExtra("description");
        id=i.getStringExtra("id");

        nameRestaurant=i.getStringExtra("nameRestaurant");
        addressRestaurant=i.getStringExtra("addressRestaurant");
        urlRestaurant=i.getStringExtra("urlRestaurant");
        idRestaurant=i.getStringExtra("idRestaurant");

        proName = findViewById(R.id.productName);
        proDesc = findViewById(R.id.prodDesc);
        proPrice = findViewById(R.id.prodPrice);
        img = findViewById(R.id.big_image);
        buyBtn=findViewById(R.id.button);

        proName.setText(name);
        proPrice.setText(price+"");
        proDesc.setText(desc);
        Glide.with(this)
                .asBitmap()
                .load(image)
                .into(img);
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MiniFoodSheet bottomSheet = new MiniFoodSheet(idRestaurant,nameRestaurant,addressRestaurant,urlRestaurant,id,name, price,desc,image);
                bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
            }
        });

    }
}