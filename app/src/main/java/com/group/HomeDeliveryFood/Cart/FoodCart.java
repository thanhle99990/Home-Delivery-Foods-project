package com.group.HomeDeliveryFood.Cart;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.group.HomeDeliveryFood.Entity.Food;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class FoodCart {
    public Map<Food,Integer> m_cart;
    public Long m_value= Long.valueOf(0);

    public FoodCart()
    {
        m_cart=new LinkedHashMap<>();
    }

    public boolean check(Food product){
        Set<Food> products=getProducts();
        Iterator iterator = products.iterator();
        while(iterator.hasNext()){
            Food item =(Food) iterator.next();
            if(item.getName().equals(product.getName()) && item.getUnitPrice().equals(product.getUnitPrice())){
                return true;
            }
        }
        return false;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addToCart(Food product,int amount){
        if(check(product)){
            Log.d("Check2","Exist");
            Set<Food> products=getProducts();
            Iterator iterator = products.iterator();
            while(iterator.hasNext()){
                Food item =(Food) iterator.next();
                if(item.getName().equals(product.getName()) && item.getUnitPrice().equals(product.getUnitPrice())){
                    Integer a = m_cart.get(item);
                    Integer b= 20-a;
                    a+=amount;
                    if(a>20){
                        a=20;
                        m_value+=product.getUnitPrice()*b;
                    }else{
                        m_value+=product.getUnitPrice()*amount;
                    }
                    Log.d("Amount:",""+a);
                    m_cart.remove(item);
                    m_cart.put(item,20);
                    a = m_cart.get(item);
                    Log.d("Amount:",""+a);
                }
            }
        }else{
            Log.d("Check2","Not exist");
            m_cart.put(product,amount);
            m_value+=product.getUnitPrice()*amount;
        }

    }

    public int getQuantity(Food product){
        return m_cart.get(product);
    }
    public Set getProducts(){
        return m_cart.keySet();
    }

    public void emty(){
        m_cart.clear();
        m_value= Long.valueOf(0);
    }
    public Long getValue(){
        return m_value;
    }
    public int getSize(){
        return m_cart.size();
    }
}
