package android.example.barberbooking.stylist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.example.barberbooking.MainActivity;
import android.example.barberbooking.R;
import android.example.barberbooking.common.Common;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TransactionsDetailsActivity extends AppCompatActivity {
    TextView transactionId,amountTxt,dateSlotTxt,timeSlotTxt,bookingTimeTxt,doneTxt,bookingIdTxt;


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transctions_details);
        initialise();
        done();
        setData();


    }

    private void setData() {

            String formattedAmount = "₹"+Common.currentBooking.getPricePaid();
            amountTxt.setText(formattedAmount);
            dateSlotTxt.setText(Common.currentBooking.getDateSlot());
            timeSlotTxt.setText(Common.currentBooking.getTimeSlot());
            transactionId.setText(Common.currentBooking.getTransactionId());

            String booking = "Booking Id "+Common.currentBooking.getBookingId();
            bookingIdTxt.setText(booking);


            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy ,HH:mm a", Locale.getDefault());
            String dateF = df.format(calendar.getTime());


            bookingTimeTxt.setText(dateF);



    }

    private void done() {
        doneTxt.setOnClickListener(v -> {
            Intent intent = new Intent(TransactionsDetailsActivity.this, MainActivity.class);
            startActivity(intent);
            finishAffinity();
        });
    }

    private void initialise() {
        bookingIdTxt =findViewById(R.id.bookingIdTxt);
        transactionId =findViewById(R.id.transactionIdTxt);
        amountTxt =findViewById(R.id.amountPaidTxt);
        dateSlotTxt =findViewById(R.id.slotDateTxt);
        timeSlotTxt = findViewById(R.id.bookedSlotTxt);
        bookingTimeTxt =findViewById(R.id.bookingTimeTxt);
        doneTxt = findViewById(R.id.doneTxt);



    }
}