package android.example.barberbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.example.barberbooking.common.Common;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    FloatingSearchView mSearchView;
    ImageView calenderDate;
    TextView selectDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        intialize();
        searchView();


    }

    private void intialize() {
        mSearchView = findViewById(R.id.floating_search_view);
        calenderDate = findViewById(R.id.openCalenderIcon);
        selectDate = findViewById(R.id.select_date);
        mSearchView.requestFocus();

        dateSelctor();
    }


    private void searchView() {
        //showKeyboard(mSearchView);

        mSearchView.setOnLeftMenuClickListener(new FloatingSearchView.OnLeftMenuClickListener() {
            @Override
            public void onMenuOpened() {
                Toast.makeText(SearchActivity.this, "Open Menu", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMenuClosed() {

            }
        });



        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                //get suggestions based on newQuery
                //   List< > =new ArrayList<>();
//                suggestions.add("Saloon");
//                suggestions.add("Sirsa");


                //pass them on to the search view
                List<? extends SearchSuggestion> suggestions = new ArrayList<>();

                mSearchView.swapSuggestions(suggestions);
            }
        });
    }

    public void showKeyboard(FloatingSearchView editText) {


        editText.post(new Runnable() {

            @Override
            public void run() {
                editText.requestFocus();

                InputMethodManager imm = (InputMethodManager) editText.getContext()
                        .getSystemService(SearchActivity.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }

    public void dateSelctor() {

        if (Common.date == null) {
            selectDate.setText("Today");
        } else {
            selectDate.setText(Common.date);
        }

        calenderDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, CalenderActivity.class);
                startActivity(intent);
            }
        });
    }


}