package com.example.kajappdemo.worker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kajappdemo.R;
import com.example.kajappdemo.work.emp_RajmistriFragment;
import com.example.kajappdemo.work.wrk_RajmistriFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkerDetails2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkerDetails2Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView username, workertype, fullname, address, phone, prize;


    public WorkerDetails2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkerDetails2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkerDetails2Fragment newInstance(String param1, String param2) {
        WorkerDetails2Fragment fragment = new WorkerDetails2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_worker_details2, container, false);
        username = v.findViewById(R.id.userename);
        workertype = v.findViewById(R.id.workertype);
        fullname = v.findViewById(R.id.fullname);
        address = v.findViewById(R.id.address);
        phone = v.findViewById(R.id.phone);
        prize = v.findViewById(R.id.prize);


        Bundle bundle = this.getArguments();
        if(bundle!=null){
            String pos = bundle.getString("position");
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("user");
            String  user = wrk_RajmistriFragment.list_of_worker.get(Integer.parseInt(pos));

            Query query = databaseReference.orderByChild("username").equalTo(user);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        username.setText(snapshot.child(user).child("username").getValue(String.class));
                        workertype.setText("Worker Type: "+snapshot.child(user).child("workertype").getValue(String.class));
                        fullname.setText("Name: "+snapshot.child(user).child("name").getValue(String.class));
                        address.setText("Address: "+snapshot.child(user).child("address").getValue(String.class));
                        phone.setText("Phone Number: "+snapshot.child(user).child("phonenumber").getValue(String.class));
                        prize.setText("Salary Per Day: "+snapshot.child(user).child("salary").getValue(String.class));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }
        return v;
    }
}