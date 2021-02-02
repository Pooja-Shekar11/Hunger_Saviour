package com.example.hunger_saviour.RestaurantDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.hunger_saviour.Cart.CartActivity;
import com.example.hunger_saviour.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class FoodDetails4 extends AppCompatActivity {

    private ImageView imageView4;
    TextView name4, rate4;
    Button btnaddcart4;
    ElegantNumberButton numberButton;
    DatabaseReference ref4;
    String Foodkey4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details4);

        imageView4 = findViewById(R.id.single_foodimage4);
        name4= findViewById(R.id.single_foodname4);
        rate4 = findViewById(R.id.single_foodprice4);
        btnaddcart4 = findViewById(R.id.add_to_cart_button4);
        numberButton = findViewById(R.id.count);

        btnaddcart4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(FoodDetails2.this, "Your favourite dish is added to cart..", Toast.LENGTH_LONG).show();
                AddingToCartList();
            }
        });


        ref4 = FirebaseDatabase.getInstance().getReference("GetMelted").child("getmeltedmenus");
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Log.e("keyUser is null", "");
        } else {
            Foodkey4 = getIntent().getStringExtra("Foodkey4");
            ref4.child(Foodkey4).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String name = dataSnapshot.child("name").getValue().toString();
                        String rate = dataSnapshot.child("rate").getValue().toString();
                        String image = dataSnapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(imageView4);
                        name4.setText(name);
                        rate4.setText(rate);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        }
    }

    private void AddingToCartList() {
        String saveCurrentTime,saveCurrentDate;
        Calendar calForDate =  Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());
        final HashMap<String,Object> cartMap = new HashMap<>();
        cartMap.put("Foodkey",Foodkey4);
        cartMap.put("name",name4.getText().toString());
        cartMap.put("price",rate4.getText().toString());
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("quantity",numberButton.getNumber());
        cartMap.put("discount","");

        FirebaseDatabase.getInstance().getReference("CartList")
                .child("UserView").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Menus").child(Foodkey4)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            FirebaseDatabase.getInstance().getReference("CartList")
                                    .child("AdminView").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child("Menus").child(Foodkey4)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(FoodDetails4.this, "Added to Cart List", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(FoodDetails4.this,RestaurantFourMenus.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    public void Go_Back(View view) {
        startActivity(new Intent(getApplicationContext(), RestaurantFourMenus.class));
    }
    public void cart(View view) {
        startActivity(new Intent(getApplicationContext(), CartActivity.class));
    }

}
