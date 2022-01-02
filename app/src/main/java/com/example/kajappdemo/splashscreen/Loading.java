package com.example.kajappdemo.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.kajappdemo.R;
import com.example.kajappdemo.employer.EmployerDash;
import com.example.kajappdemo.worker.WorkerDash;

public class Loading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);

        Bundle extras = getIntent().getExtras();
        String type = extras.getString("type");
        String username = extras.getString("username");

        if(type.equals("Worker")){
            Thread td = new Thread(){
                @Override
                public void run() {
                    try {
                        sleep(3000);
                    } catch (Exception ex){
                        ex.printStackTrace();
                    } finally {
                        Intent intent = new Intent(Loading.this, WorkerDash.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        finish();
                    }
                }
            };
            td.start();
        } else{
            Thread td = new Thread(){
                @Override
                public void run() {
                    try {
                        sleep(3000);
                    } catch (Exception ex){
                        ex.printStackTrace();
                    } finally {
                        Intent intent = new Intent(Loading.this, EmployerDash.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        finish();
                    }
                }
            };
            td.start();
        }
    }
}