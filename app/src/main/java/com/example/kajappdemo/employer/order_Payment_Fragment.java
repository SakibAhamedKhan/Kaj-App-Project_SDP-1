package com.example.kajappdemo.employer;

import static java.lang.Thread.sleep;

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
import com.example.kajappdemo.employer.fragement.OrderFragment;
import com.example.kajappdemo.splashscreen.DoneFragment;
import com.example.kajappdemo.worker.fragement.NotificationFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link order_Payment_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class order_Payment_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView username, workertype, phoneNumber, duration, totalPaid;
    Button payment;

    public order_Payment_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment order_Payment_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static order_Payment_Fragment newInstance(String param1, String param2) {
        order_Payment_Fragment fragment = new order_Payment_Fragment();
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
        View view  = inflater.inflate(R.layout.fragment_order__payment_, container, false);
        username = view.findViewById(R.id.userename);
        workertype = view.findViewById(R.id.workertype);
        phoneNumber = view.findViewById(R.id.phoneNumber);
        duration = view.findViewById(R.id.duration);
        totalPaid = view.findViewById(R.id.totalPaid);

        payment = view.findViewById(R.id.payment);
        Bundle bundle = this.getArguments();
        if(bundle!=null) {
            String pos = bundle.getString("position");
            String user = OrderFragment.list_of_order_progress_of_emp.get(Integer.parseInt(pos));

            username.setText("Worker Name: "+user);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user/"+user);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    workertype.setText("Work Type: "+snapshot.child("workertype").getValue(String.class));
                    phoneNumber.setText("Phone Number: "+snapshot.child("phonenumber").getValue(String.class));

                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("user/"+user+"/orderreq/"+EmployerDash.username);
                    databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            duration.setText("Duration: "+snapshot.child("duration").getValue(String.class)+" Day");
                            totalPaid.setText("Total Paid: "+snapshot.child("paidto").getValue(String.class));

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
            payment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Payment Done Successfully", Toast.LENGTH_SHORT).show();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user/"+EmployerDash.username+"/ordertowork");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for(DataSnapshot ds: snapshot.getChildren()){
                                if(ds.getKey().equals(user)){
                                    //Toast.makeText(getContext(), user, Toast.LENGTH_SHORT).show();
                                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("user/"+EmployerDash.username+"/ordertowork/"+ds.getKey());
                                    databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            snapshot.getRef().removeValue();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("user/"+ds.getKey()+"/orderreq/"+EmployerDash.username);
                                    databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            snapshot.getRef().removeValue();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                //change fragment
                    Thread tr = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                sleep(2000);
                            } catch (Exception ex){
                                ex.printStackTrace();
                            } finally {
                                FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.replace(R.id.emp_FrameLayout, new OrderFragment());
                                transaction.commit();
                            }
                        }
                    });
                    tr.start();


                }
            });
        }



            return view;
    }
}