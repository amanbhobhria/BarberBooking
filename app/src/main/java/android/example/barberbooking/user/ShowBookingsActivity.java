package android.example.barberbooking.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.example.barberbooking.MainActivity;
import android.example.barberbooking.R;
import android.example.barberbooking.adapter.HmServicesAdapter;
import android.example.barberbooking.adapter.ShowBookingsAdapter;
import android.example.barberbooking.common.Common;
import android.example.barberbooking.model.BookingModel;
import android.example.barberbooking.model.HomeVendorModel;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowBookingsActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    ImageView backBtn;
    RecyclerView recyclerView;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bookings);

        initialise();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getBookings();
        back();

    }

    private void back() {
        backBtn.setOnClickListener(v -> {
            ShowBookingsActivity.super.onBackPressed();
        });
    }

    private void getBookings() {
        List<BookingModel> list = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for (DataSnapshot sp : snapshot.getChildren()) {


                    BookingModel bookingModel = sp.getValue(BookingModel.class);



                    if (bookingModel.getUserPhone().equals(Common.currentUser.getPhone())) {

                        bookingModel.setBookingId(sp.getKey());

                        Log.i("TAG", "onDataChange: " + sp.getKey());

                        list.add(bookingModel);
                    }

                }
                ShowBookingsAdapter showBookingsAdapter = new ShowBookingsAdapter(list, ShowBookingsActivity.this);
                recyclerView.setAdapter(showBookingsAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initialise() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("bookings");

        backBtn =findViewById(R.id.backUserBookingBtn);
        recyclerView =findViewById(R.id.userBookingRecyclerView);

    }
}