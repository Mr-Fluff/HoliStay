package anu.edu.cecs.holistay;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import anu.edu.cecs.holistay.hoteldetails.Hotel;
import anu.edu.cecs.holistay.hoteldetails.booking.Booking;
import anu.edu.cecs.holistay.hoteldetails.booking.BookingServiceImpl;
import anu.edu.cecs.holistay.hoteldetails.booking.PaymentType;


public class BookingTest {

    @Test
    public void checkPrice() {
        Hotel hotelCard = new Hotel("1", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "false", "","",false);
        Booking booking = new Booking();
        booking.setHotelId(hotelCard.getListing_id());
        booking.setPaymentType(PaymentType.CARD);
        booking.setRooms(1);
        BookingServiceImpl bookingService = new BookingServiceImpl();
        assertEquals(255.00,bookingService.calculatePrice(booking, Double.parseDouble(hotelCard.getPrice().trim().substring(1).replaceAll(",",""))), 0.0);
    }
}
