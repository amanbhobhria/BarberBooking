package android.example.barberbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.example.barberbooking.adapter.HmServicesAdapter;
import android.example.barberbooking.common.Common;
import android.example.barberbooking.model.HomeVendorModel;
import android.example.barberbooking.model.UserModel;


import android.example.barberbooking.stylist.SearchResultStylists;

import android.example.barberbooking.vendor.VendorAgreement;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import android.view.View;

import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar_home;
    DrawerLayout drawerLayout;
    LinearLayout searchBar, progressBar;
    //
    LinearLayout sirsa, kehrwala, chakkan, bhunna, ellenabad, kharia, rasalia, mammad;


    ActionBarDrawerToggle actionBarDrawerToggle;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String phone;

    RecyclerView recyclerView;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;


    @Override
    public void onBackPressed() {
// super.onBackPressed();
// Not calling **super**, disables back button in current screen.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        initialize();


        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);


        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("hmvendor");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        recyclerView.setLayoutManager(layoutManager);


        getData();


        showStylist();

        setNavigationDrawer();
        search();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getColor(R.color.colorPrimary));
        }


        cityListener();
    }

    private void cityListener() {
        callCitySearch(sirsa, "Sirsa");
        callCitySearch(kehrwala, "Kehrwala");
        callCitySearch(chakkan, "Chakkan");
        callCitySearch(bhunna, "Bhunna");
        callCitySearch(ellenabad, "Ellenabad");
        callCitySearch(kharia, "Kharian");
        callCitySearch(rasalia, "Rasalia");
        callCitySearch(mammad, "Mammad");


    }


    private void initialize() {
        drawerLayout = findViewById(R.id.my_drawer_layout);
        toolbar_home = findViewById(R.id.toolbar_home);
        recyclerView = findViewById(R.id.stylistRecyclerView);
        progressBar = findViewById(R.id.progressRecommended);

        sirsa = findViewById(R.id.sirsaBtn);
        kehrwala = findViewById(R.id.kehrwalaBtn);
        chakkan = findViewById(R.id.chakkanBtn);
        bhunna = findViewById(R.id.bhunnaBtn);
        ellenabad = findViewById(R.id.ellenabadBtn);
        kharia = findViewById(R.id.kharianBtn);
        rasalia = findViewById(R.id.rasaliaBtn);
        mammad = findViewById(R.id.mammadBtn);


    }


    private void getData() {


        sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        phone = sharedPreferences.getString("userP", "No name defined");
        editor.apply();

        UserModel userModel = new UserModel();

        userModel.setPhone(phone);
        Common.currentUser = userModel;

    }


    private void setNavigationDrawer() {
        /*Declaration*/

        NavigationView navigationView = findViewById(R.id.nav_menu);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar_home, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        View view = navigationView.getHeaderView(0);
        TextView uPhone = view.findViewById(R.id.nav_phone);
        uPhone.setText(phone);


        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_partner) {


                if (phone.equals("Guest")) {
                    Toast.makeText(getApplicationContext(), "Login with your Phone Number to Continue", Toast.LENGTH_SHORT).show();

                } else {
                    Intent intent = new Intent(MainActivity.this, VendorAgreement.class);
                    startActivity(intent);
                }
            } else if (item.getItemId() == R.id.nav_account) {


                Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.nav_logout) {
                editor.clear();
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), LauncherActivity.class);
                startActivity(intent);
                finish();
            }


            return false;
        });

    }

    private void search() {
        searchBar = findViewById(R.id.searchBar);
        searchBar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);

        });
    }


    private void showStylist() {

        List<HomeVendorModel> list = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for (DataSnapshot sp : snapshot.getChildren()) {


                    HomeVendorModel homeVendorModel = sp.getValue(HomeVendorModel.class);
                    assert homeVendorModel != null;
                    if (homeVendorModel.getStatus().trim().equalsIgnoreCase("Approved".trim())) {

                        homeVendorModel.setRegno(sp.getKey());

                        Log.i("TAG", "onDataChange: " + sp.getKey());

                        list.add(homeVendorModel);
                    }

                }
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);


                HmServicesAdapter vendorHmRequestAdapter = new HmServicesAdapter(list, MainActivity.this);
                recyclerView.setAdapter(vendorHmRequestAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }

    private void callCitySearch(LinearLayout button, String cityname) {
        button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchResultStylists.class);
            intent.putExtra("cityname", cityname);
            startActivity(intent);
        });

    }


}