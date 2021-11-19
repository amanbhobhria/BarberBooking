package android.example.barberbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.example.barberbooking.common.Common;
import android.example.barberbooking.model.UserModel;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AccountActivity extends AppCompatActivity {
    ImageView backBtn;
    TextView phoneTxt, nameTxt, cityTxt, addressTxt;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    RelativeLayout phoneLyt, guestLyt, logoutBTn;
    LinearLayout saveBTn;


    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        initialize();
        back();
        getData();

        sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();

        name = sharedPreferences.getString("userP", "No name defined");


        if (name.equals("Guest")) {
            guestLogin();
        } else {
            phoneLogin();
        }


    }


    private void initialize() {

        backBtn = findViewById(R.id.backProfileBtn);

        //when logged in with phone
        phoneLyt = findViewById(R.id.phoneloginAccLyt);
        nameTxt = findViewById(R.id.nameAccTxt);
        phoneTxt = findViewById(R.id.phoneAccTxt);
        cityTxt = findViewById(R.id.cityAccTxt);
        addressTxt = findViewById(R.id.addressAccTxt);
        logoutBTn = findViewById(R.id.logoutAccLyt);
        saveBTn = findViewById(R.id.saveBtnAccLyt);

        //guest logged in
        guestLyt = findViewById(R.id.guestLyt);


    }


    private void guestLogin() {
        guestLyt.setVisibility(View.VISIBLE);
        phoneLyt.setVisibility(View.GONE);

    }

    private void back() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void phoneLogin() {
        phoneTxt.setText(name);
        phoneLyt.setVisibility(View.VISIBLE);
        guestLyt.setVisibility(View.GONE);

        logoutBTn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                editor.clear();
                editor.apply();
                Intent intent = new Intent(AccountActivity.this, LauncherActivity.class);
                startActivity(intent);
                finish();
                return false;
            }
        });


    }


    private void getData() {

        UserModel userModel = Common.currentUser;


        if (userModel.getRoadName() == (null)) {
            System.out.println("No Data in address");
            Toast.makeText(getApplicationContext(), "if conditition", Toast.LENGTH_SHORT).show();
        } else {
            nameTxt.setText(userModel.getUserName());
            cityTxt.setText(userModel.getCity());
            addressTxt.setText(userModel.getRoadName());
            nameTxt.setText(userModel.getUserName());
            Toast.makeText(getApplicationContext(), "else conditition", Toast.LENGTH_SHORT).show();
        }


    }
}