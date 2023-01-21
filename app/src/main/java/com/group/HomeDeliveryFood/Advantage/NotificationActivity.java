package com.group.HomeDeliveryFood.Advantage;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group.HomeDeliveryFood.HomePageActivity;
import com.group.HomeDeliveryFood.R;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    NotificationAdapter adapter;
    //vars
    private ArrayList<Notification> notifications = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motification);

    }

    private void initRecyclerView(){


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NotificationAdapter(this,notifications);
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                        foods.remove(viewHolder.);
//                        adapter.notifyDataSetChanged();
//                Toast.makeText(Restaurant_Handler_Main.this, viewHolder.getAdapterPosition() + "", Toast.LENGTH_LONG).show();
                adapter.remove(viewHolder.getAdapterPosition());

            }
        }).attachToRecyclerView(recyclerView);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notification_delete,menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
//
            case R.id.delete:
                Intent intent = new Intent(this, HomePageActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


}