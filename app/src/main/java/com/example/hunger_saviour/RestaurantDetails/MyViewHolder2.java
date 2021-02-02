package com.example.hunger_saviour.RestaurantDetails;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hunger_saviour.R;

public class MyViewHolder2 extends RecyclerView.ViewHolder {
    ImageView imageView1;
    TextView textView1,textView2,textView3;
    View v;

    public MyViewHolder2(@NonNull View itemView) {
        super(itemView);
        imageView1=itemView.findViewById(R.id.foodimage);
        textView1=itemView.findViewById(R.id.foodname);
        textView2=itemView.findViewById(R.id.foodprice);
        textView3=itemView.findViewById(R.id.fooddescription);
        v=itemView;
    }
}
