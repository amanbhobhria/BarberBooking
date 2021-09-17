package android.example.barberbooking.vendor.homefacility;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.example.barberbooking.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HmVendorDetail extends AppCompatActivity {
EditText name,phone,email,adress;
Button nextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hm_vendor_detail);
        intialize();
        next();

    }

    private void next() {
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HmVendorDetail.this,BankingDetailActivityHm.class);
                startActivity(intent);
            }
        });
    }

    private void intialize() {
        name = findViewById(R.id.hmownerNameTxt);
        phone = findViewById(R.id.hmphoneNoTxt);
        email = findViewById(R.id.hmemailIdTxt);
        adress = findViewById(R.id.hmroad);
        nextBtn = findViewById(R.id.savePersonalDetailBtn);

    }
}