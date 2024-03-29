package android.example.barberbooking.stylist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.example.barberbooking.R;
import android.example.barberbooking.common.Common;
import android.example.barberbooking.model.BookingModel;
import android.example.barberbooking.model.UserModel;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class AddressActivity extends AppCompatActivity {
    ImageView exitBtn;
    MaterialSpinner citySpin;
    EditText nameEdt, addressEdt;
    LinearLayout submitBtn;
    private String name, address, city;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("user");


        initialize();
        getPreviousData();
        cityList();

        submit();
        exit();


    }

    private void initialize() {
        citySpin = findViewById(R.id.cityuserListSpin);
        nameEdt = findViewById(R.id.userNamehmTxt);
        addressEdt = findViewById(R.id.userAddressTxt);
        submitBtn = findViewById(R.id.submitAddressBtn);
        exitBtn =findViewById(R.id.exitAddress);



    }
    private void getPreviousData() {

            nameEdt.setText(Common.currentBooking.getUserName());
            addressEdt.setText(Common.currentBooking.getUserAddress());


    }

    private void submit() {
        submitBtn.setOnClickListener(v -> {
            getData();
            if (name.isEmpty() || address.isEmpty() || city.isEmpty()) {
                Toast.makeText(getApplicationContext(), "name or address is empty", Toast.LENGTH_SHORT).show();
            } else {
                UserModel userModel = Common.currentUser;
                userModel.setUserName(name);
                userModel.setRoadName(address);
                userModel.setCity(city);



                reference.child(userModel.getPhone()).setValue(userModel);





                BookingModel bookingModel = Common.currentBooking;
                bookingModel.setUserName(name);
                bookingModel.setUserAddress(address);
                bookingModel.setUserCity(city);

                Intent intent = new Intent(getApplicationContext(),BookingActivity.class);
                startActivity(intent);
             //   finish();
            }


        });

    }


    private void getData() {

        name = nameEdt.getText().toString();
        address = addressEdt.getText().toString();
        city = citySpin.getText().toString();

    }


    private void exit()
    {
        exitBtn.setOnClickListener(v -> finish());
    }

    private void cityList() {

        citySpin.setItems("Sirsa City ", "Rania", "Ellenabad", "Baragudha", "Dabwali", "Nathusary Chopta", "Odhan", "Chakan", "Keharwala", "Bhoona", "Mamber Khera");
        citySpin.setOnItemSelectedListener((MaterialSpinner.OnItemSelectedListener<String>) (view, position, id, item) -> Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show());


        citySpin.setOnClickListener(v -> {
            citySpin.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        });

    }


}