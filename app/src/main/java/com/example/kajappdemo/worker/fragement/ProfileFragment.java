package com.example.kajappdemo.worker.fragement;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.kajappdemo.Login;
import com.example.kajappdemo.R;
import com.example.kajappdemo.worker.WorkerDash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView wrk_profile_username, wrk_profile_fullname, wrk_profile_workertype;
    TextView wrk_profile_nid, wrk_profile_mobile, wrk_profile_address, wrk_profile_zipcode;
    Button logoutbtn;

//    public  static  String username, fullname, workertype, nid, mobile, address, zipcode;


    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View v = inflater.inflate(R.layout.fragment_profile2, container, false);

        wrk_profile_username = v.findViewById(R.id.wrk_profile_username);
        wrk_profile_fullname = v.findViewById(R.id.wrk_profile_fullname);
        wrk_profile_workertype = v.findViewById(R.id.wrk_profile_workertype);
        wrk_profile_nid = v.findViewById(R.id.wrk_profile_nid);
        wrk_profile_mobile = v.findViewById(R.id.wrk_profile_mobile);
        wrk_profile_address = v.findViewById(R.id.wrk_profile_address);
        wrk_profile_zipcode = v.findViewById(R.id.wrk_profile_zipcode);
        logoutbtn = v.findViewById(R.id.logoutbtn);


        wrk_profile_fullname.setText("Full Name: "+WorkerDash.fullname);
        wrk_profile_username.setText(WorkerDash.username);
        wrk_profile_workertype.setText("Worker Type: "+WorkerDash.workertype);
        wrk_profile_nid.setText("Government NID Number: "+WorkerDash.nid);
        wrk_profile_mobile.setText("Phone Number: "+WorkerDash.mobile);
        wrk_profile_address.setText("Address: "+WorkerDash.address);
        wrk_profile_zipcode.setText("Zip Code: "+WorkerDash.zipcode);

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Login.class);
                startActivity(intent);
                getActivity().finish();

            }
        });

        return v;
    }
}