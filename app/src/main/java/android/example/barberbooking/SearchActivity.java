package android.example.barberbooking;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;

import android.app.ActionBar;
import android.content.Intent;
import android.example.barberbooking.stylist.SearchResultStylists;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    ListView listView;
    String[] city_name_list = {"Sirsa", "Kehrwala", "Chakkan", "Bhunna", "Ellenabad", "Kharian", "Rasalia", "Mammad"};

    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);



        initialize();
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, city_name_list);

        listView.setAdapter(arrayAdapter);

        searchItem();



    }



    private void initialize() {
        listView = findViewById(R.id.listViewSample);
    }

    private void searchItem() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = city_name_list[position];

                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(SearchActivity.this, SearchResultStylists.class);
                intent.putExtra("cityname", text);
                startActivity(intent);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);


        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.onActionViewExpanded();

        searchView.setQueryHint("Type city name here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }
}