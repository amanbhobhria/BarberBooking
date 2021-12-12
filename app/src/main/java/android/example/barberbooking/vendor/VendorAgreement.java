package android.example.barberbooking.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.example.barberbooking.R;
import android.example.barberbooking.common.Common;
import android.example.barberbooking.vendor.homefacility.HmVendorDetail;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

            checkExistence(Common.currentUser.getPhone());

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

    private void checkExistence(String phoneNo) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userNameRef = rootRef.child("hmvendor").child(phoneNo);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //Send Otp
                    Toast.makeText(VendorAgreement.this, "Vendor Account already exist on this number", Toast.LENGTH_SHORT).show();



                } else {
                    Intent intent = new Intent(VendorAgreement.this, HmVendorDetail.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(VendorAgreement.this, "Cancelled " + databaseError.toString(), Toast.LENGTH_SHORT).show();
                Log.d("dbaerror", databaseError.getMessage()); //Don't ignore errors!
            }
        };
        userNameRef.addListenerForSingleValueEvent(eventListener);
    }



}

