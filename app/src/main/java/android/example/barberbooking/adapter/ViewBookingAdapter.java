package android.example.barberbooking.adapter;

import android.content.Intent;
import android.example.barberbooking.MainActivity;
import android.example.barberbooking.R;
import android.example.barberbooking.common.Common;
import android.example.barberbooking.fragments.HelpAssistantFragment;
import android.example.barberbooking.model.BookingModel;
import android.example.barberbooking.user.ShowBookingDetailsActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ViewBookingAdapter extends FirebaseRecyclerAdapter<BookingModel, ViewBookingAdapter.ViewBookingViewHolder> {


    public ViewBookingAdapter(@NonNull FirebaseRecyclerOptions<BookingModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewBookingViewHolder holder, int position, @NonNull BookingModel model) {


        holder.bycName.setText(model.getBycName());
        holder.bycId.setText(model.getBycId());
        String timeSlot = model.getTimeSlot() + " " + model.getDateSlot();
        holder.time.setText(timeSlot);
        holder.status.setText(model.getStatus());


        holder.bookAgainBtn.setOnClickListener(v -> {

            Intent intent = new Intent(v.getContext(), MainActivity.class);
            v.getContext().startActivity(intent);


        });


        holder.helpBtn.setOnClickListener(v -> {
            FragmentManager manager = ((AppCompatActivity)
                    v.getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, new HelpAssistantFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        });


        holder.item.setOnClickListener(v -> {
            BookingModel bookingModel = new BookingModel();
            bookingModel.setUserName(model.getUserName());
            bookingModel.setPricePaid(model.getPricePaid());
            bookingModel.setItemList(model.getItemList());
            bookingModel.setBycId(model.getBycId());
            bookingModel.setUserAddress(model.getUserAddress());
            bookingModel.setUserCity(model.getUserCity());
            bookingModel.setTrackingList(model.getTrackingList());
            bookingModel.setBookingId(model.getBookingId());
            bookingModel.setTimeSlot(model.getTimeSlot());
            bookingModel.setDateSlot(model.getDateSlot());
            Common.currentBooking = bookingModel;



            Intent intent = new Intent(v.getContext(), ShowBookingDetailsActivity.class);
            v.getContext().startActivity(intent);

        });

    }


    @NonNull
    @Override
    public ViewBookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_booking_design, parent, false);
        return new ViewBookingViewHolder(view);
    }

    public static class ViewBookingViewHolder extends RecyclerView.ViewHolder {
        CardView item;
        TextView bycName, bycId, time, status;
        LinearLayout helpBtn, bookAgainBtn;

        public ViewBookingViewHolder(@NonNull View itemView) {
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

