package anu.edu.cecs.holistay.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import anu.edu.cecs.holistay.R;
/**
 * Activity to display confirmed booking page for customer after card payment.
 *
 * Functionalities:
 * 1) After booking process completed redirecting customer to home page for further bookings.
 *
 * @Author: Saurabh Ghag (u7370442)
 */
public class ConfirmedPayment extends AppCompatActivity {
    Button newBooking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        newBooking = findViewById(R.id.newBooking);
        newBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HotelListActivity.class);
                startActivity(intent);
            }
        });
    }
}