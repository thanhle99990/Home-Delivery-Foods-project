package com.group.HomeDeliveryFood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group.HomeDeliveryFood.Advantage.CommentAdapter;
import com.group.HomeDeliveryFood.Entity.Comment;
import com.group.HomeDeliveryFood.Entity.Food;
import com.group.HomeDeliveryFood.Entity.RestaurantSection;
import com.group.HomeDeliveryFood.R;

import java.util.List;

public class MenuRestaurantAdapter extends RecyclerView.Adapter<MenuRestaurantAdapter.MainViewHolder>{

    private Context context;
    private List<RestaurantSection> allSectionList;
    private List<Comment> comments;

    private FoodRecyclerAdapter.OnClickItemListener onClickItemListener;

    public MenuRestaurantAdapter(Context context, List<RestaurantSection> RestaurantSection, List<Comment> comments, FoodRecyclerAdapter.OnClickItemListener onClickItemListener) {
        this.context = context;
        this.allSectionList = RestaurantSection;
        this.comments=comments;
        this.onClickItemListener=onClickItemListener;

    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_menu_restaurant_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        //Log.d("Test size","Restaurant list: "+ allCategoryList.get(position).getName());

      //  Log.d("Database test","comment name: "+comments.get(position).getNameUser());
//
        if(position==allSectionList.size()){
//            holder.categoryTitle.setText(allCategoryList.get(position).getCategory());
//            setCatItemRecycler(holder.itemRecycler, allCategoryList);
            holder.categoryTitle.setText("Comments");
            setCatItemRecycler2(holder.itemRecycler, comments);

        }
        else{
            holder.categoryTitle.setText(allSectionList.get(position).getTitle());
            setCatItemRecycler(holder.itemRecycler, allSectionList.get(position).getCategoryItemList());
        }
//        setCatItemRecycler(holder.itemRecycler, allCategoryList);

//        holder.categoryTitle.setText(allCategoryList.get(position).getCategory());
   //     setCatItemRecycler(holder.itemRecycler, allCategoryList);

    }

    @Override
    public int getItemCount() {

        return allSectionList.size() + 1;
    }

    public static final class MainViewHolder extends RecyclerView.ViewHolder{

        TextView categoryTitle;
        RecyclerView itemRecycler;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryTitle = itemView.findViewById(R.id.cat_title);
            itemRecycler = itemView.findViewById(R.id.item_recycler);

        }
    }

    private void setCatItemRecycler(RecyclerView recyclerView, List<Food> categoryItemList){

        FoodRecyclerAdapter itemRecyclerAdapter = new FoodRecyclerAdapter(context, categoryItemList,onClickItemListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(itemRecyclerAdapter);
    }
    private void setCatItemRecycler2(RecyclerView recyclerView, List<Comment> comments){

        CommentAdapter itemRecyclerAdapter = new CommentAdapter(context, comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(itemRecyclerAdapter);
    }
}