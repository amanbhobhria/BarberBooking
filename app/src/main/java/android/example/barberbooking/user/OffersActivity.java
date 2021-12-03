package android.example.barberbooking.user;

import androidx.appcompat.app.AppCompatActivity;

import android.example.barberbooking.R;
import android.os.Bundle;
import android.widget.ImageView;

public class OffersActivity extends AppCompatActivity {
ImageView backBtn;



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        back();
    }

    private void back() {
        backBtn =findViewById(R.id.backUserOffersBtn);
        backBtn.setOnClickListener(v -> {
            OffersActivity.super.onBackPressed();
        });
    }
}