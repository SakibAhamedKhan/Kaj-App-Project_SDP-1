package com.example.kajappdemo.employer.fragement;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.kajappdemo.Login;
import com.example.kajappdemo.R;
import com.example.kajappdemo.employer.EmployerDash;
import com.example.kajappdemo.worker.WorkerDash;

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

    TextView emp_profile_username, emp_profile_fullname;
    TextView emp_profile_nid, emp_profile_mobile, emp_profile_address, emp_profile_zipcode;
    Button logoutbtn;

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
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        emp_profile_username = v.findViewById(R.id.emp_profile_username);
        emp_profile_fullname = v.findViewById(R.id.emp_profile_fullname);
        emp_profile_nid = v.findViewById(R.id.emp_profile_nid);
        emp_profile_mobile = v.findViewById(R.id.emp_profile_mobile);
        emp_profile_address = v.findViewById(R.id.emp_profile_address);
        emp_profile_zipcode = v.findViewById(R.id.emp_profile_zipcode);
        logoutbtn = v.findViewById(R.id.logoutbtn);


        emp_profile_fullname.setText("Full Name: "+ EmployerDash.fullname);
        emp_profile_username.setText(EmployerDash.username);
        emp_profile_nid.setText("Government NID Number: "+EmployerDash.nid);
        emp_profile_mobile.setText("Phone Number: "+EmployerDash.mobile);
        emp_profile_address.setText("Address: "+EmployerDash.address);
        emp_profile_zipcode.setText("Zip Code: "+EmployerDash.zipcode);

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