package android.example.barberbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.example.barberbooking.adapter.SliderAdapter;
import android.example.barberbooking.model.SliderData;
import android.os.Bundle;

import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class SaloonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saloon);
        setImages();
    }

    private void setImages() {

        int url1 = R.drawable.barbershopiamge1;
        int url2 = R.drawable.barbershopimage2;
        int url3 = R.drawable.barbershopimage3;
        int url4 = R.drawable.barbershopimage5;
        int url5 = R.drawable.barbershopimage6;
        int url6 = R.drawable.barbershopimage7;
        int url7 = R.drawable.barbershopimage8;
        // we are creating array list for storing our image urls.
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();

        // initializing the slider view.
        SliderView sliderView = findViewById(R.id.slider);

        // adding the urls inside array list
        sliderDataArrayList.add(new SliderData(url1));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url7));
        sliderDataArrayList.add(new SliderData(url3));
        sliderDataArrayList.add(new SliderData(url4));
        sliderDataArrayList.add(new SliderData(url5));
        sliderDataArrayList.add(new SliderData(url6));


        // passing this array list inside our adapter class.
        SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);

        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        // below method is used to
        // setadapter to sliderview.
        sliderView.setSliderAdapter(adapter);

        // below method is use to set
        // scroll time in seconds.
        sliderView.setScrollTimeInSec(3);

        // to set it scrollable automatically
        // we use below method.
        sliderView.setAutoCycle(true);

        // to start autocycle below method is used.
        sliderView.startAutoCycle();

    }
}