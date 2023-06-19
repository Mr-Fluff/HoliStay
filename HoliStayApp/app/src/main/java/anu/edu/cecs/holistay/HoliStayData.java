package anu.edu.cecs.holistay;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import anu.edu.cecs.holistay.hoteldetails.Hotel;

/**
 * @author Nakul Nambiar (u7433687)
 */
public class HoliStayData extends Application {
    private List<Hotel> hotels;

    public HoliStayData(List<Hotel> hotels) {
        this.hotels = hotels;
    }

    private static HoliStayData holiStayData = null;

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(ArrayList<Hotel> hotels) {
        this.hotels = hotels;
    }

    public static HoliStayData getInstance(Context context) {
        if(holiStayData == null) {
            holiStayData = new HoliStayData(Utils.getHotels("datastream.csv", context));
        }
        return holiStayData;
    }

}
