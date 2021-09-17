package android.example.barberbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.example.barberbooking.vendor.VendorAgreement;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    RelativeLayout searchBar;
    ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);

        setNavigationDrawyer();
        search();

    }

    private void setNavigationDrawyer() {
        /*Declaration*/
        drawerLayout = findViewById(R.id.my_drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_menu);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        View view = navigationView.getHeaderView(0);


        ImageView uImage = view.findViewById(R.id.nav_imag);
        TextView uName = view.findViewById(R.id.nav_name);

          navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
              @Override
              public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                  if(item.getItemId()== R.id.nav_partner){
                      Intent intent = new Intent(MainActivity.this, VendorAgreement.class);
                      startActivity(intent);

                  }
                  else if(item.getItemId()== R.id.nav_account)
                  {
                      Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                      startActivity(intent);
                  }
                  return false;
              }
          });

    }

    private void search() {
        searchBar = findViewById(R.id.searchBar);
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);

                startActivity(intent);

            }
        });
    }




}