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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class ImageHmActivity extends AppCompatActivity {
    Button next, camera, gallery;
    ImageView imageShow;
    LinearLayout addImageLyt;

    Uri image;

    FirebaseStorage storage;
    StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_hm);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        intialize();
        submit();
        imageUpload();

    }


    private void intialize() {
        next = findViewById(R.id.submitImghm);
        camera = findViewById(R.id.camerahmImg);
        gallery = findViewById(R.id.galleryhmBtn);
        imageShow = findViewById(R.id.imagehmShow);
        addImageLyt = findViewById(R.id.addImgLyt);

    }


    private void submit() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage(image);
            }
        });


    }




    private void imageUpload() {
        //FrontImage
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkGalleryPermission();

            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
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
                imageShow.setImageBitmap(photo);
                imageShow.setVisibility(View.VISIBLE);
                addImageLyt.setVisibility(View.GONE);


            }


        }
        if (requestCode == 103) {
            if (data != null) {


                Uri selectedImg = data.getData();


                image = selectedImg;

                imageShow.setImageURI(selectedImg);
                imageShow.setVisibility(View.VISIBLE);
                addImageLyt.setVisibility(View.GONE);

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
            String id = Common.currentDetails2.getPhoneNo();
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
                            "images/" + id + "/" + "workimage"
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
                                            HomeVendorModel homeVendorModel = Common.currentDetails2;

                                            String url = task.getResult().toString();
                                            homeVendorModel.setWorkImg(url);

                                            Toast
                                                    .makeText(getApplicationContext(),
                                                            "Image Uploaded!!",
                                                            Toast.LENGTH_SHORT)
                                                    .show();
                                            Intent intent = new Intent(ImageHmActivity.this, AddServicesActivity.class);
                                            startActivity(intent);



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






