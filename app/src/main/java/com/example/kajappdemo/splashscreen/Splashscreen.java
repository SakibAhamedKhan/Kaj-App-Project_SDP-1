package com.example.kajappdemo.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.kajappdemo.Login;
import com.example.kajappdemo.R;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Thread td = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2500);
                }catch (Exception ex){
                    ex.printStackTrace();
                }finally {
                    startActivity(new Intent(Splashscreen.this, Login.class));
                    finish();
                }
            }
        };
        td.start();
    }
}