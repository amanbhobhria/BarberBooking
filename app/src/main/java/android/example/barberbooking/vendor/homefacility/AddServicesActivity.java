package android.example.barberbooking.vendor.homefacility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.example.barberbooking.R;
import android.example.barberbooking.common.Common;
import android.example.barberbooking.model.HomeVendorModel;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

public class AddServicesActivity extends AppCompatActivity {
    MaterialSpinner makeupPriceSpin, facialPr, bodyPr, hcutPr, hstylePr, hcolorPr, hmassagePr, fthreadPr, hcairTreatmentPr, pedicurePr, manicurePr, trimPr, hpermPr;
    CheckBox makeup, facial, waxing, haircut, hairstyling, haircolor, headmassage, facethread, haircare, pedicure, manicure, trimming, hairperming;
    Button submitBtn;
    String servicesList = "";
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_services);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        initialize();
        submit();


    }

    private void submit() {

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addService();
       if(count==0)
       {
           Toast.makeText(AddServicesActivity.this,"Select atleast 1 Services ",Toast.LENGTH_SHORT).show();
       }
       else
       {
           HomeVendorModel homeVendorModel = Common.currentHmVendor;
           homeVendorModel.setServiceList(servicesList);






           Intent intent = new Intent(AddServicesActivity.this, SubmitHmVendorActivity.class);
           startActivity(intent);
       }

            }
        });
    }

    private void initialize() {
        priceIntialize();
        submitBtn = findViewById(R.id.submitDataBtn);

        makeup = findViewById(R.id.makeUpBox);
        facial = findViewById(R.id.facialBox);
        waxing = findViewById(R.id.waxingBox);
        haircut = findViewById(R.id.hcutBox);
        hairstyling = findViewById(R.id.hstyleBox);
        haircolor = findViewById(R.id.hcolorBox);
        headmassage = findViewById(R.id.hmassagBox);
        facethread = findViewById(R.id.fthreadingBox);
        haircare = findViewById(R.id.hcareTreatBox);
        pedicure = findViewById(R.id.pedicureBox);
        manicure = findViewById(R.id.manicureBox);
        trimming = findViewById(R.id.trimmingBox);
        hairperming = findViewById(R.id.permingBox);
    }

    private void priceIntialize() {
        makeupPriceSpin = findViewById(R.id.makeUpPriceSpin);
        facialPr = findViewById(R.id.facialPriceSpin);
        bodyPr = findViewById(R.id.waxingPriceSpin);
        hcutPr = findViewById(R.id.cuttingpriceSpin);
        hstylePr = findViewById(R.id.hstylePriceSpin);
        hcolorPr = findViewById(R.id.hcolorPriceSpin);
        hmassagePr = findViewById(R.id.hmassagPriceSpin);
        fthreadPr = findViewById(R.id.fthreadPriceSpin);
        hcairTreatmentPr = findViewById(R.id.htreatPriceSpin);

        pedicurePr = findViewById(R.id.pedicarePriceSpin);
        manicurePr = findViewById(R.id.manicurePriceSpin);
        trimPr = findViewById(R.id.trimmingPriceSpin);
        hpermPr = findViewById(R.id.permingPriceSpin);

        makeupPriceSpin.setItems(200, 300, 400, 500, 1000, 1200, 1500, 2000, 2500, 2800, 3000, 3500, 4000, 5000);
        facialPr.setItems(100, 200, 300, 400, 500, 1000, 1200, 1500, 1800, 2000, 2500, 3000, 3500, 4000, 5000);
        bodyPr.setItems(50, 100, 200, 500, 600, 1000, 1200, 1500, 1800, 2000, 2500, 2800, 3000, 3500, 4000, 5000);
        hcutPr.setItems(50, 100, 200, 300, 400, 500, 600, 1000, 1200, 1500, 1800, 2000);
        pedicurePr.setItems(50, 100, 200, 300, 400, 500, 1000, 1200, 1500, 1800, 2000, 2500, 2800, 3000, 3500, 4000);
        manicurePr.setItems(30, 50, 100, 200, 300, 400, 500, 600, 1000, 1200, 1500, 1800, 2000, 2500, 2800, 3000, 3500, 4000, 5000);
        trimPr.setItems(30, 50, 100, 200, 300, 400, 500, 600, 1000, 1200, 1500, 1800, 2000, 2500, 2800, 3000, 3500, 4000, 5000);
        hpermPr.setItems(30, 50, 100, 200, 300, 400, 500, 600, 1000, 1200, 1500, 1800, 2000, 2500, 2800, 3000, 3500, 4000, 5000);
        hstylePr.setItems(30, 50, 100, 200, 300, 400, 500, 600, 1000, 1200, 1500, 1800);
        hcolorPr.setItems(30, 50, 100, 200, 300, 400, 500, 600, 1000, 1200, 1500, 1800, 2000);
        hmassagePr.setItems(50, 100, 200, 300, 400, 500, 600, 1000, 1200, 1500, 2000, 2500, 3000);
        hcairTreatmentPr.setItems(30, 50, 100, 200, 300, 400, 500, 600, 1000, 1200, 1500, 1800, 2000, 2500, 2800, 3000, 3500, 4000, 5000);
        fthreadPr.setItems(30, 50, 100, 200, 300, 400, 500, 600, 1000);


    }

    private void addService() {

        if (makeup.isChecked()) {


            count = count + 1;
            servicesList = makeup.getText().toString() + " : " + makeupPriceSpin.getText().toString() + "\n";

        }


        if (facial.isChecked()) {

            count = count + 1;

            servicesList = servicesList + facial.getText().toString() + " : " + facialPr.getText().toString() + "\n";
        }


        if (waxing.isChecked()) {

            count = count + 1;


            servicesList = servicesList + waxing.getText().toString() + " : " + bodyPr.getText().toString() + "\n";
        }


        if (haircut.isChecked()) {

            count = count + 1;

            servicesList = servicesList + haircut.getText().toString() + " : " + hcutPr.getText().toString() + "\n";

        }
        if (hairstyling.isChecked()) {

            count = count + 1;

            servicesList = servicesList + hairstyling.getText().toString() + " : " + hstylePr.getText().toString() + "\n";

        }
        if (haircolor.isChecked()) {

            count = count + 1;

            servicesList = servicesList + haircolor.getText().toString() + " : " + hcolorPr.getText().toString() + "\n";

        }
        if (headmassage.isChecked()) {

            count = count + 1;

            servicesList = servicesList + headmassage.getText().toString() + " : " + hmassagePr.getText().toString() + "\n";

        }
        if (facethread.isChecked()) {

            count = count + 1;

            servicesList = servicesList + facethread.getText().toString() + " : " + fthreadPr.getText().toString() + "\n";

        }
        if (haircare.isChecked()) {

            count = count + 1;

            servicesList = servicesList + haircare.getText().toString() + " : " + hcairTreatmentPr.getText().toString() + "\n";

        }

        if (pedicure.isChecked()) {

            count = count + 1;

            servicesList = servicesList + pedicure.getText().toString() + " : " + pedicurePr.getText().toString() + "\n";

        }
        if (manicure.isChecked()) {

            count = count + 1;

            servicesList = servicesList + manicure.getText().toString() + " : " + manicurePr.getText().toString() + "\n";

        }
        if (trimming.isChecked()) {

            count = count + 1;

            servicesList = servicesList + trimming.getText().toString() + " : " + trimPr.getText().toString() + "\n";

        }
        if (hairperming.isChecked()) {

            count = count + 1;

            servicesList = servicesList + hairperming.getText().toString() + " : " + hpermPr.getText().toString() + "\n";

        }





    }


}