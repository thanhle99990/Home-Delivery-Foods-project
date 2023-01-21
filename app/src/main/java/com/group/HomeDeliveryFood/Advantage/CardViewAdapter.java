package com.group.HomeDeliveryFood.Advantage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group.HomeDeliveryFood.Entity.Food;
import com.group.HomeDeliveryFood.MainActivity;
import com.group.HomeDeliveryFood.R;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.CategoryItemViewHolder> {
    private Context context;
    static List<Food> ItemList;


    public CardViewAdapter(Context context, List<Food> categoryItemList) {
        this.context = context;
        this.ItemList = categoryItemList;

    }

    @NonNull
    @Override
    public CardViewAdapter.CategoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardViewAdapter.CategoryItemViewHolder(LayoutInflater.from(context).inflate(R.layout.card_view_recycer, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewAdapter.CategoryItemViewHolder holder, int position) {

        holder.name.setText(ItemList.get(position).getName());
        holder.quantity.setText(ItemList.get(position).getUnitPrice() + "");
    }

    public void remove(int adapterPosition,int resPosition) {
        ItemList.remove(adapterPosition);

        Set<Food> products = MainActivity.cart.get(resPosition).foodCart.getProducts();
        int i = 0;
        Iterator iterator = products.iterator();
        while (iterator.hasNext()) {
            Food product = (Food) iterator.next();
            if (i == adapterPosition) {
                MainActivity.cart.get(resPosition).foodCart.m_cart.remove(product);
                break;
            }
            i++;
        }
    }
    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public static final class CategoryItemViewHolder extends RecyclerView.ViewHolder {


        TextView name, quantity;


        public CategoryItemViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_name);
            quantity = itemView.findViewById(R.id.item_quantity);

        }
    }


}