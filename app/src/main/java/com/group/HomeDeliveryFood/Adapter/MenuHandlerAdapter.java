package com.group.HomeDeliveryFood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.group.HomeDeliveryFood.Entity.Food;
import com.group.HomeDeliveryFood.R;

import java.util.ArrayList;

public class MenuHandlerAdapter extends RecyclerView.Adapter<MenuHandlerAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<Food> foods;
    private Context mContext;
    private OnClickItemListener onClickItemListener;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference foodRef = db.collection("Foods");
    private CollectionReference categoryRef = db.collection("Categories");

    public MenuHandlerAdapter(Context context,ArrayList<Food> foods,OnClickItemListener onClickItemListener) {
        this.foods=foods;
        mContext = context;
        this.onClickItemListener=onClickItemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_handler_recycler_layout, parent, false);
        return new ViewHolder(view,onClickItemListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.name.setText(foods.get(position).getName());
        holder.des.setText(foods.get(position).getDescription());
        holder.number.setText(foods.get(position).getUnitPrice()+"");
        categoryRef.document(foods.get(position).getCategory()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                holder.category.setText(value.getString("name"));
            }
        });
        Glide.with(mContext)
                .asBitmap()
                .load(foods.get(position).getImage())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public void remove(int adapterPosition) {
        foodRef.document(foods.get(adapterPosition).getFoodId()).delete();
        Toast.makeText(mContext, foods.get(adapterPosition).getName() + " deleted", Toast.LENGTH_LONG).show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button updateBtn;
        ImageView image;
        TextView name,des,number,category;
        OnClickItemListener onClickItemListener;

        public ViewHolder(View itemView, final OnClickItemListener onClickItemListener) {
            super(itemView);
            this.onClickItemListener=onClickItemListener;
            name = itemView.findViewById(R.id.text_view_title);
            des = itemView.findViewById(R.id.text_view_description);
            number = itemView.findViewById(R.id.text_view_price);
            category = itemView.findViewById(R.id.text_view_category);
            image=itemView.findViewById(R.id.food_image);
            updateBtn = itemView.findViewById(R.id.button_update);

            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemListener.onItemClick(foods.get(getAdapterPosition()).getFoodId());
                }
            });
        }



    }
    public interface OnClickItemListener{
        void onItemClick(String foodId);
    }
}
