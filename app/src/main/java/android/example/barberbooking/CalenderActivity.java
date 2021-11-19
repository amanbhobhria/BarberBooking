package android.example.barberbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.example.barberbooking.common.Common;
import android.example.barberbooking.stylist.StylistActivity;
import android.os.Bundle;

import android.widget.CalendarView;
import android.widget.ImageView;



public class CalenderActivity extends AppCompatActivity {
    CalendarView calendarView;
    ImageView closeBTn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_layout);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        initialize();

        calendarView.setMinDate(calendarView.getDate());
        calendarView.setMaxDate(calendarView.getDate() + 1000 * 60 * 60 * 24 * 7);



        close();
        setDates();



    }
    private void initialize() {
        calendarView = findViewById(R.id.calenderView);
        closeBTn = findViewById(R.id.closeCalenderBtn);
    }


    private void setDates() {
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {



            Common.date = dayOfMonth + "-" + (month + 1);


            Intent intent = new Intent(CalenderActivity.this, StylistActivity.class);
            startActivity(intent);
            finish();


        });


    }



    private void close() {
        closeBTn.setOnClickListener(v -> finish());
    }

}
