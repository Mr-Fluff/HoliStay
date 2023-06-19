package anu.edu.cecs.holistay.notification;

import android.app.NotificationManager;
import android.content.Context;

import anu.edu.cecs.holistay.hoteldetails.Hotel;

/**
 * This is the factory class for Notifications and is used to create the two Notifications
 * @author Nakul Nambiar (u7433687)
 */
public class NotificationFactory {

    public Notification createNotification(Hotel hotel, String to, NotificationType type, Context context, NotificationManager notificationManager) {
        if(type == NotificationType.EMAIL) {
            return new EmailNotification(to, hotel);
        }
        if(type == NotificationType.PUSH) {
            return new PushNotification(hotel, context, notificationManager);
        }
        return null;
    }
}
