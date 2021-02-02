package com.example.hunger_saviour.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hunger_saviour.HelperClasses.Utils;
import com.example.hunger_saviour.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class SignUp extends AppCompatActivity {
    private static final String TAG = SignUp.class.getSimpleName();
    private Button login;
    private Button sign_up;
    private ImageView backBtn;
    private TextInputLayout full_name;
    private TextInputLayout user_name;
    private TextInputLayout user_email;
    private TextInputLayout user_password;
    private TextInputLayout user_phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        full_name=(TextInputLayout) findViewById(R.id.signup_fullname);
        user_name=(TextInputLayout) findViewById(R.id.signup_username);
        user_email=(TextInputLayout) findViewById(R.id.signup_email);
        user_password=(TextInputLayout) findViewById(R.id.signup_password);
        user_phone=(TextInputLayout) findViewById(R.id.signup_phonenumber);
        backBtn=(ImageView) findViewById(R.id.signup_back_button);
        login=(Button)findViewById(R.id.signup_login_button);
        sign_up=(Button)findViewById(R.id.signup_button);

        sign_up.setOnClickListener(v -> {
            String full_name1=full_name.getEditText().getText().toString();
            String user_name1=user_name.getEditText().getText().toString();
            String user_email1=user_email.getEditText().getText().toString();
            String user_password1=user_password.getEditText().getText().toString();
            String user_phone1=user_phone.getEditText().getText().toString();

            createUser(full_name1,user_name1,user_email1,user_password1,user_phone1);
        });
        backBtn.setOnClickListener(v ->

        {

            Intent intent = new Intent(getApplicationContext(), StartUpScreen.class);

            Pair[] pairs = new Pair[1];
            pairs[0] = new Pair<View, String>(backBtn, "transition_back_arrow_btn");

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        });
        login.setOnClickListener(v ->

        {
            Intent intent = new Intent(getApplicationContext(), Login.class);

            Pair[] pairs = new Pair[1];
            pairs[0] = new Pair<View, String>(login, "transition_login_btn");

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        });
    }

    private void createUser(String full_name,String user_name,String user_email,String user_password,String user_phone){
        user user = new user(full_name,user_name,user_email,user_password,user_phone);
        getDatabaseRefence().push().setValue(user);
        createLogin(user_email,user_password);
    }

    public static DatabaseReference getDatabaseRefence() {
        return FirebaseDatabase.getInstance().getReference(Utils.USERS_TABLE);
    }

    public void createLogin(String username,String password){
        getFirebaseAuth().createUserWithEmailAndPassword(username.trim(),password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SignUp.this, "Account created successfully!!!", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent login = new Intent(SignUp.this,Login.class);
                            startActivity(login);
//                            Log.d(TAG, "onComplete: " + task.getException());
//                            Log.d(TAG, "onComplete: " + task.getResult());
                        }else {
                            Toast.makeText(SignUp.this, "Error Occured!!!", Toast.LENGTH_SHORT).show();
//                            Log.d(TAG, "onComplete: " + task.getException());
//                            Log.d(TAG, "onComplete: " + task.getResult());
                        }
                    }
                });
    }

    public static FirebaseAuth getFirebaseAuth(){return FirebaseAuth.getInstance();}

}
