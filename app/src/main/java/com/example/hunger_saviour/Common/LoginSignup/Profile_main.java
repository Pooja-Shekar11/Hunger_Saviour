package com.example.hunger_saviour.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hunger_saviour.Common.LoginSignup.ForgotPassword;
import com.example.hunger_saviour.Common.LoginSignup.Login;
import com.example.hunger_saviour.Common.LoginSignup.SignUp;
import com.example.hunger_saviour.HelperClasses.Utils;
import com.example.hunger_saviour.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Profile_main extends AppCompatActivity {
    private static final String TAG = Profile_main.class.getSimpleName();
    private ImageView backButton;
    private TextView full_name;

    private TextView user_name;

    private TextView user_email;

    private TextView user_phone;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private user _profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);
        backButton = findViewById(R.id.profile_back_button);

        full_name=(TextView)findViewById(R.id.profile_fullname);

        user_name=(TextView)findViewById(R.id.profile_username);

        user_email=(TextView)findViewById(R.id.profile_email);

        user_phone=(TextView)findViewById(R.id.profile_phonenumber);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Profile_main.super.onBackPressed();
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");

        if (FirebaseAuth.getInstance().getUid() != null){
            String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();

            Log.d(TAG, "onCreate: uid" + uid);
            user profile = getUserProfile(uid);
        }
        //Log.d(TAG, "onCreate: name"  + profile.getFull_name());


            /*databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    Profile_main value = dataSnapshot.getValue(Profile_main.class);
                    Log.d(TAG, "Value is: " + value);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
*/
    }



    private user getUserProfile(String email){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange uid: " + email);
                //_profile = snapshot.getChildrenCount(user.class);
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("user_email").getValue().equals(email)) {
                        user_email.setText(dataSnapshot.child("user_email").getValue().toString());
                        user_phone.setText(dataSnapshot.child("user_phone").getValue().toString());
                        user_name.setText(dataSnapshot.child("user_name").getValue().toString());
                        full_name.setText(dataSnapshot.child("full_name").getValue().toString());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile_main.this, "Error occured", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onCancelled: " + error.getDetails());
                Log.d(TAG, "onCancelled: " + error.getMessage());
            }
        });
        return _profile;
    }

}

