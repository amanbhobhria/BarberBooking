package android.example.barberbooking.stylist;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.example.barberbooking.R;
import android.example.barberbooking.SearchActivity;
import android.example.barberbooking.adapter.HmServicesAdapter;
import android.example.barberbooking.model.HomeVendorModel;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SearchResultStylists extends AppCompatActivity {
    RecyclerView recyclerView;
    RelativeLayout noCityLyt;
    LinearLayout goToSearchBtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    private String cityName;
    TextView searchedCity;
    ImageView backBtn;

    int vendorInThisCity = 0;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_stylists);

        //setStatusBarTransparent();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        initialize();


        getWindow().setStatusBarColor(getResources().getColor(R.color.colorRed));

        if (bundle != null) {
            cityName = bundle.getString("cityname");
            searchedCity.setText(cityName);

        }


        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("hmvendor");


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        showStylist();

        searchFun();


    }

    private void searchFun() {
        goToSearchBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SearchResultStylists.this, SearchActivity.class);
            startActivity(intent);

        });


    }

    private void initialize() {
        searchedCity = findViewById(R.id.citySearchedTxt);
        backBtn = findViewById(R.id.backResultStylist);
        noCityLyt = findViewById(R.id.noCityLyt);

        goToSearchBtn = findViewById(R.id.goToSearchBtn);


        recyclerView = findViewById(R.id.stylistonCityRecylerView);


        backBtn.setOnClickListener(v -> finish());

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


                    if ((cityName.trim().equals("Nearby")) && homeVendorModel.getStatus().trim().equalsIgnoreCase("Approved".trim())) {
                        homeVendorModel.setRegno(sp.getKey());
                        vendorInThisCity = vendorInThisCity+1;


                        Log.i("TAG", "onDataChange: " + sp.getKey());

                        list.add(homeVendorModel);


                        noCityLyt.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                    }


                    else if (homeVendorModel.getCity().trim().equalsIgnoreCase(cityName.trim()) && homeVendorModel.getStatus().trim().equalsIgnoreCase("Approved".trim())) {
                        homeVendorModel.setRegno(sp.getKey());
                        vendorInThisCity = vendorInThisCity+1;


                        Log.i("TAG", "onDataChange: " + sp.getKey());

                        list.add(homeVendorModel);


                        noCityLyt.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                    }


                }

                if(vendorInThisCity==0)
                {
                    noCityLyt.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }


                HmServicesAdapter vendorHmRequestAdapter = new HmServicesAdapter(list, SearchResultStylists.this);
                recyclerView.setAdapter(vendorHmRequestAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }


}