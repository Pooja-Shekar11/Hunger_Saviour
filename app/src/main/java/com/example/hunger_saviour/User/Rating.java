package com.example.hunger_saviour.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hunger_saviour.R;

public class Rating extends AppCompatActivity {
    RatingBar rb;
    TextView value;
    Button b;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_rating);
            rb=(RatingBar) findViewById(R.id.ratingBar);
            value=(TextView) findViewById(R.id.value);
            b=(Button)findViewById(R.id.button);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Rating.this,"Rating updated",Toast.LENGTH_LONG).show();
                }
            });
            rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    value.setText("Thank You for rating Us : " + rating);
                }
            });
        }

    public void Go_Back(View view) {
        startActivity(new Intent(getApplicationContext(), UserDashboard.class));
    }
}
