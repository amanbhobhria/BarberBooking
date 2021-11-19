package android.example.barberbooking.vendor.homefacility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.example.barberbooking.R;
import android.example.barberbooking.common.Common;
import android.example.barberbooking.model.HomeVendorModel;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class HmVendorDetail extends AppCompatActivity {
    EditText name, phoneAdd, email, address;
    TextView phoneTxt;
    Button nextBtn;
    MaterialSpinner citySpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hm_vendor_detail);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        initialize();
        cityList();
        phoneTxt.setText(Common.currentUser.getPhone());
        next();


    }

    private void initialize() {
        name = findViewById(R.id.hmownerNameTxt);
        phoneAdd = findViewById(R.id.hmphoneNoTxt);
        phoneTxt = findViewById(R.id.phoneHmTxt);
        email = findViewById(R.id.hmemailIdTxt);
        address = findViewById(R.id.hmroad);
        nextBtn = findViewById(R.id.savePersonalDetailBtn);
        citySpin = findViewById(R.id.hmcityListSpin);


    }


    private void cityList() {

        citySpin.setItems("Sirsa City ", "Rania", "Ellenabad", "Baragudha", "Dabwali", "Nathusary Chopta", "Odhan", "Chakan", "Keharwala", "Bhoona", "Mamber Khera");
        citySpin.setOnItemSelectedListener((MaterialSpinner.OnItemSelectedListener<String>) (view, position, id, item) -> Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show());


        citySpin.setOnClickListener(v -> {
            citySpin.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        });

    }


    private void next() {
        nextBtn.setOnClickListener(v -> {
            upload();
            Intent intent = new Intent(HmVendorDetail.this, BankingDetailActivityHm.class);
            startActivity(intent);
        });
    }


    private void upload() {
        //using model
        HomeVendorModel homeVendorModel = new HomeVendorModel();

        homeVendorModel.setPhoneNo(Common.currentUser.getPhone());

        homeVendorModel.setOwnerName(name.getText().toString());
        homeVendorModel.setPhoneNoAdd(phoneAdd.getText().toString());
        homeVendorModel.setEmail(email.getText().toString());
        homeVendorModel.setCity(citySpin.getText().toString());
        homeVendorModel.setAddress(address.getText().toString());


        Common.currentHmVendor = homeVendorModel;


    }
}