package anu.edu.cecs.holistay.hoteldetails.booking;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

/**
 *
 * BookingService is an interface which provides the functions to make and cancel a booking
 * @author Nakul Nambiar (u7433687)
 */
public interface BookingService {

    public void book(Booking booking, FirebaseFirestore firebaseFirestore, FirebaseUser firebaseUser);
    public double calculatePrice(Booking booking, double hotelPrice);
    public void cancel(Booking booking, FirebaseFirestore firebaseFirestore);

}
