package anu.edu.cecs.holistay.notification;

import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationCompat;

import anu.edu.cecs.holistay.hoteldetails.Hotel;
import anu.edu.cecs.holistay.R;

/**
 * Creating push notifications
 * @author Nakul Nambiar (u7433687)
 */
public class PushNotification implements Notification {

    private Hotel hotel;
    private Context context;
    private NotificationManager notificationManager;

    public PushNotification(Hotel hotel, Context context, NotificationManager notificationManager) {
        this.hotel = hotel;
        this.context = context;
        this.notificationManager = notificationManager;
    }

    @Override
    public void notifyUser() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1")
                .setContentTitle("Hurray! You've made a Booking!!")
                .setSmallIcon(R.drawable.app_logo)
                .setContentText("Your booking at " + hotel.getName() + "is confirmed.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationManager.notify(1, builder.build());
    }
}
