package android.example.barberbooking.stylist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.example.barberbooking.CalenderActivity;
import android.example.barberbooking.R;
import android.example.barberbooking.common.Common;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class StylistActivity extends AppCompatActivity {
    FloatingActionButton backBtn;
    ImageView stylistImage;
    TextView bycIdTxt,nameTxt, addressTxt, descriptionTxt, finalPriceTxt, stylistPolicy, dayTxt, timeTxt;
    Button addHairColorBtn, addManicureBtn, addWaxingBtn;
    LinearLayout bookNowBtn, dayLyt, nineLt, elevenLt, oneLt, threeLt, fiveLt, sevenLt;
    RelativeLayout makeupLyt, manicureLyt, hairColorLyt, waxingLyt, serviceLyt, totalPriceLyt;
    //Pricing TextViews
    TextView makeupPrice, hairColourPrice, serviceCharge, priceTxt;

    private String slot1, slot2, slot3, slot4, slot5, slot6;

    private final String makupString = "Makeup                          249\n";

    private String itemList;
    private float price = 259;
    private final int serviceCh = 40;


//    @Override
//    public void onBackPressed() {
//        finish();
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stylist);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setStatusBarTransparent();


        initialize();

        timeSlots();
        back();

        additional();
        setData();
        pricing();
        dateSelector();


        book();


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

    }


    private void timeSlots() {
        slot1 = getString(R.string.slot1);
        slot2 = getString(R.string.slot2);
        slot3 = getString(R.string.slot3);
        slot4 = getString(R.string.slot4);
        slot5 = getString(R.string.slot5);
        slot6 = getString(R.string.slot6);


    }


    private void pricing() {

        priceTxt.setText("₹" + price);
        finalPriceTxt.setText("₹" + price);
    }

    private void additional() {
        addHairColorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
        addWaxingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        addManicureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
            }
        });
    }


    private void back() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void setData() {
        Picasso.get()
                .load(Common.currentDetails3.getWorkImg())
                .into(stylistImage);

        bycIdTxt.setText("BYC"+Common.currentDetails3.getBycId());
        nameTxt.setText(Common.currentDetails3.getOwnername());
        addressTxt.setText(Common.currentDetails3.getAddress() + "," + Common.currentDetails3.getCity());

        descriptionTxt.setText(Common.currentDetails3.getServiceList());


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


    private void timing(LinearLayout button, String timeSlot) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeTxt.setText(timeSlot);

            }
        });
    }


    public void dateSelector() {

        if (Common.date == null) {
            dayTxt.setText("Today");
        } else {
            dayTxt.setText(Common.date);
        }

        dayLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StylistActivity.this, CalenderActivity.class);
                startActivity(intent);
            }
        });

        timing(nineLt, slot1);
        timing(elevenLt, slot2);
        timing(oneLt, slot3);
        timing(threeLt, slot4);
        timing(fiveLt, slot5);
        timing(sevenLt, slot6);

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
        Common.currentDetails4.setPrice(price);

    }


    private void book() {
        bookNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList ="";
                itemList =  makupString;
                itemAdd();

                Common.currentDetails4.setItemList(itemList);
                Intent intent = new Intent(StylistActivity.this, BookingActivity.class);
                startActivity(intent);

            }
        });
    }

}