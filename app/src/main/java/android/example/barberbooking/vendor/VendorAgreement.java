package android.example.barberbooking.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.example.barberbooking.R;
import android.example.barberbooking.common.Common;
import android.example.barberbooking.vendor.homefacility.HmVendorDetail;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VendorAgreement extends AppCompatActivity {
    FloatingActionButton chatBtn;


    Button nextBtn, homeServbtn, statusBtn;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_agreement);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        initialize();
        //addPartner();
        chat();
        back();
        homeServ();
        viewStatus();
    }


    private void viewStatus() {
        statusBtn.setOnClickListener(v -> {

            Intent intent = new Intent(VendorAgreement.this, StatusActivity.class);
            startActivity(intent);


        });
    }

    private void homeServ() {
        homeServbtn.setOnClickListener(v -> {
            Intent intent = new Intent(VendorAgreement.this, HmVendorDetail.class);
            startActivity(intent);
        });
    }


    private void back() {
        backBtn = findViewById(R.id.backBtn1);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void initialize() {
        chatBtn = findViewById(R.id.chatBtn);

        homeServbtn = findViewById(R.id.homeServBtn);
        statusBtn = findViewById(R.id.statusbtn);


    }


    private void chat() {
        //Intent intent = new Intent(VendorAgreement.this, ChatA=.class);
        //startActivity(intent);
        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "+91  9518156020";
                String url = "https://api.whatsapp.com/send?phone=" + number + "&text=" + "Hi Sir I want to join with your app";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


    }

}

