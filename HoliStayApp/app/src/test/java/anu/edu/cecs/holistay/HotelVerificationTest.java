package anu.edu.cecs.holistay;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import anu.edu.cecs.holistay.hoteldetails.Hotel;
import anu.edu.cecs.holistay.hoteldetails.verification.FraudulentHotel;


public class HotelVerificationTest {

    @Test
    public void checkHotelValid() {
        Hotel hotelCard = new Hotel("1", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "t", "","",false);
        FraudulentHotel fraudulentHotel = new FraudulentHotel(hotelCard);
        assertEquals(null, fraudulentHotel.checkHotel());
    }

    @Test
    public void checkHotelInValid() {
        Hotel hotelCard = new Hotel("1", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "f", "","",false);
        FraudulentHotel fraudulentHotel = new FraudulentHotel(hotelCard);
        assertEquals("Description seems suspicious.Host is unverified.", fraudulentHotel.checkHotel());
    }
}
