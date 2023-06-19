package anu.edu.cecs.holistay.notification;

import android.os.StrictMode;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import anu.edu.cecs.holistay.hoteldetails.Hotel;

/**
 * Sending email notifications
 * @author Nakul Nambiar (u7433687)
 */
public class EmailNotification implements Notification {

    /**
     * Sending email address
     */
    private static final String FROM_EMAIL = "holistayapp@gmail.com";

    /**
     * App password generated in the gmail account
     */
    private static final String FROM_EMAIL_PASSWORD = "lxakluorzycyketh";
    private static final String EMAIL_SUBJECT = "HoliStay Booking Confirmed!";
    private static final String HOST = "smtp.gmail.com";

    private String toEmail;
    private Hotel hotel;

    public EmailNotification(String toEmail, Hotel hotel) {
        this.toEmail = toEmail;
        this.hotel = hotel;
    }

    @Override
    public void notifyUser() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", HOST);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, FROM_EMAIL_PASSWORD);
            }
        });
        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(FROM_EMAIL));
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            mimeMessage.setSubject(EMAIL_SUBJECT);
            mimeMessage.setText("Your booking for "+ hotel.getName() + " is confirmed!");
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
