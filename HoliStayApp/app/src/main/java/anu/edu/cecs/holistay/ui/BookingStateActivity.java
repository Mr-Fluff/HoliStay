package anu.edu.cecs.holistay.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

import anu.edu.cecs.holistay.hoteldetails.Hotel;
import anu.edu.cecs.holistay.R;
import anu.edu.cecs.holistay.hoteldetails.booking.Booking;
import anu.edu.cecs.holistay.hoteldetails.booking.Status;
/**
 * Activity to display booking details page for customer to book the room.
 *
 * Functionalities:
 * 1) Display all booking details field for the customer to book the room.
 * 2) Capturing all the data available in the field and passing that data to the next stage.(Payment mode)
 * 3) if the all fields are not filled then toasting the error message.
 * @Author: Saurabh Ghag (u7370442)
 */
public class BookingStateActivity extends AppCompatActivity {

    private DatePickerDialog startDatePickerDialog;
    private DatePickerDialog endDatePickerDialog;
    private Button startDate;
    private Button endDate;
    FloatingActionButton addRoom;
    FloatingActionButton subtractRoom;
    int roomCounter=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_state);
        datePicker();
        startDate = findViewById(R.id.startDatePickerButton);
        endDate = findViewById(R.id.endDatePickerButton);
        startDate.setText(getTodayDate());
        endDate.setText(getNextDate());
        Button pay = (Button) findViewById(R.id.button2);
        EditText customerName = (EditText) findViewById(R.id.PersonName);
        EditText Email = (EditText) findViewById(R.id.EmailAddress);
        EditText phone = (EditText) findViewById(R.id.Phone);
        addRoom = findViewById(R.id.addFloater);
        subtractRoom = findViewById(R.id.subtractFloater);
        TextView room = (TextView) findViewById(R.id.room);

        room.setText(String.valueOf(roomCounter));
        addRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(roomCounter<5){
                    roomCounter++;
                }
                room.setText(String.valueOf(roomCounter));
            }
        });
        subtractRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(roomCounter!=1){
                    roomCounter--;
                }

                room.setText(String.valueOf(roomCounter));
            }
        });

        Button start = (Button) findViewById(R.id.startDatePickerButton);
        Button end = (Button) findViewById(R.id.endDatePickerButton);
        Hotel hotel = (Hotel) getIntent().getExtras().get("hotel");


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customerName.getText().toString() == null || customerName.getText().toString().equals("")
                        || Email.getText().toString()== null  || Email.getText().toString().equals("")
                        || phone.getText().toString()== null  || phone.getText().toString().equals("")
                        || room.getText().toString()== null  || room.getText().toString().equals("")
                ) {
                    Toast.makeText(getApplicationContext(), "Please fill the booking details", Toast.LENGTH_LONG).show();

                } else if(new Date((String) startDate.getText()).after(new Date((String) endDate.getText()))) {
                    Toast.makeText(getApplicationContext(), "Start date should be less than end date", Toast.LENGTH_LONG).show();
                } else if(Integer.parseInt(room.getText().toString()) > Integer.parseInt(hotel.getBedrooms())) {
                    Toast.makeText(getApplicationContext(), "Hotel Does not have that many rooms.", Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(getApplicationContext(), PaymentMode.class);
                    Booking booking = new Booking();
                    booking.setUserName(customerName.getText().toString());
                    booking.setContactEmail(Email.getText().toString());
                    booking.setStatus(Status.BOOKED);
                    booking.setRooms(Integer.parseInt(room.getText().toString()));
                    booking.setFrom(new Date(startDate.getText().toString()));
                    booking.setTo(new Date(endDate.getText().toString()));
                    booking.setAssignedTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    booking.setHotelId(hotel.getListing_id());
                    booking.setHotelName(hotel.getName());
                    booking.setPictureUrl(hotel.getPicture_url());
                    intent.putExtra("hotel", hotel);
                    intent.putExtra("booking", booking);
                    startActivity(intent);
                }
            }
        });
    }


    private String getTodayDate() {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return getDateString(day,month,year);
    }

    private String getNextDate() {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.add(java.util.Calendar.DATE, 1);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return getDateString(day,month,year);
    }

    public void datePicker() {

        DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {
          @Override
          public void onDateSet(DatePicker datePicker, int year, int month, int day) {
              month = month + 1;
              String date = getDateString(day, month, year);
              startDate.setText(date);
          }
      };
        int style = AlertDialog.THEME_HOLO_LIGHT;
        java.util.Calendar cal = java.util.Calendar.getInstance();
        int day = cal.get(java.util.Calendar.DATE);
        int month = cal.get(java.util.Calendar.MONTH);
        int year = cal.get(java.util.Calendar.YEAR);
        startDatePickerDialog = new DatePickerDialog(this,style,startDateSetListener,year,month, day);

        DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = getDateString(dayOfMonth, month, year);
                endDate.setText(date);
            }
        };
        cal.add(java.util.Calendar.DATE, 1);
        day = cal.get(java.util.Calendar.DATE);
        month = cal.get(java.util.Calendar.MONTH);
        year = cal.get(java.util.Calendar.YEAR);
        endDatePickerDialog = new DatePickerDialog(this,style,endDateSetListener,year,month, day);
    }


    private String getDateString(int day, int month, int year) {
       return getMonthFormat(month) + " " + day + " " + year;
    }


    private String getMonthFormat(int month) {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";
        // default
        return "JAN";
    }

    public void openPicker(View view) {
        startDatePickerDialog.show();
    }
    public void openEndDatePicker(View view) {
        endDatePickerDialog.show();
    }

}