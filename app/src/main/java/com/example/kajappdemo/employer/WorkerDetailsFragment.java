package com.example.kajappdemo.employer;

import static com.example.kajappdemo.R.color.white;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kajappdemo.R;
import com.example.kajappdemo.splashscreen.DoneFragment;
import com.example.kajappdemo.work.emp_RajmistriFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkerDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkerDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView username, workertype, fullname, address, phone, prize, orderday_show_onchange, total_salary, for_total;
    Button order, btn_plus, btn_minus;


    public WorkerDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkerDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkerDetailsFragment newInstance(String param1, String param2) {
        WorkerDetailsFragment fragment = new WorkerDetailsFragment();
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
        View v = inflater.inflate(R.layout.fragment_worker_details, container, false);
        username = v.findViewById(R.id.userename);
        workertype = v.findViewById(R.id.workertype);
        fullname = v.findViewById(R.id.fullname);
        address = v.findViewById(R.id.address);
        phone = v.findViewById(R.id.phone);
        prize = v.findViewById(R.id.prize);
        order = v.findViewById(R.id.Order);
        total_salary = v.findViewById(R.id.total_salary);
        for_total = v.findViewById(R.id.for_total);

        orderday_show_onchange = v.findViewById(R.id.orderday_show_onchange);
        btn_plus = v.findViewById(R.id.btn_plus);
        btn_minus = v.findViewById(R.id.btn_minus);
        final Integer[] day = {Integer.valueOf(orderday_show_onchange.getText().toString())};
        final Integer[] workerSalary = new Integer[1];
        final Integer[] total = new Integer[1];



        Bundle bundle = this.getArguments();
        if(bundle!=null){
            String pos = bundle.getString("position");
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("user");
            String  user = emp_RajmistriFragment.list_of_worker.get(Integer.parseInt(pos));

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
                        for_total.setText(snapshot.child(user).child("salary").getValue(String.class));
                        total_salary.setText(snapshot.child(user).child("salary").getValue(String.class)+".00 Tk");

                        total[0] = Integer.valueOf(for_total.getText().toString());
                        prize.setText("Salary Per Day: "+snapshot.child(user).child("salary").getValue(String.class));
                        try{
                            if(snapshot.child(user).child("orderreq").child(EmployerDash.username).child("status").getValue(String.class).equals("true")){
                                order.setBackgroundColor(order.getContext().getResources().getColor(R.color.light_drak));
                                order.setEnabled(false);
                                Toast.makeText(getContext(), "Already Ordered", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception exception){

                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference df = firebaseDatabase.getReference("user");
//        Query query = df.orderByChild("username").equalTo(username.getText().toString());
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    if(snapshot.child(username.getText().toString()).child("orderreq").child(EmployerDash.username).getValue(String.class).equals("true")){
//                        order.setBackgroundColor(order.getContext().getResources().getColor(R.color.light_drak));
//                        order.setEnabled(false);
//                        Toast.makeText(getContext(), "Already Ordered", Toast.LENGTH_SHORT).show();
//                    }
//                    Log.e("snap",snapshot.toString());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        //Plus button

        orderday_show_onchange.setText(String.valueOf(day[0])+" Day");

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                day[0]++;
                orderday_show_onchange.setText(String.valueOf(day[0])+" Day");
                workerSalary[0]= Integer.valueOf(for_total.getText().toString());
                 total[0] = workerSalary[0] * day[0];
                total_salary.setText(total[0] +".00TK");
            }
        });
        //Minus button
        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day[0]--;
                if (day[0]<1) {
                    day[0]++;
                }
                orderday_show_onchange.setText(String.valueOf(day[0])+" Day");
                workerSalary[0]= Integer.valueOf(for_total.getText().toString());
                 total[0] = workerSalary[0] * day[0];
                total_salary.setText(total[0] +".00TK");

            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference("user").child(username.getText().toString()).child("orderreq").child(EmployerDash.username);
                    Map<String, Object> userData = new HashMap<>();
                    userData.put("status", "true");
                    userData.put("employeer", EmployerDash.username);
                    userData.put("duration", String.valueOf(day[0]));
                    userData.put("paidto", String.valueOf(total[0]));
                    databaseReference.updateChildren(userData);

                FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.emp_FrameLayout, new DoneFragment());
                transaction.commit();

            }
        });




        return v;
    }
}