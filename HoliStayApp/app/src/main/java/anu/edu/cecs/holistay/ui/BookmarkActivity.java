package anu.edu.cecs.holistay.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import anu.edu.cecs.holistay.R;
import anu.edu.cecs.holistay.hoteldetails.Hotel;
import anu.edu.cecs.holistay.ui.helper.ListingAdapter;

/**
 * Activity to display details of bookmarked hotels w.r.t a user (using firebase).
 *
 * Functionalities:
 * 1) Display all bookmarked hotels
 *
 * @Author: Srinivasa Sai Damarla (u7370240)
 */
public class BookmarkActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView ;
    ListingAdapter listingAdapter;
    ArrayList<Hotel> data;
    FirebaseFirestore fireStoreDB;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        // Set views to variables
        setViewIDs();
        data = new ArrayList<>();

        // Set listeners
        setClickListeners();

        listingAdapter = new ListingAdapter(data);
        fireStoreDB = FirebaseFirestore.getInstance();
        fireStoreDB.collection("bookmarks").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                List<String> bookmarks = (List<String>)documentSnapshot.get("hotels");
                if(bookmarks!=null && bookmarks.size() != 0) {
                    fireStoreDB.collection("listingsBoston").whereIn("id",bookmarks)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Hotel hotelCard = new Hotel();
                                            if(document.getData().get("id") != null)
                                                hotelCard.setListing_id(document.getData().get("id").toString());
                                            if (document.getData().get("name") != null)
                                                hotelCard.setName(document.getData().get("name").toString());
                                            if (document.getData().get("number_of_reviews") != null)
                                                hotelCard.setNumber_of_reviews(document.getData().get("number_of_reviews").toString());
                                            if (document.getData().get("description") != null)
                                                hotelCard.setDescription(document.getData().get("description").toString());
                                            if (document.getData().get("accommodates") != null)
                                                hotelCard.setAccommodates(document.getData().get("accommodates").toString());
                                            if (document.getData().get("bathrooms") != null)
                                                hotelCard.setBathrooms(document.getData().get("bathrooms").toString());
                                            if (document.getData().get("bedrooms") != null)
                                                hotelCard.setBedrooms(document.getData().get("bedrooms").toString());
                                            if (document.getData().get("beds") != null)
                                                hotelCard.setBeds(document.getData().get("beds").toString());
                                            if(document.getData().get("property_type") != null)
                                                hotelCard.setProperty_type(document.getData().get("property_type").toString());
                                            if (document.getData().get("price") != null)
                                                hotelCard.setPrice(document.getData().get("price").toString().substring(1));
                                            if (document.getData().get("review_scores_rating") != null)
                                                hotelCard.setAvgReview(document.getData().get("review_scores_rating").toString());
                                            if(document.getData().get("picture_url") != null)
                                                hotelCard.setPicture_url(document.getData().get("picture_url").toString());
                                            if(document.getData().get("amenities")!=null)
                                                hotelCard.setAmenities(document.getData().get("amenities").toString());
                                            if(document.getData().get("latitude")!=null)
                                                hotelCard.setLatitude(document.getData().get("latitude").toString());
                                            if(document.getData().get("longitude")!=null)
                                                hotelCard.setLongitude(document.getData().get("longitude").toString());
                                            if(bookmarks != null && bookmarks.contains(document.getData().get("id"))) {
                                                hotelCard.setBookmarked(true);
                                            }
                                            data.add(hotelCard);
                                        }
                                    } else {
                                        Log.d("", "Error getting documents: ", task.getException());
                                    }
                                    listingAdapter = new ListingAdapter(data);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    recyclerView.setAdapter(listingAdapter);
                                }
                            });
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "No Bookmarked Hotels!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void setClickListeners(){
        // bottomNavigationView listener: set this activity to be true
        // Additionally, direct to either HotelListActivity or ProfileActivity when clicked on their respected ids
        bottomNavigationView.setSelectedItemId(R.id.bookmarks_view);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bookmarks_view:{
                        return true;
                    }

                    case R.id.hotels_view:{
                        startActivity(new Intent(getApplicationContext(),HotelListActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    }

                    case R.id.profile_view:{
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void setViewIDs(){
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.bookmarkCardProgressBar);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }
}