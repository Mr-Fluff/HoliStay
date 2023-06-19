package anu.edu.cecs.holistay.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import anu.edu.cecs.holistay.R;
import anu.edu.cecs.holistay.hoteldetails.booking.Booking;
import anu.edu.cecs.holistay.ui.helper.BookingAdapter;

/**
 * List all previous bookings (using firebase).
 *
 * Functionalities:
 * 1) Ability to cancel booking
 *
 * @Author: Srinivasa Sai Damarla (u7370240)
 */
public class MyBookingsActivity extends AppCompatActivity {

    RecyclerView recyclerView ;
    BookingAdapter bookingAdapter;
    ArrayList<Booking> data;
    FirebaseFirestore fireStoreDB;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        // Set views to variables
        setViewIDs();

        data = new ArrayList<>();
        bookingAdapter = new BookingAdapter(data);
        fireStoreDB = FirebaseFirestore.getInstance();
        fireStoreDB.collection("bookings").whereEqualTo("assignedTo",FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Booking hotelCard = document.toObject(Booking.class);
                                data.add(hotelCard);
                            }
                        } else {
                            Log.d("", "Error getting documents: ", task.getException());
                        }
                        bookingAdapter = new BookingAdapter(data);
                        progressBar.setVisibility(View.INVISIBLE);
                        recyclerView.setAdapter(bookingAdapter);
                    }
                });
    }

    public void setViewIDs(){
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.hotelCardProgressBar);
    }
}