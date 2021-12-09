package android.example.barberbooking.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.example.barberbooking.R;
import android.example.barberbooking.common.Common;
import android.example.barberbooking.model.VendorModel;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class VendorDetail extends AppCompatActivity {
    EditText name,saloonName, aadhar, phone,roadName;
    Button saveBtn;
    ImageView backBtn;
    MaterialSpinner citySpin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_detail);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        initialize();
        back();
        save();
        cityList();
        edit();


    }

    private void edit() {
        if(Common.isEdit)
        {
            name.setText(Common.currentVendor.getOwnerName());
            saloonName.setText(Common.currentVendor.getSaloonName());
            roadName.setText(Common.currentVendor.getAddress());
            citySpin.setText(Common.currentVendor.getCity());
            phone.setText(Common.currentVendor.getPhoneNo());
            aadhar.setText(Common.currentVendor.getAddress());


        }
    }

    private void save() {
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate();

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


    private void initialize() {
        name = findViewById(R.id.ownerNameTxt);
        saloonName = findViewById(R.id.salNameTxt);
        aadhar = findViewById(R.id.aadharNoTxt);
        phone = findViewById(R.id.phoneNoTxt);
        roadName = findViewById(R.id.road);
        saveBtn = findViewById(R.id.saveVendorDetail);
        backBtn = findViewById(R.id.backBtn2);


        citySpin = (MaterialSpinner) findViewById(R.id.cityListSpin);
        citySpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                citySpin.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
            }
        });
    }





    private void validate() {

        if (name.getText().toString().equals("")) {
            name.requestFocus();
            //Toast.makeText(getApplicationContext(), "*Owner name required", Toast.LENGTH_SHORT).show();
            name.setHintTextColor(Color.RED);

        } else if (aadhar.getText().toString().equals("")) {
            aadhar.requestFocus();
            aadhar.setHint("*required");
            aadhar.setHintTextColor(Color.RED);

        } else if (phone.getText().toString().equals("")) {
            phone.requestFocus();
            phone.setHintTextColor(Color.RED);
            // Toast.makeText(getApplicationContext(), "*Phone Number  required", Toast.LENGTH_SHORT).show();
        } else {
            submit();
            Intent intent = new Intent(VendorDetail.this, FacilitiesSelectActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Saved  " + name.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void submit() {
        //using model
        VendorModel vendorModel = new VendorModel();

        vendorModel.setOwnerName(name.getText().toString());
        vendorModel.setPhoneNo(phone.getText().toString());
        vendorModel.setSaloonName(saloonName.getText().toString());
        vendorModel.setAadharNo(aadhar.getText().toString());
        vendorModel.setAddress(roadName.getText().toString());
        vendorModel.setCity(citySpin.getText().toString());

        Common.currentVendor = vendorModel;


    }


    private void cityList() {

        citySpin.setItems("Sirsa City" ,"Kehrwala", "Chakkan","Bhunna", "Ellenabad","Kharian","Rasalia","Mammad");
      //  String extraCities = "\"Rania\",\"Nathusary Chopta\"\"Baragudha\","

        //citySpin.setOnItemSelectedListener((MaterialSpinner.OnItemSelectedListener<String>) (view, position, id, item) -> Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show());


    }


}