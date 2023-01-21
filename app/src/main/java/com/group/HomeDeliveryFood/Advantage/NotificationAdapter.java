package com.group.HomeDeliveryFood.Advantage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.group.HomeDeliveryFood.R;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<Notification> notifications;
    private Context mContext;


    public NotificationAdapter(Context context, ArrayList<Notification> notifications) {
        this.notifications=notifications;
        mContext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_adapter_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.name.setText(notifications.get(position).getNote());
        holder.time.setText(notifications.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void remove(int adapterPosition) {
        //foodRef.document(notifications.get(adapterPosition).getNoteID()).delete();
        //Toast.makeText(mContext, notifications.get(adapterPosition).getNote() + " deleted", Toast.LENGTH_LONG).show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,time;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            time=itemView.findViewById(R.id.item_time);
        }

    }

}
