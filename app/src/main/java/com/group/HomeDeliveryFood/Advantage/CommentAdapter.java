package com.group.HomeDeliveryFood.Advantage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.group.HomeDeliveryFood.Entity.Comment;
import com.group.HomeDeliveryFood.R;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    //vars
    private List<Comment> comments;
    private Context mContext;


    public CommentAdapter(Context context, List<Comment> comments) {
        this.comments=comments;
        mContext = context;

    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_recycler_layout, parent, false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, final int position) {

        Glide.with(mContext)
                .asBitmap()
                .load(comments.get(position).getImageUrlUser())
                .into(holder.imgUser);
    //    Log.d("Dtb","Each comment name: "+comments.get(position).getRatingAmount());
        holder.nameUser.setText(comments.get(position).getNameUser());
        holder.timeUser.setText(comments.get(position).getTimeCmt().toString());
        holder.noteUser.setText(comments.get(position).getReview());
        holder.ratingBar.setRating(comments.get(position).getRatingAmount());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nameUser,timeUser,noteUser;
        ImageView imgUser;
        RatingBar ratingBar;
        public ViewHolder(View itemView) {
            super(itemView);
            nameUser = itemView.findViewById(R.id.nameUser);
            timeUser = itemView.findViewById(R.id.time);
            noteUser = itemView.findViewById(R.id.note);
            imgUser = itemView.findViewById(R.id.image_view);
            ratingBar=itemView.findViewById(R.id.ratingBar);

        }

    }
}