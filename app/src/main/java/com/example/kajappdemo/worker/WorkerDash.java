package com.example.kajappdemo.worker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.kajappdemo.Login;
import com.example.kajappdemo.R;
import com.example.kajappdemo.worker.fragement.HomeFragment;
import com.example.kajappdemo.worker.fragement.NotificationFragment;
import com.example.kajappdemo.worker.fragement.OrderFragment;
import com.example.kajappdemo.worker.fragement.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class WorkerDash extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    public  static  String username, fullname, workertype, nid, mobile, address, zipcode, email, salary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker_dash);

        bottomNavigationView = findViewById(R.id.wrk_BottomNavView);

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

                fullname= snapshot.child(username).child("name").getValue(String.class);
                nid = snapshot.child(username).child("nidnumber").getValue(String.class);
                mobile = snapshot.child(username).child("phonenumber").getValue(String.class);
                email = snapshot.child(username).child("email").getValue(String.class);
                workertype = snapshot.child(username).child("workertype").getValue(String.class);
                address = snapshot.child(username).child("address").getValue(String.class);
                zipcode = snapshot.child(username).child("zipcode").getValue(String.class);
                salary = snapshot.child(username).child("salary").getValue(String.class);

//                textView2.setText("Full Name: "+fullname+"\nGovertment NID No: "+nidnumber+"\nPhone Number: "+phonenumber+"\nEmail: "+email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        Fragement set

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.wrk_FrameLayout, new HomeFragment());
        transaction.commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                switch (item.getItemId()){
                    case R.id.wrk_Home:
                        transaction.replace(R.id.wrk_FrameLayout, new HomeFragment());
                        break;
                    case R.id.wrk_Order:
                        transaction.replace(R.id.wrk_FrameLayout, new OrderFragment());
                        break;
                    case R.id.wrk_Notification:
                        transaction.replace(R.id.wrk_FrameLayout, new NotificationFragment());
                        break;
                    case R.id.wrk_Profile:
                        transaction.replace(R.id.wrk_FrameLayout, new ProfileFragment());
                        break;
                }
                transaction.commit();

                return true;
            }
        });


    }

    public void logout(View view) {
        Intent intent = new Intent(WorkerDash.this, Login.class);
        startActivity(intent);
        finish();
    }
}