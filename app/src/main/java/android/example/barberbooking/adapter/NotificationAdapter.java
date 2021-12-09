package android.example.barberbooking.adapter;

import android.content.Context;
import android.example.barberbooking.R;
import android.example.barberbooking.model.NotificationModel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewholder> {
    List<NotificationModel> list;
    Context context;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    public NotificationAdapter(List<NotificationModel> list, Context context) {
        this.list = list;
        this.context = context;
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference  = firebaseDatabase.getReference("notification");
    }


    @NonNull
    @Override
    public NotificationViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_design, parent, false);

        return new NotificationViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewholder holder, int position) {
         holder.titleTxt.setText(list.get(position).getTitle());
         holder.dateTxt.setText(list.get(position).getDate());
         holder.messageTxt.setText(list.get(position).getMessage());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class NotificationViewholder extends RecyclerView.ViewHolder {
        TextView dateTxt, titleTxt, messageTxt;
        CardView item;

        public NotificationViewholder(@NonNull View itemView) {
            super(itemView);
            dateTxt = itemView.findViewById(R.id.datetimeNotificationTxt);
            titleTxt = itemView.findViewById(R.id.titleNotificationTxt);
            messageTxt = itemView.findViewById(R.id.messageNotificationTxt);
            item = itemView.findViewById(R.id.notification_design_layt);
        }
    }
}
