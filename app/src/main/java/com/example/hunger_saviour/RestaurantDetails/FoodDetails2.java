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
import com.example.hunger_saviour.User.ConfirmFinalOrder;
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

public class FoodDetails2 extends AppCompatActivity {

    private ImageView imageView2;
    TextView name2, rate2;
    Button btnaddcart2;
    ElegantNumberButton numberButton;
    DatabaseReference ref2;
    String Foodkey2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details2);

        imageView2 = findViewById(R.id.single_foodimage2);
        name2 = findViewById(R.id.single_foodname2);
        rate2 = findViewById(R.id.single_foodprice2);
        btnaddcart2 = findViewById(R.id.add_to_cart_button2);
        numberButton = findViewById(R.id.count);

        btnaddcart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(FoodDetails2.this, "Your favourite dish is added to cart..", Toast.LENGTH_LONG).show();
                AddingToCartList();
            }
        });


        ref2 = FirebaseDatabase.getInstance().getReference("Dominos").child("dominosmenu");
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Log.e("keyUser is null", "");
        } else {
            Foodkey2 = getIntent().getStringExtra("Foodkey2");
            ref2.child(Foodkey2).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String name = dataSnapshot.child("name").getValue().toString();
                        String rate = dataSnapshot.child("rate").getValue().toString();
                        String image = dataSnapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(imageView2);
                        name2.setText(name);
                        rate2.setText(rate);
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
        cartMap.put("Foodkey",Foodkey2);
        cartMap.put("name",name2.getText().toString());
        cartMap.put("price",rate2.getText().toString());
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("quantity",numberButton.getNumber());
        cartMap.put("discount","");

        FirebaseDatabase.getInstance().getReference("CartList")
                .child("UserView").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Menus").child(Foodkey2)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            FirebaseDatabase.getInstance().getReference("CartList")
                                    .child("AdminView").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child("Menus").child(Foodkey2)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(FoodDetails2.this, "Added to Cart List", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(FoodDetails2.this,RestaurantTwoMenus.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    public void Go_Back(View view) {
        startActivity(new Intent(getApplicationContext(), RestaurantTwoMenus.class));
    }
    public void cart(View view) {
        startActivity(new Intent(getApplicationContext(), CartActivity.class));
    }

}
