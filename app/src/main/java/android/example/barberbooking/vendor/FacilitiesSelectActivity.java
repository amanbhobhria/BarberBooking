package android.example.barberbooking.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.example.barberbooking.R;
import android.example.barberbooking.common.Common;
import android.example.barberbooking.model.VendorModel;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class FacilitiesSelectActivity extends AppCompatActivity {
    Button addBtn;
    CheckBox hairBx, shavebx, spawBx, beautyBx, facialBx;
    MaterialSpinner genderSpin, cPrice, sPrice, spawPrice, facPrice, bPPrice;
    LinearLayout menLyt;
    RelativeLayout womenLayt;
    String servicesList = "";
    private int count = 0;

    @Override
    public void onBackPressed() {
// super.onBackPressed();
// Not calling **super**, disables back button in current screen.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facilities_select);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        intialize();
        maleService();
        feamleService();
        gender();
        back();
        submit();


    }




    private void intialize() {
        addBtn = findViewById(R.id.addDataBtn);

        hairBx = findViewById(R.id.hcutBox);
        shavebx = findViewById(R.id.shaveBox);
        spawBx = findViewById(R.id.spawBox);
        beautyBx = findViewById(R.id.beautyParlourBox);
        facialBx = findViewById(R.id.facialBox);

        menLyt = findViewById(R.id.mensFaclLyt);
        womenLayt = findViewById(R.id.wommensFaclLyt);
        genderSpin = (MaterialSpinner) findViewById(R.id.genderSpinFacility);


        cPrice = (MaterialSpinner) findViewById(R.id.cuttingpriceSpin);
        sPrice = (MaterialSpinner) findViewById(R.id.shavingPriceSpin);
        spawPrice = (MaterialSpinner) findViewById(R.id.spawPriceSpin);
        facPrice = (MaterialSpinner) findViewById(R.id.facialPriceSpin);
        bPPrice = (MaterialSpinner) findViewById(R.id.beautyParlourPriceSpin);


    }


    private void gender() {


        genderSpin.setItems("Male only ", "Female Only", "Bisexual");
        genderSpin.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();


            }
        });


    }


    private void maleService() {


        cPrice.setItems(50, 70, 100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700);
        sPrice.setItems(50, 70, 100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700);
        spawPrice.setItems(50, 70, 100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700);
       facPrice.setItems(50, 70, 100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700);



    }

    private void feamleService() {

        bPPrice.setItems(400, 500, 600, 1000, 1200, 1500, 1800, 2000, 2500, 2800, 3000, 3500, 4000);
    }

    private void submit() {
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addService();


                if (count == 0) {
                    Toast.makeText(getApplicationContext(), "Select minimum one option", Toast.LENGTH_SHORT).show();
                } else {
                    VendorModel vendorModel = Common.currentDetails;
                    vendorModel.setServiceList(servicesList);
                    vendorModel.setGender(genderSpin.getText().toString());
                    //Common.currentFacilities = vendorModel;



                    Intent intent = new Intent(FacilitiesSelectActivity.this, SubmitFormActivity
                            .class);
                    startActivity(intent);
                }
            }
        });


    }

    private void addService() {

        if (hairBx.isChecked()) {


            count = count + 1;
            servicesList = hairBx.getText().toString() + " : " + cPrice.getText().toString() + "\n";

        }

        if (shavebx.isChecked()) {
            count = count + 1;


            servicesList = servicesList + shavebx.getText().toString() + " : " + sPrice.getText().toString() + "\n";
        }


        if (facialBx.isChecked()) {

            count = count + 1;

            servicesList = servicesList + facialBx.getText().toString() + " : " + facPrice.getText().toString() + "\n";
        }


        if (spawBx.isChecked()) {

            count = count + 1;


            servicesList = servicesList + spawBx.getText().toString() + " : " + spawPrice.getText().toString() + "\n";
        }


        if (beautyBx.isChecked()) {

            count = count + 1;

            servicesList = servicesList + beautyBx.getText().toString() + " : " + bPPrice.getText().toString() + "\n";
        }


    }


    private void back() {
        ImageView backBtn = findViewById(R.id.backBtn3);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}


