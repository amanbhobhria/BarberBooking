package android.example.barberbooking.adapter;

import android.content.Context;
import android.content.Intent;
import android.example.barberbooking.R;
import android.example.barberbooking.common.Common;
import android.example.barberbooking.model.HomeVendorModel;
import android.example.barberbooking.model.SlotsModel;
import android.example.barberbooking.model.StylistModel;
import android.example.barberbooking.stylist.SearchResultStylists;
import android.example.barberbooking.stylist.StylistActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HmServicesAdapter extends RecyclerView.Adapter<HmServicesAdapter.HmServicesViewHolder> {
    List<HomeVendorModel> list;
    Context context;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;


    public HmServicesAdapter(List<HomeVendorModel> list, Context context) {
        this.list = list;
        this.context = context;
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("hmvendor");


    }


    @NonNull
    @Override
    public HmServicesAdapter.HmServicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stylist_deign, parent, false);

        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.stylistacctocitydesign, parent, false);


        if (context.getClass().toString().equals(SearchResultStylists.class.toString())) {

            return new HmServicesViewHolder(view1);

        } else {

            return new HmServicesViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull HmServicesAdapter.HmServicesViewHolder holder, int position) {
        if (list.get(position).getStatus().trim().equalsIgnoreCase("Approved".trim())) {

            holder.name.setText(list.get(position).getOwnerName());


            String bycId = "BYC "+list.get(position).getBycId();

            holder.id.setText(bycId);
            holder.city.setText(list.get(position).getCity());
            if(list.get(position).getBasePrice()==null)
            {
                holder.price.setText("₹249");
            }
            else {
                holder.price.setText(list.get(position).getBasePrice());
            }

            Picasso.get()
                    .load(list.get(position).getWorkImg())
                    .into(holder.workImg);

            holder.item.setOnClickListener(v -> {

                StylistModel stylistModel = new StylistModel();
                stylistModel.setBycId(list.get(position).getBycId());
                stylistModel.setWorkImg(list.get(position).getWorkImg());
                stylistModel.setPhoneNo(list.get(position).getPhoneNo());
                stylistModel.setServiceList(list.get(position).getServiceList());
                stylistModel.setOwnername(list.get(position).getOwnerName());
                stylistModel.setAddress(list.get(position).getAddress());
                stylistModel.setCity(list.get(position).getCity());



                stylistModel.setBasePrice(list.get(position).getBasePrice());


                Common.currentStylist = stylistModel;

                Intent intent = new Intent(context, StylistActivity.class);
                context.startActivity(intent);
            });


        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class HmServicesViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView name;
        TextView city;
        ImageView workImg;
        TextView price;
        LinearLayout item;


        public HmServicesViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.idStylist);
            name = itemView.findViewById(R.id.nameStylist);
            city = itemView.findViewById(R.id.addressStylist);
            workImg = itemView.findViewById(R.id.workimgStylist);
            price =itemView.findViewById(R.id.priceStylist);
            item = itemView.findViewById(R.id.stylist_design_layout);
        }
    }
}
