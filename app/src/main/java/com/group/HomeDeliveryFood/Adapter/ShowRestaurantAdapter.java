package com.group.HomeDeliveryFood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.group.HomeDeliveryFood.Entity.Restaurant;
import com.group.HomeDeliveryFood.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowRestaurantAdapter extends RecyclerView.Adapter<ShowRestaurantAdapter.ViewHolder>  {
    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<Restaurant> resCart = new ArrayList<>();
    private Context mContext;
    private OnClickItemListener onClickItemListener;

    public ShowRestaurantAdapter(Context context, ArrayList<Restaurant> resCart, OnClickItemListener onClickItemListener) {
        this.resCart=resCart;
        mContext = context;
        this.onClickItemListener=onClickItemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_restaurant_recycler_layout, parent, false);
        return new ViewHolder(view,onClickItemListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        Glide.with(mContext)
                .asBitmap()
                .load(resCart.get(position).getImage())
                .into(holder.image);

        holder.name.setText(resCart.get(position).getName());
        holder.address.setText(resCart.get(position).getAddress());
        holder.totalAmount.setText("Total amount: "+resCart.get(position).foodCart.m_value);


    }

    @Override
    public int getItemCount() {
        return resCart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CircleImageView image;
        TextView name,address,totalAmount;
        OnClickItemListener onClickItemListener;

        public ViewHolder(View itemView,OnClickItemListener onClickItemListener) {
            super(itemView);
            image = itemView.findViewById(R.id.image_view);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            totalAmount = itemView.findViewById(R.id.totalAmount);

            this.onClickItemListener=onClickItemListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickItemListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnClickItemListener{
        void onItemClick(int position);
    }
}
