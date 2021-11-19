package android.example.barberbooking.vendor.homefacility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.example.barberbooking.MainActivity;
import android.example.barberbooking.R;
import android.example.barberbooking.common.Common;
import android.example.barberbooking.model.HomeVendorModel;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class SubmitHmVendorActivity extends AppCompatActivity {
    TextView owner, phone, email, city, address, bankName, bankAddress, beneficiary, accNo, accType, ifsc, idNo, idType, services, editBtn, referenceId, home;
    CheckBox agreeChkBx;
    Button submitBtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;





    @Override
    public void onBackPressed() {
// super.onBackPressed();
// Not calling **super**, disables back button in current screen.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_hm_vendor);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("hmvendor");



        initialize();
        getData();
        submit();
        returnHome();

    }



    private void initialize() {
        //Personal
        owner = findViewById(R.id.oNamehmSbmt);
        phone = findViewById(R.id.phonehmSbmt);
        email = findViewById(R.id.emailhmSbmt);
        city = findViewById(R.id.cityhmSbmt);

        address = findViewById(R.id.addresshmSbmt);

        //Bank
        bankName = findViewById(R.id.bankNameSbmt);
        bankAddress = findViewById(R.id.bnkAdressSbmt);
        beneficiary = findViewById(R.id.benifNameSbmt);
        accNo = findViewById(R.id.accountNumhmSbmt);
        accType = findViewById(R.id.acctypeSbmt);
        ifsc = findViewById(R.id.ifscSbmt);



        //Govt Document detail
        idNo = findViewById(R.id.idNoSbmt);
        idType = findViewById(R.id.idTypeSbmt);


        //others
        services = findViewById(R.id.servicesHm);
        agreeChkBx = findViewById(R.id.agreeSubmithmCheckBox);
        submitBtn = findViewById(R.id.submitFormhmBtn);

        editBtn = findViewById(R.id.edithmBtn);
        home = findViewById(R.id.returnhmHome);






    }

    private void getData()  {

        owner.setText(Common.currentHmVendor.getOwnerName());
        phone.setText(Common.currentHmVendor.getPhoneNoAdd());
        email.setText(Common.currentHmVendor.getEmail());
        city.setText(Common.currentHmVendor.getCity());
        address.setText(Common.currentHmVendor.getAddress());

        bankName.setText(Common.currentHmVendor.getBankname());
        bankAddress.setText(Common.currentHmVendor.getBankadress());
        beneficiary.setText(Common.currentHmVendor.getBenificaryname());
        accNo.setText(Common.currentHmVendor.getAccountNo());
        accType.setText(Common.currentHmVendor.getAccounttype());
        ifsc.setText(Common.currentHmVendor.getIfsccode());





        idNo.setText(Common.currentHmVendor.getGovtidno());
        idType.setText(Common.currentHmVendor.getDocType());


        services.setText(Common.currentHmVendor.getServiceList());

    }

    private void submit() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(agreeChkBx.isChecked()) {


                    String id = Common.currentHmVendor.getPhoneNo();
                    String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
                    HomeVendorModel homeVendorModel = Common.currentHmVendor;
                    homeVendorModel.setTime(currentDateTimeString);
                    homeVendorModel.setStatus("Pending");


                    reference.child(id).setValue(homeVendorModel);

                    Toast.makeText(getApplicationContext(), "Request Sent successfully  ", Toast.LENGTH_SHORT).show();


                    Common.isUploaded = true;
                    agreeChkBx.setVisibility(View.GONE);
                    submitBtn.setVisibility(View.GONE);
                    editBtn.setVisibility(View.GONE);
                    home.setVisibility(View.VISIBLE);
                }
                else
                {
                    agreeChkBx.requestFocus();
                    Toast.makeText(getApplicationContext(), "Please Tick the checkbox before submitting " , Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    private void returnHome(){
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubmitHmVendorActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }











}