package anu.edu.cecs.holistay;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import anu.edu.cecs.holistay.hoteldetails.Hotel;

/**
 * This class is used to upload data from csv to firestore every x seconds
 * @author Nakul Nambiar (u7433687)
 */
public class DataStream {

    private static final String HOTELS_FILE = "datastream.csv";

    /**
     * This variable sets the value of the next hotel to be read from the file.
     */
    private int nextCount = 0;

    private Context context;

    private List<Hotel> hotels;

    private FirebaseFirestore firebaseFirestore;

    public DataStream(Context context, List<Hotel> hotels, FirebaseFirestore firebaseFirestore) {
        this.nextCount = nextCount;
        this.context = context;
        this.hotels = hotels;
        this.firebaseFirestore = firebaseFirestore;
    }

    public int getNextCount() {
        return nextCount;
    }

    public void setNextCount(int nextCount) {
        this.nextCount = nextCount;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }

    public FirebaseFirestore getFirebaseFirestore() {
        return firebaseFirestore;
    }

    public void setFirebaseFirestore(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    /**
     * Schedules task to run every hour
     * @author Nakul Nambiar (u7433687)
     */
    public void scheduleDataUpload() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                addHotelToFirebase();
            }
        }, 0, 3600000);
    }

    /**
     * Uploads data to firebase
     * @author Nakul Nambiar (u7433687)
     */
    public void addHotelToFirebase() {
        if(hotels.size() > nextCount) {
            firebaseFirestore = FirebaseFirestore.getInstance();
            int next = nextCount++;
            firebaseFirestore.collection("listingsBoston").document(hotels.get(next).getListing_id())
                    .set(hotels.get(next))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Logger.getInstance().info(DataStream.class, "Successfully Uploaded Data!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Logger.getInstance().error(DataStream.class, e);
                        }
                    });
        }
    }
}
