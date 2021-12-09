package android.example.barberbooking.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.example.barberbooking.MainActivity;
import android.example.barberbooking.R;
import android.example.barberbooking.adapter.HmServicesAdapter;
import android.example.barberbooking.adapter.NotificationAdapter;
import android.example.barberbooking.model.NotificationModel;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    ImageView backBtn;
    RecyclerView recyclerView;
    RelativeLayout noNotLyt;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    int noOfNotif = 0;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference  = firebaseDatabase.getReference("notification");
        initialise();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
            getNotifications();
        }
        catch (Exception e)
        {
            Toast.makeText(NotificationActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
        }
        back();
    }

    private void getNotifications() {


        List<NotificationModel> list = new ArrayList<>();


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot sp : snapshot.getChildren()) {


                    NotificationModel notificationModel = sp.getValue(NotificationModel.class);


                    assert notificationModel != null;
                    notificationModel.setNid(sp.getKey());


                    list.add(notificationModel);
                    noOfNotif = noOfNotif+1;


                }

                if(noOfNotif == 0)
                {

                    noNotLyt.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }

                NotificationAdapter notificationAdapter = new NotificationAdapter(list, NotificationActivity.this);
                recyclerView.setAdapter(notificationAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void back() {
        backBtn.setOnClickListener(v -> NotificationActivity.super.onBackPressed());
    }


    private void initialise() {
        backBtn = findViewById(R.id.backNotifBtn);
        recyclerView = findViewById(R.id.notificationRecyclerView);
        noNotLyt =findViewById(R.id.noNotfLyt);

    }
}