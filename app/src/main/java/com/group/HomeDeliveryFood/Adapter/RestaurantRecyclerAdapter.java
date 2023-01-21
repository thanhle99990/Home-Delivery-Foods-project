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
import com.group.HomeDeliveryFood.Entity.Restaurant;
import com.group.HomeDeliveryFood.R;

import java.util.List;

public class RestaurantRecyclerAdapter extends RecyclerView.Adapter<RestaurantRecyclerAdapter.RestaurantViewHolder> {
    private Context context;
    private List<Restaurant> ItemList;
    private OnClickItemListener onClickItemListener;

    public RestaurantRecyclerAdapter(Context context, List<Restaurant> categoryItemList,OnClickItemListener onClickItemListener) {
        this.context = context;
        this.ItemList = categoryItemList;
        this.onClickItemListener=onClickItemListener;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RestaurantViewHolder(LayoutInflater.from(context).inflate(R.layout.restaurant_recycler, parent, false),onClickItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {

        Glide.with(context)
                .asBitmap()
                .load(ItemList.get(position).getImage())
                .into(holder.itemImage);
        holder.name.setText(ItemList.get(position).getName());
        holder.description.setText(ItemList.get(position).getAddress());
        Restaurant res = ItemList.get(position);
 //       Toast.makeText(context, "Restaurant adapter " + res.getCategoryId() + " " + res.getName(), Toast.LENGTH_LONG).show();

    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public final class RestaurantViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        Button view_button;
        TextView name,description;
        OnClickItemListener onClickItemListener;

        public RestaurantViewHolder(@NonNull View itemView, final OnClickItemListener onClickItemListener) {
            super(itemView);

            this.onClickItemListener=onClickItemListener;

            itemImage = itemView.findViewById(R.id.item_image);
            name=itemView.findViewById(R.id.item_name);
            description=itemView.findViewById(R.id.item_description);
            view_button=itemView.findViewById(R.id.view_restaurant_button);

            view_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemListener.onViewClick(
                   //         ItemList.get(getAdapterPosition()).getRestaurantId(),
                            ItemList.get(getAdapterPosition()).getName(),
                            ItemList.get(getAdapterPosition()).getAddress(),
                            ItemList.get(getAdapterPosition()).getImage(),
                            ItemList.get(getAdapterPosition()).getRestaurantId()

                    );
                }
            });

        }
    }

    public interface OnClickItemListener{
        void onViewClick(String nameItem,String desItem,String imgItem, String restaurantId);
    }
}

