package com.group.HomeDeliveryFood.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.group.HomeDeliveryFood.Entity.Order;
import com.group.HomeDeliveryFood.R;

import java.util.ArrayList;

public class BookOrdersRecycler extends RecyclerView.Adapter<BookOrdersRecycler.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<Order> orders = new ArrayList<>();
    private Context mContext;
    private BookOrdersRecycler.OnClickItemListener onClickItemListener;

    public BookOrdersRecycler(Context context, ArrayList<Order> orders, BookOrdersRecycler.OnClickItemListener onClickItemListener) {
        this.orders=orders;
        mContext = context;
        this.onClickItemListener=onClickItemListener;
    }

    @Override
    public BookOrdersRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_orders_recycler, parent, false);
        return new BookOrdersRecycler.ViewHolder(view,onClickItemListener);
    }

    @Override
    public void onBindViewHolder(BookOrdersRecycler.ViewHolder holder, final int position) {
        holder.id.setText("ID:" + orders.get(position).getIdOrder());
        holder.name.setText("Customer: " + orders.get(position).getCustomerName());
        holder.address.setText("Address: " + orders.get(position).getAddressCustomer());
        holder.phone.setText("Phone number: " + orders.get(position).getPhoneCustomer());
        holder.date.setText("Order time: " + orders.get(position).getDate().toString());
        holder.totalBill.setText("Total: " + Long.toString(orders.get(position).getTotalBill()) + " VND");

        if(orders.get(position).getOrderStatus().equals("confirmed")){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#a6a9b6"));
        }
        else if(orders.get(position).getOrderStatus().equals("waiting")){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#eff8ff"));
        }else{
            holder.cardView.setCardBackgroundColor(Color.parseColor("#a6a9b6"));
        }

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        TextView name,address,totalBill,phone,id,date;
        BookOrdersRecycler.OnClickItemListener onClickItemListener;
        CardView cardView;
        public ViewHolder(View itemView, BookOrdersRecycler.OnClickItemListener onClickItemListener) {
            super(itemView);


            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            totalBill = itemView.findViewById(R.id.billTotal);
            phone=itemView.findViewById(R.id.phone);
            id=itemView.findViewById(R.id.idOrder);
            date=itemView.findViewById(R.id.date);

            cardView =itemView.findViewById(R.id.cardViewMain);

            this.onClickItemListener=onClickItemListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickItemListener.onItemClick(orders.get(getAdapterPosition()));
        }
    }

    public interface OnClickItemListener{
        void onItemClick(Order order);
    }
}
