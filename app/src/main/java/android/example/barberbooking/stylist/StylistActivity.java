package android.example.barberbooking.stylist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.example.barberbooking.CalenderActivity;
import android.example.barberbooking.R;
import android.example.barberbooking.adapter.SlotsAdapter;
import android.example.barberbooking.common.Common;
import android.example.barberbooking.model.BookingModel;
import android.example.barberbooking.model.SlotsModel;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class StylistActivity extends AppCompatActivity {
    ProgressBar slotsProgressbar;
    FloatingActionButton backBtn;
    ImageView stylistImage;
    TextView bycIdTxt, nameTxt, addressTxt, descriptionTxt, finalPriceTxt, stylistPolicy,
             dayTxt, timeTxt,makeupPrice, hairColourPrice, serviceCharge, priceTxt;
    Button addHairColorBtn, addManicureBtn, addWaxingBtn;
    LinearLayout bookNowBtn, dayLyt, nineLt, elevenLt, oneLt, threeLt, fiveLt, sevenLt;
    RelativeLayout makeupLyt, manicureLyt, hairColorLyt, waxingLyt, serviceLyt, totalPriceLyt;


FirebaseDatabase firebaseDatabase;
DatabaseReference reference;
RecyclerView slotsRecyclerView;

    private final String makeupString = "Makeup                          249\n";

    private String itemList;
    private float price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stylist);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setStatusBarTransparent();

        firebaseDatabase = FirebaseDatabase.getInstance();

        initialize();

        slotsProgressbar.setVisibility(View.VISIBLE);
        String phone = Common.currentStylist.getPhoneNo();
        reference = firebaseDatabase.getReference("hmvendor").child(phone).child("days");
        slotsRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        back();

        additional();
        dateSelector();
        getSlots();
        setData();

        book();

        getSelectedSlot();


    }

    private void getSelectedSlot() {




        if(Common.selectedSlot == null)
        {
          timeTxt.setText(R.string.select_time_slot);

        }
        else
        {

            timeTxt.setText(Common.selectedSlot);
            Toast.makeText(StylistActivity.this,Common.selectedSlot,Toast.LENGTH_SHORT).show();

        }
    }


    private void initialize() {
        backBtn = findViewById(R.id.backFLaoting);

        stylistImage = findViewById(R.id.specialistImg);

        bycIdTxt = findViewById(R.id.bycIdstylistBook);
        nameTxt = findViewById(R.id.namestylistBook);
        addressTxt = findViewById(R.id.addressStylistBook);
        descriptionTxt = findViewById(R.id.descriptionTxt);

        finalPriceTxt = findViewById(R.id.totalPriceBottom);

        stylistPolicy = findViewById(R.id.stylistPolicyBtn);

        addHairColorBtn = findViewById(R.id.addhaircolour);
        addManicureBtn = findViewById(R.id.addmanicureBtn);
        addWaxingBtn = findViewById(R.id.addbodywaxingBtn);

        bookNowBtn = findViewById(R.id.bookNowBtn);


        //Pricing
        makeupLyt = findViewById(R.id.basePriceLyt);
        hairColorLyt = findViewById(R.id.hairPriceLyt);
        manicureLyt = findViewById(R.id.manicurePriceLyt);
        waxingLyt = findViewById(R.id.waxingPriceLyt);
        serviceLyt = findViewById(R.id.serviceChargeLyt);

        hairColorLyt.setVisibility(View.GONE);
        manicureLyt.setVisibility(View.GONE);
        waxingLyt.setVisibility(View.GONE);


        priceTxt = findViewById(R.id.totalPrice);
        makeupPrice = findViewById(R.id.makeupPrice);
        serviceCharge = findViewById(R.id.serviceCharge);
        price = price + 40;//Service Charge


        dayLyt = findViewById(R.id.dayLayt);
        timeTxt = findViewById(R.id.timeTxtView);
        dayTxt = findViewById(R.id.dayTxtView);

        nineLt = findViewById(R.id.nine);
        elevenLt = findViewById(R.id.eleven);
        oneLt = findViewById(R.id.one);
        threeLt = findViewById(R.id.three);
        fiveLt = findViewById(R.id.five);
        sevenLt = findViewById(R.id.seven);

        slotsRecyclerView = findViewById(R.id.slots_recyler_view);
        slotsProgressbar = findViewById(R.id.slotsProgressBar);


    }


    private void pricing() {
        String priceSh = "₹" + price;
        priceTxt.setText(priceSh);
        finalPriceTxt.setText(priceSh);
    }


    private void additional() {
        addHairColorBtn.setOnClickListener(v -> {
            if (addHairColorBtn.getText().toString().equals("Add")) {
                addHairColorBtn.setText(R.string.added);
                price = price + 100;
                // itemList = itemList + "Hair colour            100\n";

                hairColorLyt.setVisibility(View.VISIBLE);
            } else {
                addHairColorBtn.setText(R.string.add);
                price = price - 100;
                hairColorLyt.setVisibility(View.GONE);
            }
            pricing();
        });
        addWaxingBtn.setOnClickListener(v -> {
            if (addWaxingBtn.getText().toString().equals("Add")) {
                price = price + 500;
                //itemList = itemList + "Waxing                  500\n";
                addWaxingBtn.setText(R.string.added);
                waxingLyt.setVisibility(View.VISIBLE);

            } else {
                addWaxingBtn.setText(R.string.add);
                price = price - 500;
                waxingLyt.setVisibility(View.GONE);
            }

            pricing();
        });

        addManicureBtn.setOnClickListener(v -> {

            if (addManicureBtn.getText().toString().equals("Add")) {
                price = price + 150;

                addManicureBtn.setText(R.string.added);
                manicureLyt.setVisibility(View.VISIBLE);

            } else {
                addManicureBtn.setText(R.string.add);
                price = price - 150;
                manicureLyt.setVisibility(View.GONE);

            }
            pricing();
        });
    }


    private void back() {
        backBtn.setOnClickListener(v -> finish());
    }


    private void setData() {
        Picasso.get()
                .load(Common.currentStylist.getWorkImg())
                .into(stylistImage);

        String bycId = "BYC" + Common.currentStylist.getBycId();
        String address = Common.currentStylist.getAddress() + "," + Common.currentStylist.getCity();
        bycIdTxt.setText(bycId);
        nameTxt.setText(Common.currentStylist.getOwnername());
        addressTxt.setText(address);

        descriptionTxt.setText(Common.currentStylist.getServiceList());


    }


    private void setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            View decorView = window.getDecorView();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }




    public void dateSelector() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM", Locale.getDefault());

        String getCurrentDate = sdf.format(c.getTime());


        if (Common.date == null) {


            dayTxt.setText(getCurrentDate);
            Common.date = getCurrentDate;

        } else {
            dayTxt.setText(Common.date);
        }

        dayLyt.setOnClickListener(v -> {
            Intent intent = new Intent(StylistActivity.this, CalenderActivity.class);
            startActivity(intent);
        });



    }

    private void getSlots() {

        List<SlotsModel> list = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for (DataSnapshot sp : snapshot.getChildren()) {


                    SlotsModel slotsModel = sp.getValue(SlotsModel.class);

                    assert slotsModel != null;




                    if (sp.getKey().equals(Common.date)) {
                        slotsModel.setDateId(sp.getKey());
                        list.add(slotsModel);
                    }

                }
                SlotsAdapter slotsAdapter = new SlotsAdapter(list, StylistActivity.this);
                slotsRecyclerView.setAdapter(slotsAdapter);
                slotsProgressbar.setVisibility(View.GONE);
                slotsRecyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void itemAdd() {

        if (addManicureBtn.getText().toString().equalsIgnoreCase("Added")) {
            itemList = itemList + "Manicure                       150\n";
        }
        if (addWaxingBtn.getText().toString().equalsIgnoreCase("Added")) {
            itemList = itemList + "Waxing                          500\n";
        }
        if (addHairColorBtn.getText().toString().equalsIgnoreCase("Added")) {
            itemList = itemList + "Hair Colour                    100\n";
        }
        itemList = itemList + "Service Charge               40\n";
//        Common.currentUser.setPrice(price);

    }


    private void book() {
        bookNowBtn.setOnClickListener(v -> {
            itemList = "";
            itemList = makeupString;
            itemAdd();

            //Common.currentUser.setItemList(itemList);
            BookingModel bookingModel = new BookingModel();
            bookingModel.setBookingId(Common.currentStylist.getBycId());
            bookingModel.setBycPhone(Common.currentStylist.getPhoneNo());
            bookingModel.setPricePaid(String.valueOf(price));
            
            Intent intent = new Intent(StylistActivity.this, BookingActivity.class);
            startActivity(intent);

        });
    }

}