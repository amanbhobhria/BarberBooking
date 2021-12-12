package android.example.barberbooking.adapter;

import android.app.Activity;
import android.content.Intent;
import android.example.barberbooking.R;
import android.example.barberbooking.stylist.StylistActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Cities_List_Adapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] cityname;
    private final Integer[] imgid;


    public Cities_List_Adapter(Activity context, String[] cityname, Integer[] imgid) {
        super(context, R.layout.cities_list_design, cityname);
        this.context = context;
        this.cityname = cityname;
        this.imgid = imgid;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.cities_list_design, null, true);

        TextView cityName = (TextView) rowView.findViewById(R.id.cityName_inList);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.city_icon);


        cityName.setText(cityname[position]);
        imageView.setImageResource(imgid[position]);
        rowView.setOnClickListener(v -> {
            Intent intent = new Intent(context, StylistActivity.class);
            context.startActivity(intent);

        });
        return rowView;

    }


}
