package android.example.barberbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.example.barberbooking.adapter.SliderAdapter;
import android.example.barberbooking.model.SliderData;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

public class LauncherActivity extends AppCompatActivity {
    CountryCodePicker ccp;
    TextView skip;
    EditText phoneNoEdt, otpEdt;
    Button submitOtpBTn;
    ImageView generateOtpBTn;
    private FirebaseAuth mAuth;
    RelativeLayout phoneLyt;
    private String otp, phoneNo, verificationId;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

   // private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        mAuth = FirebaseAuth.getInstance();

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        initialize();
        setImages();
        skip();
        login();



        sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (sharedPreferences.getBoolean("isLogin", false)) {

            Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }







    }

    private void initialize() {
        ccp = findViewById(R.id.ccp);
        skip = findViewById(R.id.signUpLaterTxt);
        phoneNoEdt = findViewById(R.id.phoneNoEdit);
        otpEdt = findViewById(R.id.idEdtOtp);
        generateOtpBTn = findViewById(R.id.verifyPhoneBtn);
        submitOtpBTn = findViewById(R.id.verifyOtpBtn);
        phoneLyt = findViewById(R.id.phoneLayout);


    }

    private void setImages() {

        int url1 = R.drawable.barbershopiamge1;
        int url2 = R.drawable.barbershopimage2;
        int url3 = R.drawable.barbershopimage3;
        int url4 = R.drawable.barbershopimage5;
        int url5 = R.drawable.barbershopimage6;
        int url6 = R.drawable.barbershopimage7;
        int url7 = R.drawable.barbershopimage8;
        // we are creating array list for storing our image urls.
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();

        // initializing the slider view.
        SliderView sliderView = findViewById(R.id.slider);

        // adding the urls inside array list
        sliderDataArrayList.add(new SliderData(url1));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url7));
        sliderDataArrayList.add(new SliderData(url3));
        sliderDataArrayList.add(new SliderData(url4));
        sliderDataArrayList.add(new SliderData(url5));
        sliderDataArrayList.add(new SliderData(url6));


        // passing this array list inside our adapter class.
        SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);

        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        // below method is used to
        // setadapter to sliderview.
        sliderView.setSliderAdapter(adapter);

        // below method is use to set
        // scroll time in seconds.
        sliderView.setScrollTimeInSec(3);

        // to set it scrollable automatically
        // we use below method.
        sliderView.setAutoCycle(true);

        // to start autocycle below method is used.
        sliderView.startAutoCycle();

    }


//    public void onCountryPickerClick(View view) {
//        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
//            @Override
//            public void onCountrySelected() {
//                String selected_country_code = ccp.getSelectedCountryCodeWithPlus();
//                Toast.makeText(LauncherActivity.this, selected_country_code, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void skip() {
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("isLogin", true);
                editor.putString("userP", "Guest");
                editor.commit();
                Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    private void login() {
        generateOtp();
        submitOtp();

    }


    private void generateOtp() {
        generateOtpBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNo = phoneNoEdt.getText().toString();
                if (TextUtils.isEmpty(phoneNo)) {
                    // when mobile number text field is empty
                    // displaying a toast message.
                    Toast.makeText(LauncherActivity.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                } else {
                    // if the text field is not empty we are calling our
                    // send OTP method for getting OTP from Firebase.
                    String phone = "+91" + phoneNo;
                    Toast.makeText(LauncherActivity.this, "Otp sent to +91" + phoneNo + " successfully.", Toast.LENGTH_SHORT).show();
                    otpEdt.setVisibility(View.VISIBLE);
                    phoneLyt.setVisibility(View.GONE);
                    submitOtpBTn.setVisibility(View.VISIBLE);
                    sendVerificationCode(phone);
                    hideKeyboard(generateOtpBTn);
                }
            }
        });
    }

    private void submitOtp() {
        // initializing on click listener
        // for verify otp button
        submitOtpBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = otpEdt.getText().toString();

                // validating if the OTP text field is empty or not.
                if (TextUtils.isEmpty(otp)) {
                    // if the OTP text field is empty display
                    // a message to user to enter OTP
                    Toast.makeText(LauncherActivity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                } else {
                    // if OTP field is not empty calling
                    // method to verify the OTP.
                    try {
                        verifyCode(otp);
                    } catch (Exception e) {
                        Toast toast = Toast.makeText(LauncherActivity.this, "Verification Code is wrong" + e.toString(), Toast.LENGTH_SHORT);
                        System.out.println("Wrong_Verif :"+e.toString());
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
            }
        });

    }


    //Login with Phone Using Firebase Authentication

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            editor.putBoolean("isLogin", true);
                            editor.putString("userP", phoneNo);
                            editor.putString("passW", otp);
                            editor.commit();
                            Toast.makeText(LauncherActivity.this, "Logging In", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                            startActivity(intent);


                             FirebaseUser user = task.getResult().getUser();
                            finish();
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            // Toast.makeText(LauncherActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            Toast.makeText(LauncherActivity.this, "Invalid OTP", Toast.LENGTH_LONG).show();
                            // Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    private void sendVerificationCode(String number) {
        // this method is used for getting
        // OTP on user phone number.
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)            // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(LauncherActivity.this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)           // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

        // callback method is called on Phone auth provider.

        private PhoneAuthProvider.OnVerificationStateChangedCallbacks

                // initializing our callbacks for on
                // verification callback method.
                mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            // below method is used when
            // OTP is sent from Firebase
            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
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
                Toast.makeText(LauncherActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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




}
