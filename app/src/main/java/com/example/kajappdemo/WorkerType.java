package com.example.kajappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kajappdemo.splashscreen.Loading;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkerType extends AppCompatActivity {

    Spinner spinner;
    Button button;
    ArrayAdapter<String> arrayAdapter;

    TextInputLayout prize;
    TextInputEditText prizeedt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker_type);

        button = findViewById(R.id.button);

        prize = findViewById(R.id.prize);
        prizeedt = findViewById(R.id.prizeedt);

        prizeedt.setClickable(true);
        prizeedt.setFocusable(true);



        spinner = findViewById(R.id.spinner1);
        List<String> type = new ArrayList<>();
        type.add(0, "Choose the work type");
        type.add("Raj Mistri");
        type.add("Kat Mistri");
        type.add("Methor");
        type.add("Bowa");
        type.add("Electric Mistri");
        type.add("Fittings Mistri");

        arrayAdapter = new ArrayAdapter<>(this, R.layout.signup_dropdown_account_type, type);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String typeSelect = spinner.getSelectedItem().toString();
                if(typeSelect.equals("Choose the work type")){
                    Toast.makeText(getApplicationContext(), "Please Choose Work Type!", Toast.LENGTH_SHORT).show();
                } else{

                    String Salary = prize.getEditText().getText().toString();
                    if(!Salary.isEmpty()){
                        prize.setError(null);
                        prize.setErrorEnabled(false);




                        Bundle extra = getIntent().getExtras();
                        String usernamestr = extra.getString("username");
                        String type = extra.getString("type");

                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = firebaseDatabase.getReference("user").child(usernamestr);

//                    add new Name and value in Child
                        Map<String, Object> userData = new HashMap<>();
                        userData.put("workertype", typeSelect);
                        userData.put("salary", Salary);
                        databaseReference.updateChildren(userData);
//                    Toast.makeText(getApplicationContext(), type, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(WorkerType.this, Loading.class);
                        intent.putExtra("username", usernamestr);
                        intent.putExtra("type","Worker");
                        startActivity(intent);
                        finish();
                    }else{
                        prize.setError("Please Give the Salary!");
                    }


                }
            }
        });
    }
}