package com.example.kajappdemo.worker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kajappdemo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Notification_order_req_adapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> ordered_emp;

    public Notification_order_req_adapter(@NonNull Context context, List<String> ordered_emp) {
        super(context, R.layout.list_of_ordertoworker_from_emp, ordered_emp);
        this.context = context;
        this.ordered_emp = ordered_emp;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_ordertoworker_from_emp, null, true);
        TextView name = view.findViewById(R.id.name);
        TextView phone = view.findViewById(R.id.phone_number);
        TextView place = view.findViewById(R.id.place);
        TextView username = view.findViewById(R.id.userename);
        TextView duration = view.findViewById(R.id.duration);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("user");

        Query check = databaseReference.orderByChild("username").equalTo(ordered_emp.get(position).toString());
        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText("Employeer Name: "+snapshot.child(ordered_emp.get(position).toString()).child("name").getValue(String.class));
                phone.setText("Employeer Phone Number: "+snapshot.child(ordered_emp.get(position).toString()).child("phonenumber").getValue(String.class));
                place.setText("Ordered Place: "+snapshot.child(ordered_emp.get(position).toString()).child("address").getValue(String.class));
                //duration.setText("Duration: 1 Day");
                username.setText(ordered_emp.get(position).toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference databaseReference1 = firebaseDatabase.getReference("user/"+WorkerDash.username+"/orderreq/"+ordered_emp.get(position).toString());
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               duration.setText("Duration: "+snapshot.child("duration").getValue(String.class)+" Day");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}
