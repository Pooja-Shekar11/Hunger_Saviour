package com.example.hunger_saviour.User;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hunger_saviour.Cart.CartActivity;
import com.example.hunger_saviour.Common.LoginSignup.Login;
import com.example.hunger_saviour.Common.LoginSignup.Profile_main;
import com.example.hunger_saviour.Common.LoginSignup.StartUpScreen;
import com.example.hunger_saviour.R;

import com.example.hunger_saviour.RestaurantDetails.RestaurantFiveMenus;
import com.example.hunger_saviour.RestaurantDetails.RestaurantFourMenus;
import com.example.hunger_saviour.RestaurantDetails.RestaurantThreeMenus;
import com.example.hunger_saviour.RestaurantDetails.RestaurantTwoMenus;
import com.example.hunger_saviour.RestaurantDetails.ResturantOneMenus;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static final float END_SCALE = 0.7f;
    RecyclerView featuredRecycler;
    RecyclerView.Adapter adapter;
    ImageView menuIcon;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Button btn1,btn2,btn3,btn4,btn7;
    LinearLayout contentView;
    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);
        menuIcon = findViewById(R.id.menu_icon);
        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        contentView = findViewById(R.id.content);

        //Textview blinking
        blinkTextView();

        btn1=findViewById(R.id.mcdbtn);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserDashboard.this, ResturantOneMenus.class);
                startActivity(i);
            }
        });

        btn2=findViewById(R.id.dominosbtn);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserDashboard.this, RestaurantTwoMenus.class);
                startActivity(i);
            }
        });

        btn3=findViewById(R.id.PHbtn);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserDashboard.this, RestaurantThreeMenus.class);
                startActivity(i);
            }
        });

        btn4=findViewById(R.id.icbtn);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserDashboard.this, RestaurantFourMenus.class);
                startActivity(i);
            }
        });
        btn7=findViewById(R.id.paradisebtn);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserDashboard.this, RestaurantFiveMenus.class);
                startActivity(i);
            }
        });
        navigationDrawer();
    }

    ///Navigation Drawer Functions
    private void navigationDrawer() {
        //Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_view);
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        animateNavigationDrawer();
    }
    private void animateNavigationDrawer() {
        drawerLayout.setScrimColor(getResources().getColor(R.color.grey));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                                           @Override
                                           public void onDrawerSlide(View drawerView, float slideOffset) {
                                               final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                                               final float offsetScale = 1 - diffScaledOffset;
                                               contentView.setScaleX(offsetScale);
                                               contentView.setScaleY(offsetScale);
                                               final float xOffset = drawerView.getWidth() * slideOffset;
                                               final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                                               final float xTranslation = xOffset - xOffsetDiff;
                                               contentView.setTranslationX(xTranslation);
                                           }
                                       }
        );
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_login:
                startActivity(new Intent(getApplicationContext(), Login.class));
                break;
            case R.id.home:
                startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                break;
            case R.id.profile:
                if (FirebaseAuth.getInstance().getUid() != null) {
                    startActivity(new Intent(getApplicationContext(), Profile_main.class));
                    //Toast.makeText(this, "Logout successfully..!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Please Login to your account", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
                break;
            case R.id.cart:
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
                break;
            case R.id.rate_us:
                startActivity(new Intent(getApplicationContext(), Rating.class));
                break;
            case R.id.nav_logout:
                if (FirebaseAuth.getInstance().getUid() != null) {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(this, "Logout successfully..!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                } else {
                    Toast.makeText(this, "Please Login to your account", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    public void callStartUpScreens(View view){
        startActivity(new Intent(getApplicationContext(), StartUpScreen.class));
    }
    private void blinkTextView() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 3000;
                try{Thread.sleep(timeToBlink);}catch (Exception e) {}
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TextView txt = findViewById(R.id.RestaurantsName);
                        if(txt.getVisibility() == View.VISIBLE){
                            txt.setVisibility(View.INVISIBLE);
                        } else {
                            txt.setVisibility(View.VISIBLE);
                        }
                        blinkTextView();
                    }
                });
            }
        }).start();
    }

    public void MaxSafety(View view) {
        startActivity(new Intent(getApplicationContext(), RestaurantThreeMenus.class));
    }
    public void Trending(View view) {
        startActivity(new Intent(getApplicationContext(), ResturantOneMenus.class));
    }
    public void Pro(View view) {
        startActivity(new Intent(getApplicationContext(), RestaurantFourMenus.class));
    }
    public void PureVeg(View view) {
        startActivity(new Intent(getApplicationContext(), RestaurantFiveMenus.class));
    }

}
