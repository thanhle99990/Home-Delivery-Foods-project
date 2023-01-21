package com.group.HomeDeliveryFood.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group.HomeDeliveryFood.Entity.Material;
import com.group.HomeDeliveryFood.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerMaterialAdapter extends RecyclerView.Adapter<RecyclerMaterialAdapter.ViewHolder> implements Filterable {
    private static final String TAG = "RecyclerViewAdapter";

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference materialRef = db.collection("Materials");
    //vars
    private ArrayList<Material> materials = new ArrayList<>();
    private ArrayList<Material> Filtermaterials = new ArrayList<>();
    private Context mContext;
    private RecyclerMaterialAdapter.OnClickItemListener onClickItemListener;

    public RecyclerMaterialAdapter(Context context, ArrayList<Material> materials, RecyclerMaterialAdapter.OnClickItemListener onClickItemListener) {
        this.materials=materials;
        this.Filtermaterials=materials;
        mContext = context;
        this.onClickItemListener=onClickItemListener;
    }

    @Override
    public RecyclerMaterialAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_material_recycler, parent, false);
        return new RecyclerMaterialAdapter.ViewHolder(view,onClickItemListener);
    }

    @Override
    public void onBindViewHolder(RecyclerMaterialAdapter.ViewHolder holder, final int position) {


        Glide.with(mContext)
                .asBitmap()
                .load(materials.get(position).getImageUrl())
                .into(holder.image);

        holder.name.setText("Name: " + materials.get(position).getName());
        holder.supplier.setText("Supplier: " +materials.get(position).getSupplier());
        holder.totalAmount.setText("Quantity: " + String.valueOf((materials.get(position).getQuantity())));
        holder.unitPrice.setText("Price: " +String.valueOf(materials.get(position).getUnitPrice())+"/"+String.valueOf(materials.get(position).getUnit()));
        holder.unit.setText(String.valueOf(materials.get(position).getUnit()));
        if(materials.get(position).getQuantity() <= 6){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FF4C4C"));
        }
        else{
            holder.cardView.setCardBackgroundColor(Color.parseColor("#eff8ff"));
        }

    }

    @Override
    public int getItemCount() {
        return Filtermaterials.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charcater = constraint.toString();
                if (charcater.isEmpty()){
                    Filtermaterials = materials ;
                }else {
                    ArrayList<Material> filterList = new ArrayList<>();
                    for (Material row: materials){
                        if (row.getName().toLowerCase().contains(charcater.toLowerCase()) || row.getQuantity().toString().toLowerCase().contains(charcater.toLowerCase())){
                            filterList.add(row);
                        }
                    }

                    Filtermaterials = filterList ;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = Filtermaterials;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                Filtermaterials = (ArrayList<Material>) results.values ;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView name,supplier,totalAmount,unitPrice,unit;
        RecyclerMaterialAdapter.OnClickItemListener onClickItemListener;
        Button addBtn,minusBtn,deleteBtn, updateBtn;
        CardView cardView;

        public ViewHolder(final View itemView, final RecyclerMaterialAdapter.OnClickItemListener onClickItemListener) {
            super(itemView);
            this.onClickItemListener=onClickItemListener;
            cardView = itemView.findViewById(R.id.cardViewMain);
            image = itemView.findViewById(R.id.image_view);
            name = itemView.findViewById(R.id.name);
            supplier = itemView.findViewById(R.id.supplier);
            totalAmount = itemView.findViewById(R.id.totalAmount);
            unitPrice=itemView.findViewById(R.id.unitPrice);
            unit=itemView.findViewById(R.id.unit);

            addBtn=itemView.findViewById(R.id.addAmount);
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemListener.onItemClick(getAdapterPosition(),"add",String.valueOf(materials.get(getAdapterPosition()).getQuantity()),materials.get(getAdapterPosition()).getId());
                }
            });
            minusBtn=itemView.findViewById(R.id.minusAmount);
            minusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemListener.onItemClick(getAdapterPosition(),"minus",String.valueOf(materials.get(getAdapterPosition()).getQuantity()),materials.get(getAdapterPosition()).getId());
                }
            });
            deleteBtn=itemView.findViewById(R.id.delete);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    materialRef.document(mAuth.getCurrentUser().getUid()).collection("materialList").document(materials.get(getAdapterPosition()).getId()).delete();
                    Toast.makeText(mContext, materialRef.document(mAuth.getCurrentUser().getUid()).collection("materialList").document(materials.get(getAdapterPosition()).getName())+ " deleted", Toast.LENGTH_LONG).show();
                }
            });
            updateBtn = itemView.findViewById(R.id.button_update);
            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    onClickItemListener.onItemClick(getAdapterPosition(),"","",materials.get(getAdapterPosition()).getId());

                }
            });
        }

    }

    public interface OnClickItemListener{
        void onItemClick(int position,String status,String totalAmount,String materialId);
    }
}
