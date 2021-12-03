package android.example.barberbooking.common;

import android.example.barberbooking.model.BookingModel;
import android.example.barberbooking.model.HomeVendorModel;
import android.example.barberbooking.model.SlotsModel;
import android.example.barberbooking.model.StylistModel;
import android.example.barberbooking.model.UserModel;
import android.example.barberbooking.model.VendorModel;
import android.widget.LinearLayout;

public class Common
{
    public static String date;
    public static VendorModel currentVendor;
    public static HomeVendorModel currentHmVendor;
    public static SlotsModel currentSlots;

    public static BookingModel currentBooking;

    public static StylistModel currentStylist;
    public static UserModel currentUser;
    public static boolean isUploaded =false;
    public static  boolean isEdit= false;


}
