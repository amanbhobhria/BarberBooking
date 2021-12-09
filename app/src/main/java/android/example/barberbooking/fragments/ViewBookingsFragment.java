package android.example.barberbooking.fragments;

import android.example.barberbooking.adapter.ViewBookingAdapter;
import android.example.barberbooking.common.Common;
import android.example.barberbooking.model.BookingModel;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.example.barberbooking.R;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class ViewBookingsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;


    RecyclerView recyclerView;
    ViewBookingAdapter adapter;

    public ViewBookingsFragment() {
        // Required empty public constructor
    }


    public static ViewBookingsFragment newInstance(String param1, String param2) {
        ViewBookingsFragment fragment = new ViewBookingsFragment();
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


        View view = inflater.inflate(R.layout.fragment_view_bookings, container, false);
        try {
            recyclerView = (RecyclerView) view.findViewById(R.id.userBookingRecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


            FirebaseRecyclerOptions<BookingModel> options =
                    new FirebaseRecyclerOptions.Builder<BookingModel>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("bookings").orderByChild("userPhone").equalTo(Common.currentUser.getPhone()), BookingModel.class)
                            .build();



            adapter = new ViewBookingAdapter(options);


            recyclerView.setAdapter(adapter);
            return view;
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }

        return inflater.inflate(R.layout.fragment_view_bookings, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}