package com.example.kajappdemo.work;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kajappdemo.R;
import com.example.kajappdemo.employer.WorkerDetailsFragment;
import com.example.kajappdemo.worker.WorkerDetails2Fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link wrk_RajmistriFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class wrk_RajmistriFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView textView1;
    public static List<String> list_of_worker;
    ListView listView;

    WorkerListAdapter workerListAdapter;

    public wrk_RajmistriFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment wrk_RajmistriFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static wrk_RajmistriFragment newInstance(String param1, String param2) {
        wrk_RajmistriFragment fragment = new wrk_RajmistriFragment();
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
        View v=  inflater.inflate(R.layout.fragment_wrk__rajmistri, container, false);

        textView1 = v.findViewById(R.id.textView1);
        listView = v.findViewById(R.id.listViewWorker);
        String workertype = null;

        Bundle  bundle =  this.getArguments();
        if(bundle!=null){
            workertype = bundle.getString("workertype");
//            Log.e("workert", workertype);
        }
        textView1.setText(workertype);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("user");

        Query check_workertype = databaseReference.orderByChild("workertype").equalTo(workertype);

        check_workertype.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    list_of_worker = new ArrayList<String>();
                    for(DataSnapshot ds : snapshot.getChildren()){
                        list_of_worker.add(ds.child("username").getValue(String.class));
                    }
                    workerListAdapter = new WorkerListAdapter(getContext(), list_of_worker);
                    listView.setAdapter(workerListAdapter);
                }
                Log.e("workerFind", String.valueOf(list_of_worker));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WorkerDetails2Fragment fragment = new WorkerDetails2Fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Bundle bundle = new Bundle();
                bundle.putString("position", String.valueOf(position));
                fragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.wrk_FrameLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });




        return v;
    }
}