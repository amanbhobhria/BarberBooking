package android.example.barberbooking.stylist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.example.barberbooking.MainActivity;
import android.example.barberbooking.R;
import android.example.barberbooking.common.Common;

import android.example.barberbooking.model.UserModel;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.net.NetworkInfo;
import android.net.Uri;

import java.util.ArrayList;


public class BookingActivity extends AppCompatActivity {
    final int UPI_PAYMENT = 0;
    LinearLayout changeAddressBtn, topBookingLyt,exploreMoreLyt;
    TextView name, phone, address, city, changeBtnTxt, nameOnImg, itemListTxt, priceTxt, transactionDetailsTV,exploreMoreTxt;
    UserModel userModel = Common.currentDetails4;
    Button makePaymentBtn;
    private String nameS, phoneS, addressS, cityS;
    private  boolean paymentDone = false;



    @Override
    public void onBackPressed() {
        if (paymentDone) {
            System.out.println("Payment Done");

        } else {
            finish();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        initialize();
        changeAddress();
        setAddress();

        //add items and price
        itemListTxt.setText(userModel.getItemList());
        priceTxt.setText("Price                          ₹" + userModel.getPrice());


        makePaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userModel.getRoadName() == (null)) {
                    Toast.makeText(getApplicationContext(), "Add address to make payment", Toast.LENGTH_SHORT).show();
                } else {

                    makePayment();
                }


            }
        });


        exploreMoreTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });





    }



    private void initialize() {
        changeAddressBtn = findViewById(R.id.changeAddressBTn);
        topBookingLyt = findViewById(R.id.topBookingLyt);

        name = findViewById(R.id.uName);
        city = findViewById(R.id.uCity);
        address = findViewById(R.id.uRoad);
        phone = findViewById(R.id.uPhone);
        changeBtnTxt = findViewById(R.id.changetxt);
        nameOnImg = findViewById(R.id.nameOnimg);
        itemListTxt = findViewById(R.id.itemListTxt);
        priceTxt = findViewById(R.id.pricetoPayTxt);
        makePaymentBtn = findViewById(R.id.makePaymentBtn);
        transactionDetailsTV = findViewById(R.id.idTVTranscationDetails);

        exploreMoreLyt =findViewById(R.id.exploreMoreLyt);
        exploreMoreTxt =findViewById(R.id.exploreMoreTxt);


    }


    private void setAddress() {


        nameS = userModel.getUserName();
        addressS = userModel.getRoadName();
        phoneS = userModel.getPhone();
        cityS = userModel.getCity();

        if (addressS == (null)) {
            name.setVisibility(View.GONE);
            address.setVisibility(View.GONE);
            city.setVisibility(View.GONE);
            phone.setVisibility(View.GONE);
            changeBtnTxt.setText(R.string.add_Adress);


        } else {
            name.setText(nameS);
            nameOnImg.setText("Hi " + nameS);
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


    private void makePayment() {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault());
        String bookingId = df.format(c);
        // String amount = String.valueOf(userModel.getPrice());
        String upi = "bhobhriaaman333@okaxis";
        String desc = "Stylist Service Booking";
        String nameU = "Aman";

        payUsingUpi("1.0", upi, nameU, desc);


    }

    void payUsingUpi(String amount, String upiId, String name, String note) {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if (null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(BookingActivity.this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {

        //Test mode only
        String testmodeStatus = "success";
        String testmodeRefid = "30902098";


        if (isConnectionAvailable(BookingActivity.this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: " + str);
            String paymentCancel = "";
            if (str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";


            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if (equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    } else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                } else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }


            //For Test mode

            status = testmodeStatus;
            approvalRefNo = testmodeRefid;

            //


            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(BookingActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();

                transactionDetailsTV.setText("Transaction Successful with ref Id : " + approvalRefNo);
                topBookingLyt.setBackground(getDrawable(R.drawable.successsparkleimgeone));
                nameOnImg.setText("Congratulations " + nameS);
                paymentDone = true;
                exploreMoreLyt.setVisibility(View.VISIBLE);




                makePaymentBtn.setVisibility(View.GONE);
                changeAddressBtn.setVisibility(View.GONE);
                transactionDetailsTV.setVisibility(View.VISIBLE);

                Log.d("UPI", "responseStr: " + approvalRefNo);
            } else if ("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(BookingActivity.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(BookingActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(BookingActivity.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }


}