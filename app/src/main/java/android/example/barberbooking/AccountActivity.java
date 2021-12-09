package android.example.barberbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.example.barberbooking.common.Common;
import android.example.barberbooking.model.UserModel;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

public class AccountActivity extends AppCompatActivity {
    MaterialSpinner citySpin;
    ImageView backBtn, submitOtpBtn, generateOtpBtn;
    EditText nameTxt, cityTxt, addressTxt, phoneNoEdt, otpEdt;
    TextView phoneTxt, editBtn;
    ProgressBar progressBar,lgProgressBar;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private FirebaseAuth mAuth;

    RelativeLayout phoneLyt, guestLyt, logoutBTn, otpLyt, enterPhoneLyt;
    LinearLayout saveBTn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;


    private String name, phoneNo, otp, verificationId;
    private static final String KEY_VERIFICATION_ID = "key_verification_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);



        initialize();
        back();
        getData();

        references();

        checkCondition();

        editData();
        save();
        generateOtp();

        if (verificationId == null && savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }



        submitOtp();

        cityList();



    }


    private void cityList() {
        citySpin.setItems("Sirsa City ", "Rania", "Ellenabad","Kharian", "Dabwali", "Chakkan", "Keharwala", "Bhoona", "Mamber Khera");

        citySpin.setOnItemSelectedListener((MaterialSpinner.OnItemSelectedListener<String>) (view, position, id, item) -> Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show());


        citySpin.setOnClickListener(v -> {
            citySpin.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        });

    }

    private void checkCondition() {

        nameTxt.setEnabled(false);
        cityTxt.setEnabled(false);
        addressTxt.setEnabled(false);


        if (name.equals("Guest")) {
            guestLogin();
        } else {
            phoneLogin();
        }

    }

    private void references() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("user");


        sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
        name = sharedPreferences.getString("userP", "No name defined");

        mAuth = FirebaseAuth.getInstance();
    }

    private void save() {
        saveBTn.setOnClickListener(v -> {
            updateData();

            logoutBTn.setVisibility(View.VISIBLE);
            saveBTn.setVisibility(View.GONE);
            editBtn.setVisibility(View.VISIBLE);
            citySpin.setVisibility(View.GONE);
            cityTxt.setVisibility(View.VISIBLE);

            nameTxt.setEnabled(false);

            addressTxt.setEnabled(false);



        });


    }

    private void editData() {


        editBtn.setOnClickListener(v -> {
            editBtn.setVisibility(View.GONE);
            logoutBTn.setVisibility(View.GONE);
            cityTxt.setVisibility(View.GONE);
            nameTxt.setEnabled(true);
            addressTxt.setEnabled(true);
            saveBTn.setVisibility(View.VISIBLE);
            citySpin.setVisibility(View.VISIBLE);



        });
    }

    private void updateData() {
        UserModel userModel = Common.currentUser;
        userModel.setPhone(name);
        userModel.setUserName(nameTxt.getText().toString());
        userModel.setRoadName(addressTxt.getText().toString());
        userModel.setCity(citySpin.getText().toString());


        reference.child(name).setValue(userModel);


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
        editBtn = findViewById(R.id.editAccBTn);

        //guest logged in
        guestLyt = findViewById(R.id.guestLyt);
        phoneNoEdt = findViewById(R.id.phoneNoAccEdit);
        otpEdt = findViewById(R.id.otpAccEdit);
        otpLyt = findViewById(R.id.otpAccLayout);
        generateOtpBtn = findViewById(R.id.verifyPhoneBtn);
        submitOtpBtn = findViewById(R.id.verifyOtpAccBtn);
        enterPhoneLyt = findViewById(R.id.phoneAccLayout);
        progressBar = findViewById(R.id.otpVerifyAccProgressBar);
        lgProgressBar = findViewById(R.id.logoutProgressBar);
        citySpin = findViewById(R.id.cityListAccountSpin);


    }


    private void guestLogin() {
        guestLyt.setVisibility(View.VISIBLE);
        phoneLyt.setVisibility(View.GONE);

    }

    private void back() {
        backBtn.setOnClickListener(v -> finish());

    }



    private void phoneLogin() {
        editBtn.setVisibility(View.VISIBLE);
        phoneTxt.setText(name);


        cityTxt.setText(name);
        phoneTxt.setText(name);
        if (Common.currentUser.getCity() != null) {
            cityTxt.setText(Common.currentUser.getCity());

        }

        if (Common.currentUser.getRoadName() != null) {
            addressTxt.setText(Common.currentUser.getRoadName());

        }






        phoneLyt.setVisibility(View.VISIBLE);
        guestLyt.setVisibility(View.GONE);

        logoutBTn.setOnClickListener(v -> {
            lgProgressBar.setVisibility(View.VISIBLE);
            editor.clear();
            editor.apply();

            editor.putBoolean("isLogin", true);
            editor.putString("userP", "Guest");
            editor.commit();

          Intent intent = new Intent(AccountActivity.this,MainActivity.class);
          startActivity(intent);
          finish();



        });


    }


    private void getData() {

        UserModel userModel = Common.currentUser;


        if (userModel.getRoadName() == (null)) {
            System.out.println("No Data in address");

        }
        else {
            nameTxt.setText(userModel.getUserName());
            cityTxt.setText(userModel.getCity());
            addressTxt.setText(userModel.getRoadName());



        }


    }


    private void generateOtp() {
        generateOtpBtn.setOnClickListener(v -> {
            phoneNo = phoneNoEdt.getText().toString();
            if (TextUtils.isEmpty(phoneNo)) {
                // when mobile number text field is empty
                // displaying a toast message.
                Toast.makeText(AccountActivity.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
            } else {
                // if the text field is not empty we are calling our
                // send OTP method for getting OTP from Firebase.
                String phone = "+91" + phoneNo;
                Toast.makeText(AccountActivity.this, "Otp sent to " + phone + " successfully.", Toast.LENGTH_SHORT).show();
                otpEdt.setVisibility(View.VISIBLE);
                enterPhoneLyt.setVisibility(View.GONE);
                otpLyt.setVisibility(View.VISIBLE);
                sendVerificationCode(phone);
                hideKeyboard(generateOtpBtn);
            }
        });
    }


    private void submitOtp() {
        // initializing on click listener
        // for verify otp button
        submitOtpBtn.setOnClickListener(v -> {
            otp = otpEdt.getText().toString();
            hideKeyboard(submitOtpBtn);

            // validating if the OTP text field is empty or not.
            if (TextUtils.isEmpty(otp)) {
                // if the OTP text field is empty display
                // a message to user to enter OTP
                Toast.makeText(AccountActivity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
            } else {
                progressBar.setVisibility(View.VISIBLE);
                otpEdt.setEnabled(false);

                // if OTP field is not empty calling
                // method to verify the OTP.
                try {
                    verifyCode(otp);
                } catch (Exception e) {
                    otpEdt.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                    Toast toast = Toast.makeText(AccountActivity.this, "Verification Code is wrong" + e.toString(), Toast.LENGTH_SHORT);
                    System.out.println("Wrong_Verif :" + e.toString());
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }
            }
        });

    }


    //Login with Phone Using Firebase Authentication

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        editor.putBoolean("isLogin", true);
                        editor.putString("userP", phoneNo);
                        editor.putString("passW", otp);
                        editor.commit();
                        Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                        startActivity(intent);


                        FirebaseUser user = task.getResult().getUser();
                        finish();
                        // Update UI
                    } else {
                        otpEdt.setEnabled(true);
                        progressBar.setVisibility(View.GONE);
                        // Sign in failed, display a message and update the UI
                        // Toast.makeText(LauncherActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        Toast.makeText(AccountActivity.this, "Invalid OTP", Toast.LENGTH_LONG).show();
                        // Log.w(TAG, "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(AccountActivity.this, "Invalid Verification Code", Toast.LENGTH_SHORT).show();
                            // The verification code entered was invalid
                        }
                    }
                });
    }


    //1
    private void sendVerificationCode(String number) {
        // this method is used for getting
        // OTP on user phone number.
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)            // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(AccountActivity.this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)           // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // callback method is called on Phone auth provider.


    //2
    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(@NotNull String s, @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
            verificationId = s;


        }


        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();

            // checking if the code
            // is null or not.
            if (code != null) {
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
                otpEdt.setText(code);

                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(AccountActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };


    // below method is use to verify code from Firebase.
    private void verifyCode(String code) {
        // below line is used for getting getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }


    public void hideKeyboard(View view) {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception ignored) {
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_VERIFICATION_ID,verificationId);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        verificationId = savedInstanceState.getString(KEY_VERIFICATION_ID);
    }


}