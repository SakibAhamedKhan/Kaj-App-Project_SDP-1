package com.example.kajappdemo.worker.fragement;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kajappdemo.R;
import com.example.kajappdemo.worker.Notification_order_req_adapter;
import com.example.kajappdemo.worker.WorkerDash;
import com.example.kajappdemo.worker.orderRequestDetailsFragment;
import com.google.firebase.database.ChildEventListener;
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
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static List<String> list_of_req_emp;
    ListView listView;
    Notification_order_req_adapter adapter;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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
        View v = inflater.inflate(R.layout.fragment_notification, container, false);
        listView = v.findViewById(R.id.list_view_order_request);

        String[] ordered_emp = new String[1000];
        final Integer[] index = {0};

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("user/"+WorkerDash.username+"/orderreq");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.e("order", String.valueOf(snapshot.getRef().getParent().getKey()));// rakibcrs

                Integer p=0;
                for(DataSnapshot ds: snapshot.getChildren()){
                    if(snapshot.child(ds.getKey()).child("status").getValue(String.class).equals("true")) {
                        ordered_emp[index[0]++] = snapshot.child(ds.getKey()).child("employeer").getValue(String.class);
                        p++;
                    }
                    Log.e("orders", String.valueOf(snapshot.child(ds.getKey()).child("status").getValue(String.class)));
                }
                Log.e("order", String.valueOf(snapshot.getChildrenCount()));

                list_of_req_emp = new ArrayList<>();

                for(Integer i=0; i<p; i++){

                    Log.e("String", ordered_emp[i]);
                    list_of_req_emp.add(ordered_emp[i]);
                }
                try {
                    adapter = new Notification_order_req_adapter(getContext(), list_of_req_emp);
                    listView.setAdapter(adapter);
                } catch (Exception ex){
                    ex.printStackTrace();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // list click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                orderRequestDetailsFragment fragment = new orderRequestDetailsFragment();
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