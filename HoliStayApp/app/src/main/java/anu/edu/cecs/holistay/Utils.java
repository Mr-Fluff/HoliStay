package anu.edu.cecs.holistay;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anu.edu.cecs.holistay.hoteldetails.Hotel;


/**
 * Utility class with utility functions
 * @author Nakul Nambiar (u7433687)
 */
public class Utils {

    public static List<Hotel> getHotels(String fileName, Context context) {
        List<Hotel> hotels = new ArrayList<>();
        try {
            hotels = new CsvToBeanBuilder(new InputStreamReader(context.getAssets().open(fileName), StandardCharsets.UTF_8)).withType(Hotel.class).build().parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hotels;
    }

    public static void addBookmark(String hotelId, FirebaseFirestore firebaseFirestore, String userId) {
        firebaseFirestore.collection("bookmarks").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    Map<String, Object> map = new HashMap<>();
                    if(documentSnapshot.exists()) {
                        List<String> hotels = (List<String>)documentSnapshot.get("hotels");
                        if(!hotels.contains(hotelId)) hotels.add(hotelId);
                        map.put("hotels", hotels);
                        firebaseFirestore.collection("bookmarks").document(userId).set(map);
                    } else {
                        List<String> hotels = new ArrayList<>();
                        hotels.add(hotelId);
                        map.put("hotels", hotels);
                        firebaseFirestore.collection("bookmarks").document(userId).set(map);
                    }
                }
            }
        });
    }

    public static void removeBookmark(String hotelId, FirebaseFirestore firebaseFirestore, String userId) {
        firebaseFirestore.collection("bookmarks").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    Map<String, Object> map = new HashMap<>();
                    if(documentSnapshot.exists()) {
                        List<String> hotels = (List<String>)documentSnapshot.get("hotels");
                        hotels.remove(hotelId);
                        map.put("hotels", hotels);
                        firebaseFirestore.collection("bookmarks").document(userId).set(map);
                    } else {
                        Logger.getInstance().info(Utils.class, "No Bookmarks Found!");
                    }
                }
            }
        });
    }

}
