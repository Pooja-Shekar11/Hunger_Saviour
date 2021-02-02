package com.example.hunger_saviour.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.hunger_saviour.R;
import com.example.hunger_saviour.User.UserDashboard;

public class StartUpScreen extends AppCompatActivity {
    private Button loginBtn;
    private Button signupBtn;
    //private Button skipBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start_up_screen);


        loginBtn = findViewById(R.id.login_btn);

        loginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);


            Pair[] pairs = new Pair[1];
            pairs[0] = new Pair<View, String>(loginBtn, "transition_login");

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StartUpScreen.this, pairs);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        });

//        skipBtn = findViewById(R.id.skip_btn1);
//
//        skipBtn.setOnClickListener(v -> {
//            Intent intent = new Intent(getApplicationContext(), UserDashboard.class);
//
//
//            Pair[] pairs = new Pair[1];
//            pairs[0] = new Pair<View, String>(skipBtn, "transition_login");
//
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RetailerStartUpScreen.this, pairs);
//                startActivity(intent, options.toBundle());
//            } else {
//                startActivity(intent);
//            }
//        });


        signupBtn = findViewById(R.id.signup_btn);

        signupBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SignUp.class);


            Pair[] pairs = new Pair[1];
            pairs[0] = new Pair<View, String>(signupBtn, "transition_signup");

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StartUpScreen.this, pairs);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        });

    }
}