package android.example.barberbooking.vendor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.example.barberbooking.R;
import android.example.barberbooking.common.Common;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StatusActivity extends AppCompatActivity {
    TextView reqIdTxt, statusTxt, noreqLyt, waitingTxt;

    LinearLayout stsLyt, showStstLyt;
    ProgressBar progressBar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("hmvendor");


        try {
            initialize();
            setStatus();
        }
        catch (Exception e)
        {
            Toast.makeText(StatusActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
        }

    }

    private void setMessage(String n1, String n2, String name) {


        if (n1.trim().toLowerCase().equals(n2.trim().toLowerCase())) {

            String approvedMessage = "Congratulations! " + name + getString(R.string.approve_txt);
            waitingTxt.setText(approvedMessage);


        }
    }


    private void initialize() {
        reqIdTxt = findViewById(R.id.reqstid);
        statusTxt = findViewById(R.id.reqstStatus);

        stsLyt = findViewById(R.id.statusLyt);
        noreqLyt = findViewById(R.id.noRequestTXt);
        waitingTxt = findViewById(R.id.waitTxt);

        progressBar = findViewById(R.id.progressStatus);
        showStstLyt = findViewById(R.id.showStatusLyt);


    }

    private void setStatus() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot sp : snapshot.getChildren()) {



                    try {

                        if (sp.getKey().equals(Common.currentUser.getPhone())) {

                            reqIdTxt.setText(sp.getKey());    //
                            statusTxt.setText(sp.child("status").getValue().toString());

                            stsLyt.setVisibility(View.VISIBLE);
                            noreqLyt.setVisibility(View.GONE);

                            String Approve = "Approved";
                            String name = (sp.child("ownerName").getValue().toString());


                            setMessage(statusTxt.getText().toString(), Approve, name);

                        }
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(StatusActivity.this,"Error"+e.toString(),Toast.LENGTH_SHORT).show();
                    }

                }

                progressBar.setVisibility(View.GONE);
                showStstLyt.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(StatusActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        });


    }

}