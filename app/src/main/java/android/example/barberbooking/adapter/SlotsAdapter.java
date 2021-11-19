package android.example.barberbooking.adapter;


import android.content.Context;
import android.content.Intent;
import android.example.barberbooking.R;
import android.example.barberbooking.common.Common;
import android.example.barberbooking.model.SlotsModel;
import android.example.barberbooking.stylist.StylistActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class SlotsAdapter extends RecyclerView.Adapter<SlotsAdapter.HmBookingsViewHolder> {
    List<SlotsModel> list;
    Context context;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;


    public SlotsAdapter(List<SlotsModel> list, Context context) {
        this.list = list;
        this.context = context;
        firebaseDatabase = FirebaseDatabase.getInstance();

        String phone = Common.currentStylist.getPhoneNo();

        reference = firebaseDatabase.getReference("hmvendor"+"/"+phone+"/"+"days");


    }

    @NonNull
    @Override
    public SlotsAdapter.HmBookingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slots_design, parent, false);

        return new HmBookingsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SlotsAdapter.HmBookingsViewHolder holder, int position) {



            holder.daySlot1.setText(list.get(position).getSlot1());
            holder.daySlot2.setText(list.get(position).getSlot2());
            holder.daySlot3.setText(list.get(position).getSlot3());
            holder.daySlot4.setText(list.get(position).getSlot4());
            holder.daySlot5.setText(list.get(position).getSlot5());
            holder.daySlot6.setText(list.get(position).getSlot6());

            SlotsModel slotsModel = new SlotsModel();
            slotsModel.setDateId(list.get(position).getDateId());
            Common.currentSlots = slotsModel;

            selectSlots(holder.nine,holder.daySlot1,"09:00 AM - 11:00AM");
            selectSlots(holder.eleven,holder.daySlot2,"11:00AM - 12:00 AM");
            selectSlots(holder.one,holder.daySlot3,"01:00 PM - 03:00 PM");
            selectSlots(holder.three,holder.daySlot4,"03:00 PM - 05:00 PM");
            selectSlots(holder.five,holder.daySlot5,"05:00 PM - 07:00 PM");
            selectSlots(holder.seven,holder.daySlot6,"07:00 PM - 09:00 PM");



    }

    private void selectSlots(LinearLayout slot,TextView slotTxt, String slotTime)
    {

        slot.setOnClickListener(v -> {

            if(slotTxt.getText().toString().equalsIgnoreCase("Booked"))
            {
                Toast.makeText(context,"Not Available",Toast.LENGTH_SHORT).show();
            }
            else if (slotTxt.getText().toString().equalsIgnoreCase("UnAvailable"))
            {
                Toast.makeText(context,"UnAvailable",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Common.selectedSlot = slotTime;
                Intent intent = new Intent(context, StylistActivity.class);
                context.startActivity(intent);

            }


        });
    }





    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class HmBookingsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout item, nine, eleven, one, three, five, seven;


      //  TextView dateTxt;
        TextView daySlot1;
        TextView daySlot2;
        TextView daySlot3;
        TextView daySlot4;
        TextView daySlot5;
        TextView daySlot6;


        public HmBookingsViewHolder(@NonNull View itemView) {
            super(itemView);
            //dateTxt = itemView.findViewById(R.id.dayTxtView);
            daySlot1 = itemView.findViewById(R.id.daySlot1);
            daySlot2 = itemView.findViewById(R.id.daySlot2);
            daySlot3 = itemView.findViewById(R.id.daySlot3);
            daySlot4 = itemView.findViewById(R.id.daySlot4);
            daySlot5 = itemView.findViewById(R.id.daySlot5);
            daySlot6 = itemView.findViewById(R.id.daySlot6);


            nine = itemView.findViewById(R.id.nine);
            eleven = itemView.findViewById(R.id.eleven);
            one = itemView.findViewById(R.id.one);
            three = itemView.findViewById(R.id.three);
            five = itemView.findViewById(R.id.five);
            seven = itemView.findViewById(R.id.seven);


            item = itemView.findViewById(R.id.slots_layout_linear);


        }
    }
}