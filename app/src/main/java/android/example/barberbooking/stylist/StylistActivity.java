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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
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
    ImageView stylistImage, belowIcon, basicServiceTick, hairColorTick, manicureTick, waxingTick;
    TextView bycIdTxt, nameTxt, addressTxt, descriptionTxt, finalPriceTxt, stylistPolicy,
            dayTxt, timeTxt, makeupPrice, serviceCharge, priceTxt, availableTxtView, basicPriceAddTxt;
    Button addHairColorBtn, addManicureBtn, addWaxingBtn, addBasicPriceBtn;
    LinearLayout bookNowBtn, dayLyt, nineLt, elevenLt, oneLt, threeLt, fiveLt, sevenLt;
    RelativeLayout makeupLyt, manicureLyt, hairColorLyt, waxingLyt, basicPriceLyt;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    RecyclerView slotsRecyclerView;


    private String itemList;
    private int price, basicPrice, bridalPrice;


    Handler handler = new Handler();
    Runnable runnable;
    int delay = 0;

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Common.currentUser.setSlotTime(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stylist);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setStatusBarTransparent();

        //For previous demo vendors only
        setBase();

        firebaseDatabase = FirebaseDatabase.getInstance();

        initialize();


        slotsProgressbar.setVisibility(View.VISIBLE);
        String phone = Common.currentStylist.getPhoneNo();
        reference = firebaseDatabase.getReference("hmvendor").child(phone).child("days");
        slotsRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        back();

        additional();
        dateSelector();

        slotsHint();

        getSlots();
        setData();

        book();
        pricing();

        stylistPolicy();


    }

    private void stylistPolicy() {
        stylistPolicy.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://pages.flycricket.io/byc/terms.html"));
            startActivity(intent);
        }
        );

    }

    private void setBase() {
        try {
            if (Common.currentStylist.getBasePrice() == null) {
                basicPrice = 249;
            } else {
                basicPrice = Integer.parseInt(Common.currentStylist.getBasePrice());
            }
            price = basicPrice;
        } catch (Exception e) {
            Toast.makeText(StylistActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }


    }

    private void slotsHint() {
        timeTxt.setOnClickListener(v -> {

            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(50); //You can manage the blinking time with this parameter
            anim.setStartOffset(20);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(33);
            availableTxtView.startAnimation(anim);
            availableTxtView.setTextColor(Color.RED);
            belowIcon.setVisibility(View.VISIBLE);

        });
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
        addBasicPriceBtn = findViewById(R.id.addbasicServiceBtn);

        bookNowBtn = findViewById(R.id.bookNowBtn);


        //Pricing
        makeupLyt = findViewById(R.id.basePriceLyt);
        hairColorLyt = findViewById(R.id.hairPriceLyt);
        manicureLyt = findViewById(R.id.manicurePriceLyt);
        waxingLyt = findViewById(R.id.waxingPriceLyt);
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

        slotsRecyclerView = findViewById(R.id.slots_recycler_view);
        slotsProgressbar = findViewById(R.id.slotsProgressBar);

        availableTxtView = findViewById(R.id.availableTxtView);

        belowIcon = findViewById(R.id.below_slots_icon);


        hairColorTick = findViewById(R.id.hairColorTick);
        manicureTick = findViewById(R.id.manicureTick);
        waxingTick = findViewById(R.id.waxingTick);
        basicServiceTick = findViewById(R.id.basicServiceTick);
        basicPriceLyt = findViewById(R.id.basicPriceLyt);
        basicPriceAddTxt = findViewById(R.id.basicPriceTxt);

        String myBaseP = "₹" + basicPrice;
        basicPriceAddTxt.setText(myBaseP);
        makeupPrice.setText(myBaseP);


    }

    private void getSelectedSlot() {


        if (Common.currentUser.getSlotTime() == null) {
            timeTxt.setText(R.string.select_time_slot);


        } else {

            timeTxt.setText(Common.currentUser.getSlotTime());
            timeTxt.setTextColor(Color.RED);


        }


    }


    private void pricing() {


        String priceSh = "₹" + price;

        priceTxt.setText(priceSh);
        finalPriceTxt.setText(priceSh);
    }


    private void additional() {
        addBasicPriceBtn.setOnClickListener(v -> {

            if (addBasicPriceBtn.getText().toString().equals("Add")) {
                addBasicPriceBtn.setText(R.string.added);
                basicServiceTick.setVisibility(View.VISIBLE);
                price = price + basicPrice;
                itemList = itemList + "Basic Price                    " + basicPrice + "\n";
                basicPriceLyt.setVisibility(View.VISIBLE);
            } else {
                addBasicPriceBtn.setText(R.string.add);
                price = price - basicPrice;
                hairColorLyt.setVisibility(View.GONE);
                basicServiceTick.setVisibility(View.GONE);
                basicPriceLyt.setVisibility(View.GONE);
            }
            pricing();


        });


        addHairColorBtn.setOnClickListener(v -> {
            if (addHairColorBtn.getText().toString().equals("Add")) {
                addHairColorBtn.setText(R.string.added);
                hairColorTick.setVisibility(View.VISIBLE);
                price = price + 100;
                itemList = itemList + "Hair colour            100\n";
                hairColorLyt.setVisibility(View.VISIBLE);
            } else {
                addHairColorBtn.setText(R.string.add);
                price = price - 100;
                hairColorLyt.setVisibility(View.GONE);
                hairColorTick.setVisibility(View.GONE);
            }
            pricing();
        });
        addWaxingBtn.setOnClickListener(v -> {
            if (addWaxingBtn.getText().toString().equals("Add")) {
                waxingTick.setVisibility(View.VISIBLE);
                price = price + 500;
                itemList = itemList + "Waxing                  500\n";
                addWaxingBtn.setText(R.string.added);
                waxingLyt.setVisibility(View.VISIBLE);

            } else {
                waxingTick.setVisibility(View.GONE);
                addWaxingBtn.setText(R.string.add);
                price = price - 500;
                waxingLyt.setVisibility(View.GONE);
            }

            pricing();
        });

        addManicureBtn.setOnClickListener(v -> {

            if (addManicureBtn.getText().toString().equals("Add")) {
                manicureTick.setVisibility(View.VISIBLE);
                price = price + 150;
                itemList = itemList + "Manicure               100\n";
                addManicureBtn.setText(R.string.added);
                manicureLyt.setVisibility(View.VISIBLE);

            } else {
                manicureTick.setVisibility(View.GONE);
                addManicureBtn.setText(R.string.add);
                price = price - 150;
                manicureLyt.setVisibility(View.GONE);

            }
            pricing();
        });
    }


    private void back() {
        backBtn.setOnClickListener(v -> StylistActivity.super.onBackPressed());
        Common.currentUser.setSlotTime(null);
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

        descriptionTxt.setText(R.string.description_stylist);


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
        if (addBasicPriceBtn.getText().toString().equalsIgnoreCase("Added")) {
            itemList = itemList + "Basic Service                " + basicPrice + "\n";
        }


        if (addManicureBtn.getText().toString().equalsIgnoreCase("Added")) {
            itemList = itemList + "Manicure                       150\n";
        }
        if (addWaxingBtn.getText().toString().equalsIgnoreCase("Added")) {
            itemList = itemList + "Waxing                          500\n";
        }
        if (addHairColorBtn.getText().toString().equalsIgnoreCase("Added")) {
            itemList = itemList + "Hair Colour                    100\n";
        }
        itemList = itemList + "Service Charge             40\n";


    }


    private void book() {
        bookNowBtn.setOnClickListener(v -> {
            itemList = "";


            if (timeTxt.getText().equals("Select Time Slot")) {
                Toast.makeText(StylistActivity.this, "Please Select a time slot for booking.", Toast.LENGTH_SHORT).show();
            } else if (price < 160) {
                Toast.makeText(StylistActivity.this, "Minimum Booking amount is ₹200", Toast.LENGTH_SHORT).show();
            } else {
                try {

                    itemAdd();
                    BookingModel bookingModel = new BookingModel();
                    bookingModel.setBycId(Common.currentStylist.getBycId());
                    bookingModel.setBycPhone(Common.currentStylist.getPhoneNo());
                    bookingModel.setItemList(itemList);
                    bookingModel.setDateSlot(dayTxt.getText().toString());
                    bookingModel.setBycName(Common.currentStylist.getOwnername());
                    bookingModel.setTimeSlot(timeTxt.getText().toString());
                    bookingModel.setPricePaid(String.valueOf(price));


                    Common.currentBooking = bookingModel;


                    Intent intent = new Intent(StylistActivity.this, BookingActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(StylistActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }


        });
    }


    @Override
    protected void onResume() {
        handler.postDelayed(runnable = () -> {
            handler.postDelayed(runnable, delay);
            getSelectedSlot();
        }, delay);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
    }

}