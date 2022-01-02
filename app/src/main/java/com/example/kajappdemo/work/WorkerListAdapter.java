package com.example.kajappdemo.work;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

public class WorkerListAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> workerusername;

    public WorkerListAdapter(@NonNull Context context, List<String> workerusername) {
        super(context, R.layout.list_of_worker_item, workerusername);
        this.context = context;
        this.workerusername = workerusername;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_worker_item,null, true);
        TextView fullName = view.findViewById(R.id.fullname);
        TextView address = view.findViewById(R.id.address);
        TextView prize = view.findViewById(R.id.prize);
        ImageView image = view.findViewById(R.id.imageView);

//        image.setImageDrawable(image.getResources().getDrawable(R.drawable.people));




        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference  databaseReference = firebaseDatabase.getReference("user");

        Query check = databaseReference.orderByChild("username").equalTo(workerusername.get(position).toString());
        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fullName.setText(snapshot.child(workerusername.get(position).toString()).child("name").getValue(String.class));
                address.setText(snapshot.child(workerusername.get(position).toString()).child("address").getValue(String.class));
                prize.setText("- "+snapshot.child(workerusername.get(position).toString()).child("salary").getValue(String.class)+"/day");
                image.setBackgroundResource(R.drawable.people);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}
