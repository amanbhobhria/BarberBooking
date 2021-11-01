package android.example.barberbooking.vendor.homefacility;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.example.barberbooking.MainActivity;
import android.example.barberbooking.R;
import android.example.barberbooking.common.Common;
import android.example.barberbooking.model.HomeVendorModel;
import android.example.barberbooking.model.VendorModel;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.UUID;

public class SubmitHmVendorActivity extends AppCompatActivity {
    TextView owner, phone, email, city, address, bankName, bankAddress, beneficiary, accNo, accType, ifsc, idNo, idType, services, editBtn, referenceId, home;
    CheckBox agreeChkBx;
    Button submitBtn;
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
        setContentView(R.layout.activity_submit_hm_vendor);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("hmvendor");



        initialize();
        getData();
        submit();
        returnHome();

    }



    private void initialize() {
        //Personal
        owner = findViewById(R.id.oNamehmSbmt);
        phone = findViewById(R.id.phonehmSbmt);
        email = findViewById(R.id.emailhmSbmt);
        city = findViewById(R.id.cityhmSbmt);

        address = findViewById(R.id.addresshmSbmt);

        //Bank
        bankName = findViewById(R.id.bankNameSbmt);
        bankAddress = findViewById(R.id.bnkAdressSbmt);
        beneficiary = findViewById(R.id.benifNameSbmt);
        accNo = findViewById(R.id.accountNumhmSbmt);
        accType = findViewById(R.id.acctypeSbmt);
        ifsc = findViewById(R.id.ifscSbmt);



        //Govt Document detail
        idNo = findViewById(R.id.idNoSbmt);
        idType = findViewById(R.id.idTypeSbmt);


        //others
        services = findViewById(R.id.servicesHm);
        agreeChkBx = findViewById(R.id.agreeSubmithmCheckBox);
        submitBtn = findViewById(R.id.submitFormhmBtn);

        editBtn = findViewById(R.id.edithmBtn);
        home = findViewById(R.id.returnhmHome);






    }

    private void getData()  {

        owner.setText(Common.currentDetails2.getOwnerName());
        phone.setText(Common.currentDetails2.getPhoneNoAdd());
        email.setText(Common.currentDetails2.getEmail());
        city.setText(Common.currentDetails2.getCity());
        address.setText(Common.currentDetails2.getAddress());

        bankName.setText(Common.currentDetails2.getBankname());
        bankAddress.setText(Common.currentDetails2.getBankadress());
        beneficiary.setText(Common.currentDetails2.getBenificaryname());
        accNo.setText(Common.currentDetails2.getAccountNo());
        accType.setText(Common.currentDetails2.getAccounttype());
        ifsc.setText(Common.currentDetails2.getIfsccode());





        idNo.setText(Common.currentDetails2.getGovtidno());
        idType.setText(Common.currentDetails2.getDocType());


        services.setText(Common.currentDetails2.getServiceList());

    }

    private void submit() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(agreeChkBx.isChecked()) {


                    String id = Common.currentDetails2.getPhoneNo();
                    String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
                    HomeVendorModel homeVendorModel = Common.currentDetails2;
                    homeVendorModel.setTime(currentDateTimeString);
                    homeVendorModel.setStatus("Pending");


                    reference.child(id).setValue(homeVendorModel);

                    Toast.makeText(getApplicationContext(), "Request Sent successfully  ", Toast.LENGTH_SHORT).show();


                    Common.isUploaded = true;
                    agreeChkBx.setVisibility(View.GONE);
                    submitBtn.setVisibility(View.GONE);
                    editBtn.setVisibility(View.GONE);
                    home.setVisibility(View.VISIBLE);
                }
                else
                {
                    agreeChkBx.requestFocus();
                    Toast.makeText(getApplicationContext(), "Please Tick the checkbox before submitting " , Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    private void returnHome(){
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubmitHmVendorActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }











}