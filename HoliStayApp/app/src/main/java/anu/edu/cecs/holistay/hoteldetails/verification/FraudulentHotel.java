package anu.edu.cecs.holistay.hoteldetails.verification;

import android.location.Location;

import anu.edu.cecs.holistay.hoteldetails.Hotel;

/**
 * Check if hotel could be fraudulent
 *
 * Factors:
 * 1) if hotel location is null
 * 2) if given hotel location is not in Boston
 * 3) if description is less than 30 characters
 * 4) if host is verified
 *
 * @Author: Nakul Nambiar (u7433687)
 */
public class FraudulentHotel {

    Hotel hotel;

    private final Double BOSTON_LAT = 42.3601;
    private final Double BOSTON_LON = 71.0589;

    public FraudulentHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    private boolean checkLocation() {
        return ((hotel.getLatitude() == null || hotel.getLatitude().equals("")) && (hotel.getLongitude() == null || hotel.getLongitude().equals("")));
    }

    private boolean checkDistance() {
        Location centralLocation = new Location("Center");
        centralLocation.setLatitude(BOSTON_LAT);
        centralLocation.setLongitude(BOSTON_LON);
        Location hotelLocation = new Location("Hotel");
        hotelLocation.setLatitude(Math.abs(Double.parseDouble(hotel.getLatitude())));
        hotelLocation.setLongitude(Math.abs(Double.parseDouble(hotel.getLongitude())));

        float location = centralLocation.distanceTo(hotelLocation);
        return location > 75000;
    }

    private boolean checkDescription() {
        return !(hotel.getDescription()!= null && hotel.getDescription().length() > 30);
    }

    private boolean checkHost() {
        return !(hotel.getHost_identity_verified() != null && hotel.getHost_identity_verified().equals("t"));
    }

    public String checkHotel() {
        String cases = "";
        int count = 0;
        if(checkLocation()) {
            count++;
            cases += "Location details not provided.";
        }
        if(!checkLocation() && checkDistance()) {
            count++;
            cases += "Distance from central Boston is greater than 75Kms.";
        }
        if(checkDescription()) {
            count++;
            cases += "Description seems suspicious.";
        }
        if(checkHost()) {
            count++;
            cases += "Host is unverified.";
        }
        if(count < 2) return null;
        else return cases;
    }

}
