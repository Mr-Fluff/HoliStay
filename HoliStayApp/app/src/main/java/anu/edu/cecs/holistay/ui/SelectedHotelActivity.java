package anu.edu.cecs.holistay.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import anu.edu.cecs.holistay.hoteldetails.Hotel;
import anu.edu.cecs.holistay.hoteldetails.verification.FraudulentHotel;
import anu.edu.cecs.holistay.R;

/**
 * Activity to display details of hotel selected (using firebase).
 *
 * Functionalities:
 * 1) Display details of hotel
 * 2) Display GPS position
 * 3) Display name of host only if identity verified
 * 4) Notify user if hotel could be fraudulent
 *
 * @Author: Srinivasa Sai Damarla (u7370240)
 */
public class SelectedHotelActivity extends AppCompatActivity implements OnMapReadyCallback {

    TextView hotelName, hotelDescription;
    TextView hotelPrice, hotelReview, hotelAmenities;
    TextView hotelAccommodates, hotelBathrooms, hotelBedrooms;
    TextView hotelBeds, hotelType;
    TextView hostName;
    ImageView hotelImage;
    Button book;
    Hotel hotel;
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectedhotel);

        // Set views to variables
        setViewIDs();

        Intent intent = this.getIntent();
        Bundle bundle  = intent.getExtras();
        hotel = (Hotel) bundle.getSerializable("key");

        // Set selected hotel information
        setInformation();

        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);

        // Check if hotel is fraudulent based on criteria described in FraudulentHotel
        checkIfHotelFraudulent();

        // Set listeners
        setClickListeners();
    }

    public void setClickListeners(){
        // book listener: go to BookingStateActivity
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BookingStateActivity.class);
                intent.putExtra("hotel", hotel);
                startActivity(intent);
            }
        });
    }

    public void setViewIDs(){
        hotelImage = findViewById(R.id.hotelimage);
        hotelName = findViewById(R.id.hotelName1);
        hotelReview = findViewById(R.id.review_total);
        hotelAccommodates = findViewById(R.id.accommodates);
        hotelBathrooms = findViewById(R.id.bathroom);
        hotelBedrooms = findViewById(R.id.bedroom);
        hotelBeds = findViewById(R.id.beds);
        hotelPrice = findViewById(R.id.price);
        hotelType = findViewById(R.id._type);
        hotelDescription = findViewById(R.id.hotelDesc1);
        book = findViewById(R.id.button);
        hostName = findViewById(R.id.hostName);
        mapView = findViewById(R.id.mapView);
        hotelAmenities = findViewById(R.id.Amenities);
    }

    public void setInformation(){
        Picasso.get().load(hotel.getPicture_url()).into(hotelImage);
        hotelName.setText(hotel.getName());
        if(hotel.getDescription()!=null || !hotel.getDescription().equals(""))
            hotel.setDescription(hotel.getDescription().replaceAll("\\<.*?\\>", ""));
        hotelAccommodates.setText(hotel.getAccommodates());
        hotelBathrooms.setText(hotel.getBathrooms().substring(0,1));
        hotelBedrooms.setText(hotel.getBedrooms());
        hotelBeds.setText(hotel.getBeds());
        hotelPrice.setText(String.format("$%s", hotel.getPrice()));
        hotelType.setText(hotel.getProperty_type());
        hotelReview.setText(String.format("%s (%s)", hotel.getAvgReview(), hotel.getNumber_of_reviews()));
        hotelDescription.setText(hotel.getDescription());
        hotelAmenities.setText(hotel.getAmenities().substring(1, hotel.getAmenities().length()-1));
        if(hotel.getHost_identity_verified()!=null && hotel.getHost_identity_verified().equalsIgnoreCase("t")){
            hostName.setText(hotel.getHost_name());
        }
        else{
            hostName.setText("Anonymous");
        }
    }

    public void checkIfHotelFraudulent(){
        String fraud = new FraudulentHotel(hotel).checkHotel();
        if(fraud != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setCancelable(true);
            builder.setTitle("Potential Fraud");
            builder.setMessage(fraud);

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(hotel.getLatitude()!=null && hotel.getLongitude()!=null){
            com.google.android.gms.maps.model.LatLng latLng = new com.google.android.gms.maps.model.LatLng(Double.parseDouble(hotel.getLatitude()), Double.parseDouble(hotel.getLongitude()));
            googleMap.addMarker(new MarkerOptions().position( latLng)
                    .title(hotel.getName())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(hotel.getLatitude()), Double.parseDouble(hotel.getLongitude())), 15));
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }
}