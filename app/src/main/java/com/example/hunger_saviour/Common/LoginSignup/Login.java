package com.example.hunger_saviour.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hunger_saviour.R;
import com.example.hunger_saviour.User.UserDashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    ImageView backBtn;
    Button frgtpasswrd, Signup, Login;
    TextInputLayout username, password;

    private String uname, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        backBtn = findViewById(R.id.login_back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login.super.onBackPressed();
            }
        });
        frgtpasswrd = findViewById(R.id.forgt_buttn);
        frgtpasswrd.setOnClickListener(v ->
        {
            Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);

            Pair[] pairs = new Pair[1];
            pairs[0] = new Pair<View, String>(frgtpasswrd, "transition_forgot_buttn");

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        });

        Login = findViewById(R.id.loginsucess);
        Login.setOnClickListener(v -> {

            if (!validateUserName() | !validatePassword()) {
                return;
            } else {

                uname = username.getEditText().getText().toString();
                pass = password.getEditText().getText().toString();

                Log.d("TAG", "details: " + uname + " password " + pass);

                Intent intent = new Intent(getApplicationContext(), UserDashboard.class);

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(Login, "transition_forgot_buttn");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    getFirebaseAuth().signInWithEmailAndPassword(uname, pass)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(),
                                                "Login successful!!",
                                                Toast.LENGTH_LONG)
                                                .show();
                                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                                        startActivity(intent, options.toBundle());
                                    } else {
                                        Toast.makeText(getApplicationContext(),
                                                "Login Failed!!",
                                                Toast.LENGTH_LONG)
                                                .show();
                                        Toast.makeText(Login.this, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        Signup = findViewById(R.id.create_account);
        Signup.setOnClickListener(v ->

        {
            Intent intent = new Intent(getApplicationContext(), SignUp.class);
            Pair[] pairs = new Pair[1];
            pairs[0] = new Pair<View, String>(Signup, "transition_create_buttn");

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        });
    }

    private boolean validateUserName() {
        String val = username.getEditText().getText().toString().trim();
            /*String checkspaces = "\\A\\w{1,50}\\z";
            if (val.isEmpty()) {
                username.setError("Field cannot be empty");
                return false;
            } else if (val.length() > 20) {
                username.setError("Username is too Large");
                return false;
            } else if (!val.matches(checkspaces)) {
                username.setError("No white Spaces allowed");
                return false;
            } else {
                username.setError(null);
                username.setErrorEnabled(false);
                return true;

            }*/
        return true;
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
//            /*String checkPassword = "^" +
//                    "(?=.*[0-9])" +         //at least 1 digit
//                    "(?=.*[a-z])" +         //at least 1 lower case letter
//                    "(?=.*[A-Z])" +         //at least 1 upper case letter
//                    "(?=.*[a-zA-Z])" +      //any letter
//                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
//                    "(?=\\S+$)" +           //no white spaces
//                    ".{4,}" +               //at least 4 characters
//                    "$";
//
//            if (val.isEmpty()) {
//                password.setError("Field can not be empty");
//                return false;
//            } else if (!val.matches(checkPassword)) {
//                password.setError("Password should contain 4 characters!");
//                return false;
//            } else {
//                password.setError(null);
//                password.setErrorEnabled(false);
//                return true;
//            }*/
        return true;
    }

    public static FirebaseAuth getFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

}