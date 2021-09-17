package android.example.barberbooking.vendor.homefacility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TintInfo;

import android.content.Intent;
import android.example.barberbooking.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BankingDetailActivityHm extends AppCompatActivity {
Button saveBankBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banking_detail_hm);
        intialize();
        saveBankDetail();

    }

    private void saveBankDetail() {
        saveBankBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BankingDetailActivityHm.this,GovtIdActivity.class);
                startActivity(intent);
            }
        });
    }

    private void intialize() {
        saveBankBtn = findViewById(R.id.saveBank);

    }
}