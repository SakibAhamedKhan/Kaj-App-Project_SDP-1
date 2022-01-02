package com.example.kajappdemo.employer;

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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class order_progress_show_adapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> order_progress_of_emp;

    public order_progress_show_adapter(@NonNull Context context, List<String> order_progress_of_emp) {
        super(context, R.layout.list_of_order_progress_in_emp, order_progress_of_emp);
        this.context = context;
        this.order_progress_of_emp = order_progress_of_emp;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_order_progress_in_emp, null, true);

        TextView username = view.findViewById(R.id.userename);
        TextView address = view.findViewById(R.id.address);
        TextView duration = view.findViewById(R.id.duration);
        TextView total_paid = view.findViewById(R.id.total_paid);

        username.setText("Worker Name: "+order_progress_of_emp.get(position).toString());
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user/"+order_progress_of_emp.get(position).toString()+"/orderreq/"+EmployerDash.username);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                duration.setText("Duration: "+snapshot.child("duration").getValue(String.class)+" Day");
                total_paid.setText("Total Paid: "+snapshot.child("paidto").getValue(String.class)+" .00 TK");
                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("user/"+order_progress_of_emp.get(position).toString());
                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        address.setText("Address: "+snapshot.child("address").getValue(String.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}
