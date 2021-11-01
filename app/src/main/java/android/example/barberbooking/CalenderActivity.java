package android.example.barberbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.example.barberbooking.common.Common;
import android.example.barberbooking.stylist.StylistActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalenderActivity extends AppCompatActivity {
    CalendarView calendarView;
ImageView closeBTn;

    private String selectedDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_layout);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        TextView textview = (TextView) findViewById(R.id.selDate);
        calendarView = findViewById(R.id.calenderView);
        closeBTn = findViewById(R.id.closeCalenderBtn);
        Calendar cal = Calendar.getInstance();
        close();


        calendarView.setMinDate(calendarView.getDate());
        calendarView.setMaxDate(calendarView.getDate() + 1000 * 60 * 60 * 24 * 7);





        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {


                SimpleDateFormat month_date = new SimpleDateFormat("MMMM");


                cal.set(Calendar.MONTH, month);
                String month_name = month_date.format(cal.getTime());


                selectedDay = dayOfMonth + " " + month_name;

                textview.setText(selectedDay);

                Common.date = textview.getText().toString();



                Intent intent = new Intent(CalenderActivity.this, StylistActivity.class);
                startActivity(intent);
                finish();


            }
        });


    }
    private void close()
    {
        closeBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
