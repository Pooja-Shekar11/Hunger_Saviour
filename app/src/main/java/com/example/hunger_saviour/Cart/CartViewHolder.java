package com.example.hunger_saviour.Cart;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hunger_saviour.Cart.Interface.ItemClickListener;
import com.example.hunger_saviour.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtFoodName, txtFoodPrice, txtFoodQuantity;
    private ItemClickListener itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txtFoodName = itemView.findViewById(R.id.cart_food_name);
        txtFoodPrice = itemView.findViewById(R.id.cart_food_price);
        txtFoodQuantity = itemView.findViewById(R.id.cart_food_quantity);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(),false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
