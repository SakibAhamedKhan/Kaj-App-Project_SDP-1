package com.example.kajappdemo.worker.fragement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kajappdemo.R;
import com.example.kajappdemo.work.emp_RajmistriFragment;
import com.example.kajappdemo.work.wrk_RajmistriFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button rajmistribtn, katmistribtn, methorbtn, bowabtn,  fittingsbtn, electricmistribtn;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        rajmistribtn = v.findViewById(R.id.rajmistibtn);
        katmistribtn = v.findViewById(R.id.katmistribtn);
        methorbtn = v.findViewById(R.id.methorbtn);
        bowabtn = v.findViewById(R.id.bowabtn);
        fittingsbtn = v.findViewById(R.id.fittingsmistribtn);
        electricmistribtn = v.findViewById(R.id.electricmistribtn);


        rajmistribtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrk_RajmistriFragment fragment = new wrk_RajmistriFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Bundle bundle = new Bundle();
                bundle.putString("workertype", "Raj Mistri");
                fragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.wrk_FrameLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
//                Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });
        katmistribtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrk_RajmistriFragment fragment = new wrk_RajmistriFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Bundle bundle = new Bundle();
                bundle.putString("workertype", "Kat Mistri");
                fragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.wrk_FrameLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
//                Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });
        methorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrk_RajmistriFragment fragment = new wrk_RajmistriFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Bundle bundle = new Bundle();
                bundle.putString("workertype", "Methor");
                fragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.wrk_FrameLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
//                Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });
        bowabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrk_RajmistriFragment fragment = new wrk_RajmistriFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Bundle bundle = new Bundle();
                bundle.putString("workertype", "Bowa");
                fragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.wrk_FrameLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
//                Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });
        fittingsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrk_RajmistriFragment fragment = new wrk_RajmistriFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Bundle bundle = new Bundle();
                bundle.putString("workertype", "Fittings Mistri");
                fragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.wrk_FrameLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
//                Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });
        electricmistribtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrk_RajmistriFragment fragment = new wrk_RajmistriFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Bundle bundle = new Bundle();
                bundle.putString("workertype", "Electric Mistri");
                fragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.wrk_FrameLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
//                Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });



        return v;
    }
}