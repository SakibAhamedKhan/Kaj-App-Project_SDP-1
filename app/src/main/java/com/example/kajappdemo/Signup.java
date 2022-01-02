package com.example.kajappdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kajappdemo.employer.EmployerDash;
import com.example.kajappdemo.splashscreen.Loading;
import com.example.kajappdemo.worker.WorkerDash;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Signup extends AppCompatActivity {

    TextInputLayout fullName, userName, email, phoneNumber, nidNumber, password, address, zipCode;

    Spinner spinner;
    ArrayAdapter<String> arrayAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private  SoftInputAssist softInputAssist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

//        when keyboard enable Full screen adjustResize

        softInputAssist = new SoftInputAssist(this);


//        hide status
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fullName = findViewById(R.id.fullname_text_field_design);
        userName = findViewById(R.id.username_text_field_design);
        email = findViewById(R.id.emailSignup_text_field_design);
        phoneNumber = findViewById(R.id.phone_text_field_design);
        nidNumber = findViewById(R.id.nid_text_field_design);
        password = findViewById(R.id.passwordsignup_text_field_design);
        address = findViewById(R.id.addrresssignup_text_field_design);
        zipCode = findViewById(R.id.zipcodesignup_text_field_design);

//        Dropdown item selection
        spinner = findViewById(R.id.spinner);
        List<String> catagorie = new ArrayList<>();
        catagorie.add(0, "Choose Account Type");
        catagorie.add("Worker");
        catagorie.add("Employer");

        arrayAdapter = new ArrayAdapter<>(this, R.layout.signup_dropdown_account_type, catagorie);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        //change spinner icon color
        spinner.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);


    }

//    when keyboard enable Full screen adjustResize

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

//    Register Button onClick

    public void register(View view) {
        String fullnamestr = fullName.getEditText().getText().toString();
        String usernames = userName.getEditText().getText().toString();
        String usernamestr = usernames.toLowerCase();
        String emailfs = email.getEditText().getText().toString();
        String emailstr = emailfs.toLowerCase();
        String phonenumberstr = phoneNumber.getEditText().getText().toString();
        String nidnumberstr = nidNumber.getEditText().getText().toString();
        String passwordstr = password.getEditText().getText().toString();
        String addressstr = address.getEditText().getText().toString();
        String zipcodestr = zipCode.getEditText().getText().toString();


        if (!fullnamestr.isEmpty()) {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            if (!usernamestr.isEmpty()) {
                userName.setError(null);
                userName.setErrorEnabled(false);
                if (!emailstr.isEmpty()) {
                    email.setError(null);
                    email.setErrorEnabled(false);
                    if (!phonenumberstr.isEmpty()) {
                        phoneNumber.setError(null);
                        phoneNumber.setErrorEnabled(false);
                          if (!nidnumberstr.isEmpty()) {
                            nidNumber.setError(null);
                            nidNumber.setErrorEnabled(false);
                            if (!passwordstr.isEmpty()) {
                                password.setError(null);
                                password.setErrorEnabled(false);


                                if (emailstr.matches("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {

                                    firebaseDatabase = FirebaseDatabase.getInstance();
                                    databaseReference = firebaseDatabase.getReference("user");

                                    //check signup email is already registered
                                    Query check_email = databaseReference.orderByChild("email").equalTo(emailstr);
                                    check_email.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                email.setError("email is already used");
                                            } else {
                                                email.setError(null);
                                                email.setErrorEnabled(false);
                                                //check signup username is already used
                                                Query checkusername = databaseReference.orderByChild("username").equalTo(usernamestr);
                                                checkusername.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if (snapshot.exists()) {
                                                            userName.setError("username is alreaady useed");
                                                        } else {
                                                            userName.setError(null);
                                                            userName.setErrorEnabled(false);
                                                            //phone number validation
                                                            if(phonenumberstr.length()!=11){
                                                                phoneNumber.setError("invaid phone number");
                                                            } else{
                                                                phoneNumber.setError(null);
                                                                phoneNumber.setErrorEnabled(false);

                                                                //password length must be 6
                                                                if (passwordstr.length() < 6) {
                                                                    password.setError("password must be 6 character");
                                                                } else {
                                                                    password.setError(null);
                                                                    password.setErrorEnabled(false);

                                                                    //check Account Type is Selected or not
                                                                    String accountType = spinner.getSelectedItem().toString();
                                                                    if (accountType.equals("Choose Account Type")) {
                                                                        //do nothing
                                                                        Toast.makeText(getApplicationContext(), "You must Select Account Type!", Toast.LENGTH_SHORT).show();
                                                                    } else {
                                                                        //store all data in firebase
                                                                        StoringData storingData = new StoringData(fullnamestr, usernamestr, emailstr, phonenumberstr, passwordstr, nidnumberstr, accountType, addressstr, zipcodestr);

                                                                        databaseReference.child(usernamestr).setValue(storingData);
                                                                        Toast.makeText(getApplicationContext(), "Registration Successfully", Toast.LENGTH_SHORT).show();

                                                                        if(accountType=="Worker"){
//                                                                            DatabaseReference databaseReference1 = firebaseDatabase.getReference("user").child(usernamestr);
//                                                                            Map<String, Object> userData = new HashMap<>();
//                                                                            userData.put("orderfromemp", "");
//                                                                            databaseReference1.updateChildren(userData);

                                                                            Intent intent = new Intent(Signup.this, WorkerType.class);
                                                                            intent.putExtra("username", usernamestr);
                                                                            intent.putExtra("type", "Worker");
                                                                            startActivity(intent);
                                                                            finish();
                                                                        } else{
//                                                                            DatabaseReference databaseReference1 = firebaseDatabase.getReference("user").child(usernamestr);
//                                                                            Map<String, Object> userData = new HashMap<>();
//                                                                            userData.put("ordertowrk", "");
//                                                                            databaseReference1.updateChildren(userData);
                                                                            Intent intent = new Intent(Signup.this, Loading.class);
                                                                            intent.putExtra("username", usernamestr);
                                                                            intent.putExtra("type","Employer");
                                                                            startActivity(intent);
                                                                            finish();
                                                                        }


                                                                    }

                                                                }
                                                            }


                                                        }//
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });


                                } else {
                                    email.setError("Invaild email");
                                }

                            } else {
                                password.setError("please enter the password");
                            }
                        } else {
                            nidNumber.setError("please enter the Government NID number");
                        }
                    } else {
                        phoneNumber.setError("please enter the phone number");
                    }
                } else {
                    email.setError("please enter the email");
                }
            } else {
                userName.setError("please enter the user name");
            }
        } else {
            fullName.setError("please enter the full name");
        }
    }

    public void backlogin(View view) {
        startActivity(new Intent(Signup.this, Login.class));
        finish();
    }
}