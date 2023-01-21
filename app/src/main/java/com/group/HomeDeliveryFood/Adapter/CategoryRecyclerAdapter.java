package com.group.HomeDeliveryFood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group.HomeDeliveryFood.Entity.Category;
import com.group.HomeDeliveryFood.Entity.Restaurant;
import com.group.HomeDeliveryFood.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.MainViewHolder> implements Filterable {
    private Context context;
    private List<Category> allCategoryList;
    private List<Category> allCategoryListFilter;
    private RestaurantRecyclerAdapter.OnClickItemListener onClickItemListener;
    RestaurantRecyclerAdapter itemRecyclerAdapter;


    public CategoryRecyclerAdapter(Context context, List<Category> allCategoryList, RestaurantRecyclerAdapter.OnClickItemListener onClickItemListener) {

        this.context = context;
        this.allCategoryList = allCategoryList;
        this.allCategoryListFilter=allCategoryList;
        this.onClickItemListener=onClickItemListener;
    }


    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.category_recycler, parent, false),onClickItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {

        holder.categoryTitle.setText(allCategoryList.get(position).getCategoryTitle());
//        for(Restaurant res : allCategoryList.get(position).getCategoryItemList()){
//            Toast.makeText(context, allCategoryList.get(position).getCategoryTitle() + " " + res.getCategoryId() + " " + res.getName(), Toast.LENGTH_LONG).show();
//
//        }
        Category cat = allCategoryList.get(position);
 //       Toast.makeText(context, "Category adapter " + cat.getCategoryTitle() , Toast.LENGTH_LONG).show();
     //   setCatItemRecycler(holder.itemRecycler, allCategoryList.get(position).getCategoryItemList());
//        for(Restaurant res : allCategoryList.get(position).getCategoryItemList()){
//            Toast.makeText(context, cat.getCategoryTitle()+  " list  " + res.getName(), Toast.LENGTH_LONG).show();
//        }

        itemRecyclerAdapter = new RestaurantRecyclerAdapter(context, allCategoryList.get(position).getCategoryItemList(),onClickItemListener);
        holder.itemRecycler.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        holder.itemRecycler.setAdapter(itemRecyclerAdapter);



    }

    @Override
    public int getItemCount() {
        return allCategoryListFilter.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String character = constraint.toString().trim();
                Toast.makeText(context,character+"",Toast.LENGTH_LONG).show();
                if (character.isEmpty()){
                    allCategoryListFilter = allCategoryList ;

                }else {
                    List<Category> filterList = new ArrayList<>();
                    int i=0;
                    for (Category row: allCategoryList){
                        List<Restaurant> restaurant = new ArrayList<>();
                        for(Restaurant columm : row.getCategoryItemList()){
                            if(columm.getName().toLowerCase().contains(character.toLowerCase()) || columm.getAddress().toLowerCase().contains(character.toLowerCase()) ){
                                Toast.makeText(context,columm.getName().toLowerCase() + " " + character.toLowerCase(),Toast.LENGTH_LONG).show();
                                restaurant.add(columm);
                            }
                            //Log.d("Check:",i+ "Res size:"+restaurant.size());
                        }
                        if(restaurant.size()!=0) filterList.add(new Category("1",restaurant));
                    }
                    Toast.makeText(context,filterList.size()+"",Toast.LENGTH_LONG).show();
                    allCategoryListFilter = filterList ;


                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = allCategoryListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                allCategoryListFilter = (ArrayList<Category>) results.values ;
                notifyDataSetChanged();
            }
        };
    }


    public static final class MainViewHolder extends RecyclerView.ViewHolder{

        TextView categoryTitle;
        RecyclerView itemRecycler;

        public MainViewHolder(@NonNull View itemView, RestaurantRecyclerAdapter.OnClickItemListener onClickItemListener) {
            super(itemView);

            categoryTitle = itemView.findViewById(R.id.cat_title);
            itemRecycler = itemView.findViewById(R.id.item_recycler);

        }
    }

    private void setCatItemRecycler(RecyclerView recyclerView, List<Restaurant> restaurantList){

        RestaurantRecyclerAdapter itemRecyclerAdapter = new RestaurantRecyclerAdapter(context, restaurantList,onClickItemListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(itemRecyclerAdapter);
    }
}

