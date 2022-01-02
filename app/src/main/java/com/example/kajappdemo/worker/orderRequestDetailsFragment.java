package com.example.kajappdemo.worker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kajappdemo.R;
import com.example.kajappdemo.employer.EmployerDash;
import com.example.kajappdemo.splashscreen.DoneFragment;
import com.example.kajappdemo.work.emp_RajmistriFragment;
import com.example.kajappdemo.worker.fragement.NotificationFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link orderRequestDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class orderRequestDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView username, fullname, address, phone, prize, orderduration;
    Button btn_decline, btn_accept;

    public orderRequestDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment orderRequestDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static orderRequestDetailsFragment newInstance(String param1, String param2) {
        orderRequestDetailsFragment fragment = new orderRequestDetailsFragment();
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
        View v = inflater.inflate(R.layout.fragment_order_request_details, container, false);

        username = v.findViewById(R.id.userename);
        orderduration = v.findViewById(R.id.order_duration);
        fullname = v.findViewById(R.id.fullname);
        address = v.findViewById(R.id.address);
        phone = v.findViewById(R.id.phone);
        prize = v.findViewById(R.id.prize);

        btn_accept = v.findViewById(R.id.btn_accept);
        btn_decline = v.findViewById(R.id.btn_decline);



        Bundle bundle = this.getArguments();
        if(bundle!=null){
            String pos = bundle.getString("position");
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("user");
            String user = NotificationFragment.list_of_req_emp.get(Integer.parseInt(pos));

            Query query = databaseReference.orderByChild("username").equalTo(user);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        username.setText(snapshot.child(user).child("username").getValue(String.class));
                        //orderduration.setText("Order Duration: 1 Day");
                        fullname.setText("Employeer Name: "+snapshot.child(user).child("name").getValue(String.class));
                        address.setText("Place: "+snapshot.child(user).child("address").getValue(String.class));
                        phone.setText("Phone Number: "+snapshot.child(user).child("phonenumber").getValue(String.class));
                       // prize.setText("Total Pay: "+WorkerDash.salary+".00 TK");


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            DatabaseReference databaseReference1 = firebaseDatabase.getReference("user/"+WorkerDash.username+"/orderreq/"+user);
            databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    orderduration.setText("Order Duration: "+snapshot.child("duration").getValue(String.class)+" Day");
                    prize.setText("Total Pay: "+snapshot.child("paidto").getValue(String.class)+".00 TK");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }

        // accept button
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pos = bundle.getString("position");
                String  user = NotificationFragment.list_of_req_emp.get(Integer.parseInt(pos));
                //update  worker oderreq->status to accept
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference("user/"+WorkerDash.username+"/orderreq/"+user+"/");

                HashMap hashMap = new HashMap();
                hashMap.put("status", "accept");
                databaseReference.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(getContext(), "Accepted Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
                //update employer ordertowork->
                DatabaseReference databaseReference1 = firebaseDatabase.getReference("user/"+user+"/ordertowork/"+WorkerDash.username);
                HashMap hashMap1 = new HashMap();
                hashMap1.put("Status", "accept");
                databaseReference1.updateChildren(hashMap1);

                FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.wrk_FrameLayout, new NotificationFragment());
                transaction.commit();
            }
        });

        //decline button
        btn_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pos = bundle.getString("position");
                String  user = NotificationFragment.list_of_req_emp.get(Integer.parseInt(pos));
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference("user/"+WorkerDash.username+"/orderreq/"+user+"/");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().removeValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
//                HashMap hashMap = new HashMap();
//                hashMap.put("status", "decline");
//                databaseReference.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
//                    @Override
//                    public void onSuccess(Object o) {
//                        Toast.makeText(getContext(), "Decline Successfully", Toast.LENGTH_SHORT).show();
//                    }
//                });
                FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.wrk_FrameLayout, new NotificationFragment());
                transaction.commit();
            }
        });


        return v;
    }
}