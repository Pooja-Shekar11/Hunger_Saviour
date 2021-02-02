package com.example.hunger_saviour.Cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hunger_saviour.R;
import com.example.hunger_saviour.RestaurantDetails.FoodDetails;
import com.example.hunger_saviour.RestaurantDetails.RestaurantTwoMenus;
import com.example.hunger_saviour.RestaurantDetails.ResturantOneMenus;
import com.example.hunger_saviour.User.ConfirmFinalOrder;
import com.example.hunger_saviour.User.UserDashboard;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button nextProcessBtn;
    private TextView txtTotalAmount ;
    private int overTotalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        nextProcessBtn = (Button) findViewById(R.id.placeorder);
        txtTotalAmount = (TextView) findViewById(R.id.total_price);
//        txtMsg1 = (TextView) findViewById(R.id.msg1);

        nextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    txtTotalAmount.setText("Total Price = " + String.valueOf(overTotalPrice));

                } catch (NumberFormatException e){
                    return;
                }
                txtTotalAmount.setText("Total Price = " + String.valueOf(overTotalPrice));
                Intent intent = new Intent(CartActivity.this, ConfirmFinalOrder.class);
                intent.putExtra("Total Price",String.valueOf(overTotalPrice));
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();


        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference("CartList");
        FirebaseRecyclerOptions<cart> options =
                new FirebaseRecyclerOptions.Builder<cart>()
                        .setQuery(cartListRef.child("UserView")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("Menus"),cart.class)
                        .build();

        FirebaseRecyclerAdapter<cart, CartViewHolder> adapter =
                new FirebaseRecyclerAdapter<cart, CartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int position, @NonNull final cart model) {
                        try {


                            cartViewHolder.txtFoodQuantity.setText("Quantity = " + model.getQuantity());
                            cartViewHolder.txtFoodPrice.setText("Price = " + model.getPrice());
                            cartViewHolder.txtFoodName.setText(model.getName());
                            int oneTypeTotalProduct = (Integer.valueOf(model.getPrice())) * Integer.valueOf(model.getQuantity());
                            overTotalPrice = overTotalPrice + oneTypeTotalProduct;
                        } catch(NumberFormatException e){
                            return;
                        }


                        cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                CharSequence options[] = new CharSequence[]
                                        {
                                                "Edit",
                                                "Delete"
                                        };

                                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                                builder.setTitle("Options ");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (which==0){
                                            Intent intent = new Intent(CartActivity.this, UserDashboard.class);
                                            intent.putExtra("Foodkey",model.getFoodkey());
                                            startActivity(intent);
                                        }
                                        if (which==1){
                                            cartListRef.child("UserView")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .child("Menus")
                                                    .child(model.getFoodkey())
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            if (task.isSuccessful()){
                                                                Toast.makeText(CartActivity.this, "Item deleted successfully.", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(CartActivity.this, CartActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        }
                                                    });
                                        }

                                    }
                                });
                                builder.show();
                            }
                        });
                    }
                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartmenu_layout,parent,false);
                        CartViewHolder holder = new CartViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


    public void Go_Back(View view) {
            startActivity(new Intent(getApplicationContext(), UserDashboard.class));
    }

}
