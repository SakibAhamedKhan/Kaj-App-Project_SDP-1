package com.example.kajappdemo.employer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.kajappdemo.Login;
import com.example.kajappdemo.R;
import com.example.kajappdemo.employer.fragement.HomeFragment;
import com.example.kajappdemo.employer.fragement.OrderFragment;
import com.example.kajappdemo.employer.fragement.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EmployerDash extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    public  static  String username, fullname, nid, mobile, address, zipcode, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_dash);

      bottomNavigationView = findViewById(R.id.emp_BottomNavView);

        //getextras
        Bundle extras = getIntent().getExtras();

        //start query
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("user");

        username = extras.getString("username");


        Query query = databaseReference.orderByChild("username").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                fullname = snapshot.child(username).child("name").getValue(String.class);
                nid = snapshot.child(username).child("nidnumber").getValue(String.class);
                mobile= snapshot.child(username).child("phonenumber").getValue(String.class);
                email = snapshot.child(username).child("email").getValue(String.class);
                address = snapshot.child(username).child("address").getValue(String.class);
                zipcode = snapshot.child(username).child("zipcode").getValue(String.class);
//                textView2.setText("Full Name: "+fullname+"\nGovertment NID No: "+nidnumber+"\nPhone Number: "+phonenumber+"\nEmail: "+email);
//                Toast.makeText(getApplicationContext(), fullname[0], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        Fragement set

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.emp_FrameLayout, new HomeFragment());
        transaction.commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();

                switch (item.getItemId()){
                    case R.id.emp_Home:
                        transaction.replace(R.id.emp_FrameLayout, new HomeFragment());
                        break;
                    case R.id.emp_Order:
                        transaction.replace(R.id.emp_FrameLayout, new OrderFragment());
                        break;
                    case R.id.emp_Profile:
                        transaction.replace(R.id.emp_FrameLayout, new ProfileFragment());
                        break;
                }
                transaction.commit();

                return true;
            }
        });

    }
    public void logout(View view) {
        Intent intent = new Intent(EmployerDash.this, Login.class);
        startActivity(intent);
        finish();
    }
}