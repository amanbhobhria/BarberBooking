package android.example.barberbooking.vendor.homefacility;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.example.barberbooking.R;
import android.example.barberbooking.common.Common;
import android.example.barberbooking.model.HomeVendorModel;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.ByteArrayOutputStream;

public class BankingDetailActivityHm extends AppCompatActivity {
    Button saveBankBtn, imageuploadBtn, clickphotBtn;
    MaterialSpinner accountTypeSpin;
    EditText bankName, bankAdress, benificiaryName, accNo, ifscCode;
    RelativeLayout bankphoto;
    LinearLayout addImgLyt;

    ProgressBar progressBar;
    // Uri filePath;
    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;

    ImageView imageshow;
    Uri image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banking_detail_hm);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        initialize();

        accountType();
        imageUpload();
        saveBankDetail();

    }


    private void initialize() {
        saveBankBtn = findViewById(R.id.saveBank);
        accountTypeSpin = findViewById(R.id.accountTypeSpin);
        bankName = findViewById(R.id.bankNameTxt);
        bankAdress = findViewById(R.id.bankAddressTxt);
        benificiaryName = findViewById(R.id.benificryTxt);
        accNo = findViewById(R.id.accNoTxt);
        ifscCode = findViewById(R.id.ifscNoTxt);

        bankphoto = findViewById(R.id.bankPhotoLyt);
        imageuploadBtn = findViewById(R.id.uploadPhotBtn);
        clickphotBtn = findViewById(R.id.clickPhotBtn);
        imageshow = findViewById(R.id.imageShow);
        addImgLyt = findViewById(R.id.addLyt);
        progressBar = findViewById(R.id.bnkProgress);


    }


    private void saveBankDetail() {
        saveBankBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();

            }
        });

    }



    private void accountType() {

        accountTypeSpin.setItems("Saving Account", "Current Account");
        accountTypeSpin.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {


            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();


            }
        });

    }

    private void upload() {
        HomeVendorModel homeVendorModel = Common.currentHmVendor;
        homeVendorModel.setBankname(bankName.getText().toString());
        homeVendorModel.setBankadress(bankAdress.getText().toString());
        homeVendorModel.setBenificaryname(benificiaryName.getText().toString());
        homeVendorModel.setAccountNo(accNo.getText().toString());
        homeVendorModel.setAccounttype(accountTypeSpin.getText().toString());
        homeVendorModel.setIfsccode(ifscCode.getText().toString());

        uploadImage(image);


    }


    //ImageUpload
    private void imageUpload() {
        imageuploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGalleryPermission();
            }
        });

        clickphotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCameraPermission();
            }
        });
    }

    private void checkCameraPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCameraFun();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 102);
        }

    }


    private void checkGalleryPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGalleryFun();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE}, 104);
        }
    }


    private void openCameraFun() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intent, 100);
    }

    private void openGalleryFun() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 103);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {

            if (data != null) {

                Bitmap photo = (Bitmap) data.getExtras().get("data");
                image = getImageUri(getApplicationContext(), photo);
                imageshow.setImageBitmap(photo);
                imageshow.setVisibility(View.VISIBLE);
                addImgLyt.setVisibility(View.GONE);

            }


        }
        if (requestCode == 103) {
            if (data != null) {


                Uri selectedImg = data.getData();
                imageshow.setImageURI(selectedImg);
                imageshow.setVisibility(View.VISIBLE);
                addImgLyt.setVisibility(View.GONE);
                image = selectedImg;


            }
        }

    }


    //Conversion of Bitmap to Uri
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    //Upload Image to Firebase and get Url of Image
    private void uploadImage(Uri filePath) {
        if (filePath != null) {
            String id = Common.currentHmVendor.getPhoneNo();
            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "images/" + id + "/" + "bankImage"
                    );

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot) {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();


                                    taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            HomeVendorModel homeVendorModel = Common.currentHmVendor;

                                            String url = task.getResult().toString();
                                            homeVendorModel.setBankPhoto(url);

                                            Intent intent = new Intent(BankingDetailActivityHm.this, GovtIdActivity.class);
                                            startActivity(intent);

                                            Toast
                                                    .makeText(getApplicationContext(),
                                                            "Image Uploaded!!",
                                                            Toast.LENGTH_SHORT)
                                                    .show();


                                            //next work with URL
                                        }
                                    });
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(getApplicationContext(),
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int) progress + "%");
                                }
                            });
        }
    }


}