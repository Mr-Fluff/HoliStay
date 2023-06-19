package anu.edu.cecs.holistay.hoteldetails.booking;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import anu.edu.cecs.holistay.Logger;

/**
 *
 * BookingServiceImpl is the implementation for BookingService
 * @author Nakul Nambiar (u7433687)
 */
public class BookingServiceImpl implements BookingService {

    @Override
    public void book(Booking booking, FirebaseFirestore firebaseFirestore, FirebaseUser firebaseUser) {
        booking.setAssignedTo(firebaseUser.getUid());
        DocumentReference newBookingRef = firebaseFirestore.collection("bookings").document();
        booking.setId(newBookingRef.getId());
        newBookingRef.set(booking).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Logger.getInstance().info(BookingServiceImpl.class, "Booking Added to FireStore");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Logger.getInstance().error(BookingServiceImpl.class, e);
            }
        });
    }

    @Override
    public double calculatePrice(Booking booking, double hotelPrice) {
        double price =  hotelPrice * (double) booking.getRooms();
        if(booking.getPaymentType() == PaymentType.CARD) {
            price = price * 1.02;
        }
        return price;
    }

    @Override
    public void cancel(Booking booking, FirebaseFirestore firebaseFirestore) {
        booking.setStatus(Status.CANCELLED);
        firebaseFirestore.collection("bookings").document(booking.getId()).set(booking).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Logger.getInstance().info(BookingServiceImpl.class, "Booking Cancelled");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Logger.getInstance().error(BookingServiceImpl.class, e);
            }
        });
    }
}
