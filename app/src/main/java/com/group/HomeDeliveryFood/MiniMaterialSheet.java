package com.group.HomeDeliveryFood;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MiniMaterialSheet extends BottomSheetDialogFragment {

    int position;
    String status,totalAmount,materialId;
    static String amount;
    EditText amountText;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference materialRef = db.collection("Materials");

    MiniMaterialSheet(int position,String status,String totalAmount,String materialId){
        this.position=position;
        this.status=status;
        this.totalAmount=totalAmount;
        this.materialId=materialId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_material, container, false);
      //  Toast.makeText(getContext(),materialId,Toast.LENGTH_LONG).show();
        Button button1 = v.findViewById(R.id.change_button);

        amountText = v.findViewById(R.id.amount);


        button1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String amount_txt = amountText.getText().toString();
                if(amount_txt.equals("")){
                    Toast.makeText(getContext(),"Give me an amount",Toast.LENGTH_LONG).show();

                }else{
                    switch (status){
                        case "add":
                            materialRef.document(mAuth.getCurrentUser().getUid()).collection("materialList").document(materialId).update("quantity", Long.parseLong(amount_txt));
                            Toast.makeText(getContext(),"Update successful", Toast.LENGTH_LONG).show();
                            break;
                        case "minus":
                            materialRef.document(mAuth.getCurrentUser().getUid()).collection("materialList").document(materialId).update("quantity", Long.parseLong(amount_txt));
                            Toast.makeText(getContext(),"Update successful", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }
        });

        return v;
    }


}
