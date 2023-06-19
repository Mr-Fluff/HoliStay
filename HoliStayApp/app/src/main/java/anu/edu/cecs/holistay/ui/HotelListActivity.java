package anu.edu.cecs.holistay.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Timer;

import anu.edu.cecs.holistay.DataStream;
import anu.edu.cecs.holistay.HoliStayData;
import anu.edu.cecs.holistay.datastructures.AVLTree;
import anu.edu.cecs.holistay.hoteldetails.Hotel;
import anu.edu.cecs.holistay.Logger;
import anu.edu.cecs.holistay.R;
import anu.edu.cecs.holistay.ui.helper.ListingAdapter;
import anu.edu.cecs.holistay.Utils;

/**
 * List all hotels (using firebase).
 *
 * Functionalities:
 * 1) Set main navigation menu to go to either Bookmarks or Profile
 * 2) Search bar to type a query according to grammar defined
 *
 * @Authors: Srinivasa Sai Damarla (u7370240), Nakul Nambiar (u7433687), Aishwarya Sonavane (u7173560)
 */
public class HotelListActivity extends AppCompatActivity{
    RecyclerView recyclerView ;
    ListingAdapter listingAdapter;
    ArrayList<Hotel> data;
    FirebaseFirestore fireBaseFireStore;
    ImageView searchButton;
    EditText searchText;
    static AVLTree tree ;
    ProgressBar progressBar;
    BottomNavigationView bottomNavigationView;

    private Timer timer;

    private int writeCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotellist);

        if(FirebaseAuth.getInstance().getCurrentUser() == null) startActivity(new Intent(getApplicationContext(), LoginActivity.class));

        // Set views to variables
        setViewIDs();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Hotel> hotels = HoliStayData.getInstance(getApplicationContext()).getHotels();
        DataStream dataStream = new DataStream(getApplicationContext(), hotels, fireBaseFireStore);
        dataStream.scheduleDataUpload();
        data = new ArrayList<>();

        // Set listeners
        setClickListeners();

        listingAdapter = new ListingAdapter(data);
        fireBaseFireStore = FirebaseFirestore.getInstance();
        fireBaseFireStore.collection("bookmarks").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                List<String> bookmarks = (List<String>)documentSnapshot.get("hotels");
                fireBaseFireStore.collection("listingsBoston").limit(50)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Hotel hotelCard = new Hotel();
                                        Logger.getInstance().info(this.getClass(), "Completed Data fetch!");
                                        if(document.getData().get("id") != null && !document.getData().get("id").toString().equals(""))
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
                                            hotelCard.setPrice(document.getData().get("price").toString().trim().substring(1).replaceAll(",",""));
                                        if (document.getData().get("review_scores_rating") != null && !document.getData().get("review_scores_rating").toString().equals(""))
                                            hotelCard.setAvgReview(document.getData().get("review_scores_rating").toString());
                                        if(document.getData().get("picture_url") != null)
                                            hotelCard.setPicture_url(document.getData().get("picture_url").toString());
                                        if(document.getData().get("amenities")!=null)
                                            hotelCard.setAmenities(document.getData().get("amenities").toString());
                                        if(document.getData().get("latitude")!=null)
                                            hotelCard.setLatitude(document.getData().get("latitude").toString());
                                        if(document.getData().get("longitude")!=null)
                                            hotelCard.setLongitude(document.getData().get("longitude").toString());
                                        if(document.getData().get("neighbourhood")!=null)
                                            hotelCard.setNeighbourhood(document.getData().get("neighbourhood").toString());
                                        if(document.getData().get("host_identity_verified")!=null)
                                            hotelCard.setHost_identity_verified(document.getData().get("host_identity_verified").toString());
                                        if(document.getData().get("host_name")!=null)
                                            hotelCard.setHost_name(document.getData().get("host_name").toString());
                                        if(bookmarks != null && bookmarks.contains(document.getData().get("id")))
                                            hotelCard.setBookmarked(true);
                                        if(tree==null){
                                            tree =  new AVLTree(hotelCard);
                                        }
                                        else {
                                            tree = tree.insert(hotelCard);
                                        }
                                    }
                                } else {
                                    Log.d("", "Error getting documents: ", task.getException());
                                }
                                data = tree.treeToArraylistInorder(tree);
                                listingAdapter = new ListingAdapter(data);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView.setAdapter(listingAdapter);
                            }
                        });
            }
        });

    }

    public void setClickListeners(){
        // bottomNavigationView listener: set this activity to be true
        // Additionally, direct to either BookmarkActivity or ProfileActivity when clicked on their respected ids
        bottomNavigationView.setSelectedItemId(R.id.hotels_view);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bookmarks_view:{
                        startActivity(new Intent(getApplicationContext(),BookmarkActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    }

                    case R.id.hotels_view:{
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

        // searchButton listener: read searchText and if it isn't empty, try to go to SearchResultActivity
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = searchText.getText().toString();
                if(searchQuery.equals("")){
                    Toast.makeText(getApplicationContext(),"Search cannot be empty",Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        searchText.setText("");
                        Intent intent = new Intent(getApplicationContext(),SearchResultActivity.class);
                        intent.putExtra("searchQuery",searchQuery);
                        startActivity(intent);
                    }catch (IllegalArgumentException e){
                        Toast.makeText(getApplicationContext(),"Invalid search",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void setViewIDs(){
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.hotelCardProgressBar);
        searchButton = findViewById(R.id.searchButton);
        searchText = findViewById(R.id.searchHotel);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }
}



