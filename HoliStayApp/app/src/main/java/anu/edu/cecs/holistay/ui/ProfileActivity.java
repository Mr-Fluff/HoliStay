package anu.edu.cecs.holistay.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import anu.edu.cecs.holistay.R;

/**
 * Activity to display details of user (using firebase).
 *
 * Functionalities:
 * 1) Display details of user
 * 2) Sign out option
 * 3) Enable user to view his bookings
 *
 * @Author: Srinivasa Sai Damarla (u7370240)
 */
public class ProfileActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FirebaseFirestore firebaseFirestore;
    String name;
    TextView userName;
    Button myBookings, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Set views to variables
        setViewIDs();

        // Firebase support
        firebaseFirestore = FirebaseFirestore.getInstance();

        // Set listeners
        setClickListeners();

        // Set user information
        setInformation();
    }

    public void setInformation(){
        firebaseFirestore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        name = documentSnapshot.get("name").toString();
                        userName.setText(name);
                    }
                });
    }

    public void setClickListeners(){
        // bottomNavigationView listener: set this activity to be true
        // Additionally, direct to either HotelListActivity or BookmarkActivity when clicked on their respected ids
        bottomNavigationView.setSelectedItemId(R.id.profile_view);
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
                        startActivity(new Intent(getApplicationContext(),HotelListActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    }

                    case R.id.profile_view:{
                        return true;
                    }
                }
                return false;
            }
        });

        // myBookings listener:
        myBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MyBookingsActivity.class));
            }
        });

        // logout listener: logout user
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

    public void setViewIDs(){
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        userName = findViewById(R.id.ProfileName);
        logout = findViewById(R.id.logOutButton);
        myBookings = findViewById(R.id.myBookingsButton);
    }
}