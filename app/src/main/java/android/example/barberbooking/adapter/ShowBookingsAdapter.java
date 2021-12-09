package android.example.barberbooking.adapter;

import android.content.Context;
import android.content.Intent;
import android.example.barberbooking.MainActivity;
import android.example.barberbooking.R;
import android.example.barberbooking.model.BookingModel;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ShowBookingsAdapter extends RecyclerView.Adapter<ShowBookingsAdapter.BookingsViewHolder> {
    List<BookingModel> list;
    Context context;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;


    public ShowBookingsAdapter(List<BookingModel> list, Context context) {
        this.list = list;
        this.context = context;
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("bookings");

    }

    @NonNull
    @Override
    public ShowBookingsAdapter.BookingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_booking_design, parent, false);
        return new BookingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowBookingsAdapter.BookingsViewHolder holder, int position) {

        holder.bycName.setText(list.get(position).getBycName());
        holder.bycId.setText(list.get(position).getBycId());
        String timeSlot = list.get(position).getTimeSlot() + " " + list.get(position).getDateSlot();
        holder.time.setText(timeSlot);
        holder.status.setText(list.get(position).getStatus());


//        holder.helpBtn.setOnClickListener(v -> {
//       Intent intent = new Intent(context, HelpAssistantActivity.class);
//       context.startActivity(intent);
//        });
        holder.bookAgainBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        });


//        holder.item.setOnClickListener(v -> {
//
//
//
//
//            Intent intent = new Intent(context, ShowBookingDetailsActivity.class);
//            context.startActivity(intent);
//        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class BookingsViewHolder extends RecyclerView.ViewHolder {
        CardView item;
        TextView bycName, bycId, time, status;
        LinearLayout helpBtn, bookAgainBtn;

        public BookingsViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.userShowBookingCardView);
            bycName = itemView.findViewById(R.id.stylistNameUserBookTxt);
            bycId = itemView.findViewById(R.id.bycIdUserBookTxt);
            time = itemView.findViewById(R.id.dateTimeUserBookTxt);
            status = itemView.findViewById(R.id.statusUserBookTxt);
            helpBtn = itemView.findViewById(R.id.needHelp_layt);
            bookAgainBtn = itemView.findViewById(R.id.book_again_layt);


        }
    }
}
