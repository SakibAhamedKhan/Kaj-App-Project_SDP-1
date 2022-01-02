package com.example.kajappdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.kajappdemo.employer.EmployerDash;
import com.example.kajappdemo.splashscreen.Loading;
import com.example.kajappdemo.worker.WorkerDash;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.Map;

public class Login extends AppCompatActivity {

    Button signup, login;

    TextInputLayout email, password;
    TextInputEditText emailedt, passwordedt;

    ImageView imageView1;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private  SoftInputAssist softInputAssist;
    ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        softInputAssist = new SoftInputAssist(this);
        scrollView = findViewById(R.id.scrollViewLogin);

        //hide status
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        signup = findViewById(R.id.gosignup);
        login = findViewById(R.id.login);

        email = findViewById(R.id.emaillogin_text_field_design);
        password = findViewById(R.id.passwordlogin_text_field_design);

        emailedt = findViewById(R.id.emailloginedt);
        passwordedt = findViewById(R.id.paswordlogin);
        imageView1 = findViewById(R.id.imageViewLogin);
        emailedt.setClickable(true);
        emailedt.setFocusable(true);
        passwordedt.setClickable(true);
        passwordedt.setFocusable(true);


        emailedt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    imageView1.setVisibility(View.GONE);

            }
        });
        passwordedt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView1.setVisibility(View.GONE);
                password.setError(null);
                password.setErrorEnabled(false);
            }
        });
        passwordedt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                password.setError(null);
                password.setErrorEnabled(false);
            }
        });
//        emailedt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
//                scrollView.scrollTo(0, scrollView.getBottom());;
//            }
//        });
//
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //hide keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                imageView1.setVisibility(View.VISIBLE);

                String email_d = email.getEditText().getText().toString();
                final String email_data = email_d.toLowerCase();
                final String password_data = password.getEditText().getText().toString();

                if (email_data.isEmpty()) {
                    email.setError("please enter the email");
                    password.setError(null);
                    password.setErrorEnabled(false);
                } else {
                    email.setError(null);
                    email.setErrorEnabled(false);
                    if (password_data.isEmpty()) {
                        password.setError("please enter the password");
                    } else {
                        password.setError(null);
                        password.setErrorEnabled(false);

                        //next page
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = firebaseDatabase.getReference("user");

                        //check email is in firebase databse
                        Query check_email = databaseReference.orderByChild("email").equalTo(email_data);

                        check_email.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    email.setError(null);
                                    email.setErrorEnabled(false);
//                                    String passwordcheck = (String) snapshot.getValue();
//                                    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                                    String userNamestr = null;
                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                        userNamestr = ds.child("username").getValue(String.class);
                                    }
                                    Log.e("str", userNamestr);
                                    String passwordCheck =snapshot.child(userNamestr).child("password").getValue(String.class);
                                    if(passwordCheck.equals(password_data)){
                                        password.setError(null);
                                        password.setErrorEnabled(false);

                                        String accountType = snapshot.child(userNamestr).child("accounttype").getValue(String.class);
                                        Toast.makeText(getApplicationContext(), "login successfully ", Toast.LENGTH_SHORT).show();
                                        if(accountType.equals("Worker")){
                                            Intent intent = new Intent(Login.this, Loading.class);
                                            intent.putExtra("username", userNamestr);
                                            intent.putExtra("type", "Worker");
                                            startActivity(intent);
                                            finish();
                                        } else{
                                            Intent intent = new Intent(Login.this, Loading.class);
                                            intent.putExtra("username", userNamestr);
                                            intent.putExtra("type", "Employer");
                                            startActivity(intent);
                                            finish();
                                        }

                                    }else{
                                        password.setError("wrong password");
                                    }

                                } else {
                                    email.setError("email doesn't exists");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                }

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                imageView1.setVisibility(View.VISIBLE);
                startActivity(new Intent(Login.this, Signup.class));
                finish();
            }
        });
    }


    //        when keyboard enable Full screen adjustResize
    @Override
    protected void onResume() {

        softInputAssist.onResume();

        super.onResume();
    }

    @Override
    protected void onPause() {
        softInputAssist.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        softInputAssist.onDestroy();
        super.onDestroy();
    }

}