package com.group.HomeDeliveryFood;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.group.HomeDeliveryFood.Entity.Food;
import com.group.HomeDeliveryFood.Entity.Restaurant;

public class MiniFoodSheet extends BottomSheetDialogFragment {
    String nameRestaurant;
    String addressRestaurant;
    String imgRestaurant;
    String idRestaurant;

    String nameItem;
    String desItem;
    String imgItem;
    String idItem;
    long priceItem;

    static int amount;
    TextView amountText;

    MiniFoodSheet(String id,String name,String address,String imgRestaurant,String idItem,String nameItem,long priceItem,String desItem,String imgItem){
        this.idRestaurant=id;
        this.nameRestaurant=name;
        this.addressRestaurant=address;
        this.imgRestaurant=imgRestaurant;
        this.idItem=idItem;
        this.nameItem=nameItem;
        this.priceItem=priceItem;
        this.desItem=desItem;
        this.imgItem=imgItem;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout, container, false);

        Button button1 = v.findViewById(R.id.add_to_cart_button);

        Button addButton = v.findViewById(R.id.plus_amount_cart_item);

        Button minusButton = v.findViewById(R.id.minus_amount_cart_item);

        amount=1;
        amountText = v.findViewById(R.id.text_amount_cart_item);
        amountText.setText(amount+"");

        button1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Restaurant r = new Restaurant(idRestaurant,nameRestaurant,imgRestaurant,addressRestaurant,"1");
                Food f = new Food(idItem,nameItem,priceItem,"1",imgItem,"mlem mlem");
                if(checkExist() && MainActivity.cart.size()>0){
                    for(Restaurant res : MainActivity.cart){
                        if(res.getName().equals(nameRestaurant) && res.getAddress().equals(addressRestaurant)){
                            Toast.makeText(getContext(), Integer.parseInt(amountText.getText().toString())+ " " + f.getName() + " added",Toast.LENGTH_LONG).show();
                            res.foodCart.addToCart(f, Integer.parseInt(amountText.getText().toString()));
                        }
                    }
                }else{
                    r.foodCart.addToCart(f,Integer.parseInt(amountText.getText().toString()));
                    Toast.makeText(getContext(), Integer.parseInt(amountText.getText().toString())+ " " + f.getName() + " added",Toast.LENGTH_LONG).show();
                    MainActivity.cart.add(r);
                }
               // Toast.makeText(getContext(),"Size of cart "+MainActivity.cart.size(),Toast.LENGTH_LONG).show();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAmountValue("add");
            }
        });
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAmountValue("minus");
            }
        });
        return v;
    }
    public boolean checkExist(){
        if(MainActivity.cart.size() <= 0) return false;

        for(Restaurant r : MainActivity.cart){
            if(r.getName().equals(nameRestaurant) && r.getAddress().equals(addressRestaurant)) return true;
        }
        return false;

    }
    public void setAmountValue(String action){
        switch (action){
            case "add":
                amount+=1;
                if(amount>20) amount=20;
                amountText.setText(amount+"");
                break;
            case "minus":
                amount-=1;
                if (amount<1) amount=1;
                amountText.setText(amount+"");
                break;
        }

    }
}
