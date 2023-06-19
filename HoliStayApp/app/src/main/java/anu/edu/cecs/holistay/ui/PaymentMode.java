package anu.edu.cecs.holistay.ui;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import anu.edu.cecs.holistay.R;
import anu.edu.cecs.holistay.hoteldetails.Hotel;
import anu.edu.cecs.holistay.hoteldetails.booking.Booking;
import anu.edu.cecs.holistay.hoteldetails.booking.BookingServiceImpl;
import anu.edu.cecs.holistay.hoteldetails.booking.PaymentType;
import anu.edu.cecs.holistay.notification.Notification;
import anu.edu.cecs.holistay.notification.NotificationFactory;
import anu.edu.cecs.holistay.notification.NotificationType;
/**
 * Activity to display Payment mode page for customer to choose the payment option.
 *
 * Functionalities:
 * 1) Display payment mode radio button i.e cash and card
 * 2) Capturing all the data available in the field and passing that data to the next stage.
 * 3) if the card button is selected then passing that data to the next stage.(PaymentsState)
 * 4) if the cash button is selected then storing the data in the firebase, sending notification and displaying the next stage(ConfirmedBooking).
 *
 * @Author: Saurabh Ghag (u7370442)
 */

public class PaymentMode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_mode);

        RadioButton card = (RadioButton) findViewById(R.id.radioButton);
        RadioButton cash = (RadioButton) findViewById(R.id.radioButton2);
        Button proceed = (Button) findViewById(R.id.button4);
        TextView totalPrice = findViewById(R.id.priceText);
        Hotel hotel = (Hotel) getIntent().getExtras().get("hotel");
        Booking booking = (Booking) getIntent().getExtras().get("booking");
        BookingServiceImpl bookingService = new BookingServiceImpl();
        card.setChecked(true);

        booking.setPaymentType(PaymentType.CARD);
        double price = bookingService.calculatePrice(booking, Double.parseDouble(hotel.getPrice()));
        double roundPrice = Math.round(price*100) / 100.0;
        String s = "Total Price "+ roundPrice;
        totalPrice.setText(s);
        booking.setTotalPrice(price);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(card.isChecked()) {
                    Intent intent = new Intent(getApplicationContext(), PaymentState.class);
                    intent.putExtra("booking", booking);
                    intent.putExtra("hotel", hotel);
                    startActivity(intent);
                }
                else if(cash.isChecked()) {
                    Intent intent = new Intent(getApplicationContext(), ConfirmedBooking.class);
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

        card.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    card.setChecked(true);
                    booking.setPaymentType(PaymentType.CARD);
                    double price = bookingService.calculatePrice(booking, Double.parseDouble(hotel.getPrice()));
                    double roundPrice = Math.round(price*100) / 100.0;
                    String s = "Total Price $"+ roundPrice;
                    totalPrice.setText(s);
                    booking.setTotalPrice(price);
                    cash.setChecked(false);
                }
            }
        });
        cash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    card.setChecked(false);
                    booking.setPaymentType(PaymentType.CASH);
                    double price = bookingService.calculatePrice(booking, Double.parseDouble(hotel.getPrice()));
                    double roundPrice = Math.round(price*100) / 100.0;
                    String s = "Total Price $"+ roundPrice;
                    totalPrice.setText(s);
                    booking.setTotalPrice(price);
                    cash.setChecked(true);
                }
            }
        });
    }
}