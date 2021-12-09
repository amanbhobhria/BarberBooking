package android.example.barberbooking.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.example.barberbooking.MainActivity;
import android.example.barberbooking.R;
import android.example.barberbooking.common.Common;
import android.example.barberbooking.model.BookingModel;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class ShowBookingDetailsActivity extends AppCompatActivity {
    ImageView upBtn, belowBtn;
    TextView userNameTxt, totalPriceTxt, priceDetailsTxt, bycIdTxt, userAddressTxt, userCityTxt, bookingIdTxt, slotTimeTxt, slotDateTxt, trackingListTxt;
    LinearLayout viewFullDetailsLyt;
    Button callInquiryBtn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(ShowBookingDetailsActivity.this, MainActivity.class);
        startActivity(intent);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_booking_detials);
        initialise();
        viewMoreDetail();


        getBookingData();
        getTracking();
        inquiry();
    }

    private void inquiry() {
        callInquiryBtn.setOnClickListener(v -> {
            try {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "95181 56020"));//change the number
                startActivity(callIntent);
            } catch (Exception e) {
                Toast.makeText(ShowBookingDetailsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewMoreDetail() {
        upBtn.setOnClickListener(v -> {
            belowBtn.setVisibility(View.VISIBLE);
            upBtn.setVisibility(View.GONE);
            viewFullDetailsLyt.setVisibility(View.GONE);

        });

        belowBtn.setOnClickListener(v -> {
            upBtn.setVisibility(View.VISIBLE);
            belowBtn.setVisibility(View.GONE);
            viewFullDetailsLyt.setVisibility(View.VISIBLE);

        });
    }

    private void getBookingData() {
        BookingModel bookingModel = Common.currentBooking;

        String hiUser = "Hi " + bookingModel.getUserName();
        userNameTxt.setText(hiUser);

        String totalPrice = "₹" + bookingModel.getPricePaid();
        totalPriceTxt.setText(totalPrice);

        priceDetailsTxt.setText(bookingModel.getItemList());

        bycIdTxt.setText(bookingModel.getBycId());

        userAddressTxt.setText(bookingModel.getUserAddress());
        userCityTxt.setText(bookingModel.getUserCity());

        String bookingId = "Booking ID :" + bookingModel.getBookingId();
        bookingIdTxt.setText(bookingId);

        String time = "Time Slot : " + bookingModel.getTimeSlot();
        slotTimeTxt.setText(time);

        String date = "Slot Date : " + bookingModel.getDateSlot();
        slotDateTxt.setText(date);


    }

    private void getTracking() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("bookings/" + Common.currentBooking.getBookingId() + "/trackingList");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trackingListTxt.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void initialise() {
        upBtn = findViewById(R.id.upBookingDetailsBtn);
        belowBtn = findViewById(R.id.belowBookingDetailsBtn);
        viewFullDetailsLyt = findViewById(R.id.viewFullDetailsLyt);
        userNameTxt = findViewById(R.id.hiUserTxt);
        totalPriceTxt = findViewById(R.id.pricePaidBg);
        priceDetailsTxt = findViewById(R.id.paymentDetailsBgTxt);
        bycIdTxt = findViewById(R.id.bycIdBG);
        userAddressTxt = findViewById(R.id.userAddressBG);
        userCityTxt = findViewById(R.id.usercityBG);
        bookingIdTxt = findViewById(R.id.bookingIdTxtBG);
        slotTimeTxt = findViewById(R.id.bookingSlotTxtBG);
        slotDateTxt = findViewById(R.id.bookingSlotDateTxtBG);
        trackingListTxt = findViewById(R.id.trackingListBG);
        callInquiryBtn = findViewById(R.id.callBtnBG);


    }
}