package com.example.hunger_saviour.RestaurantDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hunger_saviour.Cart.CartActivity;
import com.example.hunger_saviour.R;
import com.example.hunger_saviour.User.UserDashboard;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class RestaurantThreeMenus extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseRecyclerOptions<Menus> options;
    FirebaseRecyclerAdapter<Menus,MyViewHolder3> adapter;
    DatabaseReference DataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_three_menus);
        DataRef= FirebaseDatabase.getInstance().getReference("PizzaHut").child("pizzahutmenus");
        recyclerView=findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        LoadData();
    }

    private void LoadData() {
        options=new FirebaseRecyclerOptions.Builder<Menus>().setQuery(DataRef,Menus.class).build();
        adapter=new FirebaseRecyclerAdapter<Menus, MyViewHolder3>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder3 myViewHolder, int position, @NonNull Menus model) {
                myViewHolder.textView1.setText(model.getName());
                myViewHolder.textView2.setText("Rs. "+model.getRate());
                myViewHolder.textView3.setText(model.getDescription());
                Picasso.get().load(model.getImage()).into(myViewHolder.imageView1);
                myViewHolder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(RestaurantThreeMenus.this,FoodDetails3.class);
                        i.putExtra("Foodkey3",getRef(position).getKey());
                        startActivity(i);
                    }
                });
            }

            @NonNull
            @Override
            public MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.foodmenu_layout,parent,false);
                return new MyViewHolder3(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
    public void Go_Back(View view) {
        startActivity(new Intent(getApplicationContext(), UserDashboard.class));
    }

    public void cart(View view) {
        startActivity(new Intent(getApplicationContext(), CartActivity.class));
    }
}