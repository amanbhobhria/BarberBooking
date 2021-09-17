package android.example.barberbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.example.barberbooking.common.Common;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

public class CalenderActivity extends AppCompatActivity {
    CalendarView calendarView;
    CalendarView calender;
    Button applyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_layout);
        TextView textview = (TextView) findViewById(R.id.selDate);
        calendarView = findViewById(R.id.calenderView);
        applyBtn = findViewById(R.id.applyBtn);

        calendarView.setMinDate(calendarView.getDate());
        textview.setText("Today");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                textview.setText("Book for  : " + dayOfMonth + " / " + (month + 1) + " / " + year);

            }
        });

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Long date = calendarView.getDate();

                Common.date = textview.getText().toString();
                Intent intent = new Intent(CalenderActivity.this, SearchActivity.class);
                startActivity(intent);

            }
        });


    }

}
