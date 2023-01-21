package com.group.HomeDeliveryFood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.group.HomeDeliveryFood.Entity.Food;
import com.group.HomeDeliveryFood.R;

import java.util.List;

public class FoodRecyclerAdapter extends RecyclerView.Adapter<FoodRecyclerAdapter.CategoryItemViewHolder>{
    private Context context;
    static List<Food> ItemList;
    private OnClickItemListener onClickItemListener;

    public FoodRecyclerAdapter(Context context, List<Food> categoryItemList, OnClickItemListener onClickItemListener) {
        this.context = context;
        this.ItemList = categoryItemList;
        this.onClickItemListener=onClickItemListener;
    }

    @NonNull
    @Override
    public CategoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryItemViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_food_recycler, parent, false),onClickItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItemViewHolder holder, int position) {

        Glide.with(context)
                .asBitmap()
                .load(ItemList.get(position).getImage())
                .into(holder.itemImage);
        holder.name.setText(ItemList.get(position).getName());
        holder.description.setText(ItemList.get(position).getUnitPrice()+"");
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public static final class CategoryItemViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        Button add_button,view_button;
        TextView name,description;
        OnClickItemListener onClickItemListener;

        public CategoryItemViewHolder(@NonNull View itemView, final OnClickItemListener onClickItemListener) {
            super(itemView);

            this.onClickItemListener=onClickItemListener;

            itemImage = itemView.findViewById(R.id.item_image);
            name=itemView.findViewById(R.id.item_name);
            description=itemView.findViewById(R.id.item_description);
            add_button=itemView.findViewById(R.id.add_cart_button);
            view_button=itemView.findViewById(R.id.view_item_button);

            add_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemListener.onItemClick(
                            ItemList.get(getAdapterPosition()).getFoodId(),
                            ItemList.get(getAdapterPosition()).getName(),
                            ItemList.get(getAdapterPosition()).getUnitPrice(),
                            ItemList.get(getAdapterPosition()).getDescription(),
                            ItemList.get(getAdapterPosition()).getImage()
                    );
                }
            });
            view_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemListener.onViewItemClick(
                            ItemList.get(getAdapterPosition()).getFoodId(),
                            ItemList.get(getAdapterPosition()).getName(),
                            ItemList.get(getAdapterPosition()).getDescription()
                            ,ItemList.get(getAdapterPosition()).getUnitPrice()
                            ,ItemList.get(getAdapterPosition()).getImage()
                    );
                }
            });
        }
    }

    public interface OnClickItemListener{
        void onItemClick(String idItem, String nameItem,long price,String desItem,String imgItem);
        void onViewItemClick(String id, String name,String description,long price,String imageUrl);
    }
}
