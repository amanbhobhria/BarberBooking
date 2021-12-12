package android.example.barberbooking.vendor.homefacility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.example.barberbooking.R;
import android.example.barberbooking.common.Common;
import android.example.barberbooking.model.HomeVendorModel;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

public class AddServicesActivity extends AppCompatActivity {
    // MaterialSpinner makeupPriceSpin, facialPr, bodyPr, hcutPr, hstylePr, hcolorPr, hmassagePr, fthreadPr, hcairTreatmentPr, pedicurePr, manicurePr, trimPr, hpermPr;
    //  CheckBox makeup, facial, waxing, haircut, hairstyling, haircolor, headmassage, facethread, haircare, pedicure, manicure, trimming, hairperming;
    Button submitBtn;
    EditText basePriceEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_services);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        initialize();
        submit();


    }

    private void submit() {

        submitBtn.setOnClickListener(v -> {


            if (basePriceEdt.getText().length() == 0) {

                Toast.makeText(AddServicesActivity.this, "Enter your base booking Price", Toast.LENGTH_SHORT).show();


            } else {


                Common.currentHmVendor.setBasePrice(basePriceEdt.getText().toString());
                Intent intent = new Intent(AddServicesActivity.this, SubmitHmVendorActivity.class);
                startActivity(intent);

            }


        });
    }

    private void initialize() {

        submitBtn = findViewById(R.id.submitDataBtn);
        basePriceEdt = findViewById(R.id.basePriceEdtTxt);

    }
}