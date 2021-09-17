package android.example.barberbooking.vendor.homefacility;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.example.barberbooking.R;
import android.os.Bundle;
import android.widget.Button;

public class GovtIdActivity extends AppCompatActivity {
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_govt_id);
        intialize();
        submit();

    }

    private void submit() {
        Intent intent =  new Intent(GovtIdActivity.this,AddServicesActivity.class);
        startActivity(intent);
    }

    private void intialize() {
        submitBtn = findViewById(R.id.submitGovtDetailBtn);

    }
}