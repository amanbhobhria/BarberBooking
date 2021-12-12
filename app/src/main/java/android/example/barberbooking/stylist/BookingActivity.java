package android.example.barberbooking.stylist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;

import android.example.barberbooking.R;
import android.example.barberbooking.common.Common;


import android.example.barberbooking.model.BookingModel;
import android.example.barberbooking.model.UserModel;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;


public class BookingActivity extends AppCompatActivity implements PaymentResultWithDataListener {

    LinearLayout changeAddressBtn;
    TextView name, phone, address, city, changeBtnTxt, nameOnImg, itemListTxt, priceTxt;
    UserModel userModel = Common.currentUser;
    BookingModel bookingModel = Common.currentBooking;
    Button makePaymentBtn;
    private String creditList, bookingId;
    private int balanceAmount, vendorAmount;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    DatabaseReference referenceSlot;
    DatabaseReference referencePayment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        firebaseReferences();

        initialize();

        getCommissionAmount();
        changeAddress();
        setAddress();


        others();

        makePaymentListener();




    }

    private void others() {
        itemListTxt.setText(bookingModel.getItemList());

        String priceFinal = "Price                          ₹" + bookingModel.getPricePaid();
        priceTxt.setText(priceFinal);
    }


    private void firebaseReferences() {

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("bookings");


        String daySLot = Common.currentBooking.getDateSlot();
        String slotNo = Common.currentUser.getSlotNo();
        String phChild = Common.currentStylist.getPhoneNo();


        referenceSlot = firebaseDatabase.getReference("hmvendor" + "/" + phChild + "/" + "days/" + daySLot + "/" + slotNo);


        //payment add in vendor account
        String vendorPhone = Common.currentBooking.getBycPhone();
        referencePayment = firebaseDatabase.getReference("payments/" + vendorPhone);


        referencePayment.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (snapshot.child("balance").exists()) {
                    try {


                        balanceAmount = snapshot.child("balance").getValue(int.class);


                    } catch (NullPointerException e) {
                        Toast.makeText(BookingActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    balanceAmount = 0;
                }


                if (snapshot.child("credit").exists()) {
                    creditList = snapshot.child("credit").getValue(String.class);
                } else {
                    creditList = "";
                    creditList = "";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void initialize() {

        changeAddressBtn = findViewById(R.id.changeAddressBTn);
        name = findViewById(R.id.uName);
        city = findViewById(R.id.uCity);
        address = findViewById(R.id.uRoad);
        phone = findViewById(R.id.uPhone);
        changeBtnTxt = findViewById(R.id.changetxt);
        nameOnImg = findViewById(R.id.nameOnimg);
        itemListTxt = findViewById(R.id.itemListTxt);
        priceTxt = findViewById(R.id.pricetoPayTxt);
        makePaymentBtn = findViewById(R.id.makePaymentBtn);


    }


    private void setAddress() {
        String phoneS, addressS, cityS;
        String nameS = userModel.getUserName();
        addressS = userModel.getRoadName();
        cityS = userModel.getCity();
        phoneS = userModel.getPhone();


        if (addressS == (null)) {
            name.setVisibility(View.GONE);
            address.setVisibility(View.GONE);
            city.setVisibility(View.GONE);
            phone.setVisibility(View.GONE);
            changeBtnTxt.setText(R.string.add_Adress);


        } else {
            bookingModel.setUserName(nameS);
            bookingModel.setUserAddress(addressS);
            bookingModel.setUserCity(cityS);
            bookingModel.setUserPhone(phoneS);


            name.setText(nameS);
            String hiName = "Hi " + nameS;
            nameOnImg.setText(hiName);
            address.setText(addressS);
            city.setText(cityS);
            phone.setText(phoneS);


        }


    }

    private void changeAddress() {
        changeAddressBtn.setOnClickListener(v -> {
            Intent intent = new Intent(BookingActivity.this, AddressActivity.class);
            startActivity(intent);
        });
    }


    private void getCommissionAmount() {
        int total = Integer.parseInt((Common.currentBooking.getPricePaid()));
        vendorAmount = (total * 80) / 100;


    }


    private void makePaymentListener() {
        makePaymentBtn.setOnClickListener(v -> {



            if (userModel.getPhone().equalsIgnoreCase("Guest")) {

                Toast.makeText(getApplicationContext(), "Login with your phone Number to Make a Booking", Toast.LENGTH_SHORT).show();

            } else if (bookingModel.getUserAddress() == (null) || (bookingModel.getUserName()==null))
            {
                Toast.makeText(getApplicationContext(), "Add name or address to make payment", Toast.LENGTH_SHORT).show();
            } else {

                startPayment();

            }






        });

    }


    private void startPayment() {
        Checkout checkout = new Checkout();
        bookingId = String.valueOf(System.currentTimeMillis());


        checkout.setKeyID("rzp_test_hU56KyMKDLQQxm");
        /**
         * Instantiate Checkout
         */


        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.byclogo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */

        try {
            JSONObject options = new JSONObject();

            options.put("name", "BYC");
            options.put("description", "Stylist Service booking");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //options.put("order_id", bookingId);//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", Common.currentBooking.getPricePaid() + "00");//pass amount in currency subunits
            //options.put("prefill.email", "aman.bhobhria@example.com");
            options.put("prefill.contact", Common.currentBooking.getUserPhone());
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);

        }

    }


    private void successMethod() {

        BookingModel bookingModel = Common.currentBooking;
        Common.currentBooking.setStatus("Pending");


        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm a", Locale.getDefault());
        String formattedDate = df.format(c.getTime());


        String track1 = "Booking Successful  " + formattedDate;
        Common.currentBooking.setTrackingList(track1);


        Common.currentBooking.setBookingId(bookingId);
        reference.child(bookingId).setValue(bookingModel);
        referenceSlot.setValue("Booked");


        //vendor account credited with pricepaid by user


//                For a 12-hour clock with suffix "AM" or "PM":

        DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDateAndTime = df2.format(new Date());

        String creditNew = bookingId + "           " + currentDateAndTime + "            Rs " + vendorAmount;
        if (creditList == null) {
            creditList = creditNew;
        } else {
            creditList = creditNew + " \n" + creditList;
        }
        referencePayment.child("credit").setValue(creditList);


        //Add Current Paid Amount into


        balanceAmount = balanceAmount + vendorAmount;
        referencePayment.child("balance").setValue(balanceAmount);


        Toast.makeText(BookingActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {

        Common.currentBooking.setTransactionId(s);
        successMethod();

        try {
            Intent intent = new Intent(BookingActivity.this, TransactionsDetailsActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(BookingActivity.this, "Intent  Error due to : " + e.toString(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Toast.makeText(BookingActivity.this, "Payment Error due to : " + s, Toast.LENGTH_SHORT).show();
    }



}