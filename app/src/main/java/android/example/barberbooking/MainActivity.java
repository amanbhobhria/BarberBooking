package android.example.barberbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.example.barberbooking.adapter.HmServicesAdapter;
import android.example.barberbooking.common.Common;
import android.example.barberbooking.fragments.HelpAssistantFragment;
import android.example.barberbooking.fragments.OffersFragment;
import android.example.barberbooking.fragments.ViewBookingsFragment;
import android.example.barberbooking.model.HomeVendorModel;
import android.example.barberbooking.model.UserModel;


import android.example.barberbooking.stylist.SearchResultStylists;


import android.example.barberbooking.user.NotificationActivity;
import android.example.barberbooking.vendor.VendorAgreement;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;


import android.view.View;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar_home;
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    LinearLayout searchBar, progressBar,bestOfferLyt;
    LinearLayout sirsa, kehrwala, chakkan, bhunna, ellenabad, kharia, rasalia, mammad;


    ActionBarDrawerToggle actionBarDrawerToggle;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String phone;

    RecyclerView recyclerView;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    DatabaseReference referenceUser;

    AppBarLayout homeBarLayt;
    NestedScrollView nestedScrollView;
    FrameLayout frameLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView notificationBtn;


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


        referenceUser = firebaseDatabase.getReference("user");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);




        getData();


        showStylist();

        setNavigationDrawer();

        setBottomNavigation();
        search();

        notifications();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getColor(R.color.colorPrimary));
        }


        bestOffers();


        cityListener();
    }


    private void bestOffers() {
        bestOfferLyt.setOnClickListener(v -> {
            collapsingToolbarLayout.setVisibility(View.GONE);
            nestedScrollView.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);
            replaceFragment(new OffersFragment());
        });
    }


    private void notifications() {
        notificationBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
            startActivity(intent);
        });
    }

    private void setBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.home) {
                item.setChecked(true);
                collapsingToolbarLayout.setVisibility(View.VISIBLE);
                // homeBarLayt.setVisibility(View.VISIBLE);
                nestedScrollView.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
                collapsingToolbarLayout.setVisibility(View.VISIBLE);
            } else if (item.getItemId() == R.id.bookings) {
                collapsingToolbarLayout.setVisibility(View.GONE);
                item.setChecked(true);
                nestedScrollView.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
                replaceFragment(new ViewBookingsFragment());

            } else if (item.getItemId() == R.id.offers) {
                collapsingToolbarLayout.setVisibility(View.GONE);
                nestedScrollView.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
                item.setChecked(true);

                replaceFragment(new OffersFragment());
            } else if (item.getItemId() == R.id.invite) {

                item.setChecked(true);
                //Toast.makeText(MainActivity.this, "Invite", Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.help) {
                collapsingToolbarLayout.setVisibility(View.GONE);
                item.setChecked(true);
                nestedScrollView.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
                replaceFragment(new HelpAssistantFragment());

            }

            return false;
        });
    }

    private void cityListener() {
        callCitySearch(sirsa, "Sirsa City");
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

        bottomNavigationView = findViewById(R.id.bottom_nav);

        homeBarLayt = findViewById(R.id.home_appBarLayout);
        nestedScrollView = findViewById(R.id.nested_scroll);
        frameLayout = findViewById(R.id.frame_layout);
        collapsingToolbarLayout = findViewById(R.id.toolbar);

        notificationBtn = findViewById(R.id.bellNotification);
        bestOfferLyt = findViewById(R.id.bestOfferLyt);



    }


    private void getData() {


        sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        phone = sharedPreferences.getString("userP", "No name defined");
        editor.apply();


        UserModel userModel = new UserModel();

        userModel.setPhone(phone);

        if (!phone.equalsIgnoreCase("Guest")) {

            try {
                referenceUser.child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child("userName").exists()) {
                            String name = Objects.requireNonNull(snapshot.child("userName").getValue(String.class));
                            userModel.setUserName(name);
                        }
                        if (snapshot.child("city").exists()) {

                            String city = Objects.requireNonNull(snapshot.child("city").getValue(String.class));
                            userModel.setCity(city);
                        }
                        if (snapshot.child("roadName").exists()) {

                            String roadName = Objects.requireNonNull(snapshot.child("roadName").getValue(String.class));
                            userModel.setRoadName(roadName);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            } catch (Exception e) {
                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }

        }


        Common.currentUser = userModel;
        Common.currentUser.setSlotTime(null);

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


        ImageView viewProfileArrow = view.findViewById(R.id.viewProfileBtn_nav);
        viewProfileArrow.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,AccountActivity.class);
            startActivity(intent);
        });


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
            }
            else if (item.getItemId() == R.id.nav_bookings) {

                collapsingToolbarLayout.setVisibility(View.GONE);
                nestedScrollView.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
                replaceFragment(new ViewBookingsFragment());
                drawerLayout.closeDrawer(GravityCompat.START);

            }
            else if (item.getItemId() == R.id.nav_help) {

                collapsingToolbarLayout.setVisibility(View.GONE);
                nestedScrollView.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
                replaceFragment(new HelpAssistantFragment());
                drawerLayout.closeDrawer(GravityCompat.START);

            }
            else if (item.getItemId() == R.id.nav_invite_earn) {

                Toast.makeText(MainActivity.this,"Coming Soon",Toast.LENGTH_SHORT).show();

            }
            else if (item.getItemId() == R.id.nav_logout) {
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

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }


}