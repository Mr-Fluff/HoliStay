package anu.edu.cecs.holistay.ui;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import anu.edu.cecs.holistay.R;
import anu.edu.cecs.holistay.hoteldetails.Hotel;
import anu.edu.cecs.holistay.hoteldetails.booking.Booking;
import anu.edu.cecs.holistay.hoteldetails.booking.BookingServiceImpl;
import anu.edu.cecs.holistay.notification.Notification;
import anu.edu.cecs.holistay.notification.NotificationFactory;
import anu.edu.cecs.holistay.notification.NotificationType;
/**
 * Activity to display payment details page for customer to pay with card.
 *
 * Functionalities:
 * 1) Display all payment details field for the customer to pay for the room.
 * 2) if the all fields are not filled then toasting the error message.
 * 3) once all fields are filled then storing the data in the firebase, sending notification and displaying the next stage.(Confirmed Payment)
 * @Author: Saurabh Ghag (u7370442)
 */
public class PaymentState extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_state);
        Button pay = (Button) findViewById(R.id.button3);
        BookingServiceImpl bookingService = new BookingServiceImpl();
        EditText customerName = (EditText) findViewById(R.id.editTextTextPersonName);
        EditText cardNo1 = (EditText) findViewById(R.id.editTextTextPersonName4);
        EditText cardNo2 = (EditText) findViewById(R.id.editTextTextPersonName5);
        EditText cardNo3 = (EditText) findViewById(R.id.editTextTextPersonName6);
        EditText cardNo4 = (EditText) findViewById(R.id.editTextTextPersonName7);
        EditText cvv = (EditText) findViewById(R.id.editTextTextPersonName8);
        EditText date = (EditText) findViewById(R.id.editTextDate);
        Booking booking = (Booking) getIntent().getExtras().get("booking");
        Hotel hotel = (Hotel) getIntent().getExtras().get("hotel");

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(customerName.getText().toString()== null ||customerName.getText().toString().equals("")||
                        cardNo1.getText().toString()== null || cardNo1.getText().toString().equals("")||
                        cardNo2.getText().toString()== null || cardNo2.getText().toString().equals("")||
                        cardNo3.getText().toString()== null || cardNo3.getText().toString().equals("")||
                        cardNo4.getText().toString()== null || cardNo4.getText().toString().equals("")||
                        cvv.getText().toString()== null || cvv.getText().toString().equals("")||
                        date.getText().toString()== null || date.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Complete payment details to proceed further.", Toast.LENGTH_LONG).show();

                }
                else {

                    Intent intent = new Intent(getApplicationContext(), ConfirmedPayment.class);
                    NotificationManager notificationManager = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        notificationManager = getSystemService(NotificationManager.class);
                    }
                    Notification pushNotification = new NotificationFactory().createNotification(hotel, null, NotificationType.PUSH, getApplicationContext(), notificationManager);
                    Notification emailNotification = new NotificationFactory().createNotification(hotel, booking.getContactEmail(), NotificationType.EMAIL, getApplicationContext(), notificationManager);
                    pushNotification.notifyUser();
                    emailNotification.notifyUser();
                    bookingService.book(booking, FirebaseFirestore.getInstance(), FirebaseAuth.getInstance().getCurrentUser());
                    startActivity(intent);

                }
            }
        });


    }
}
