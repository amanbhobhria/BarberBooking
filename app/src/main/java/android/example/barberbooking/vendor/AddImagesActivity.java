package android.example.barberbooking.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.example.barberbooking.MainActivity;
import android.example.barberbooking.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddImagesActivity extends AppCompatActivity {
Button saveimagesBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_images);
        saveimagesBtn = findViewById(R.id.saveImageBtn);
        saveimagesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddImagesActivity.this,SubmitFormActivity.class);
                startActivity(intent);
            }
        });
    }
}