package anu.edu.cecs.holistay.ui.helper;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import anu.edu.cecs.holistay.R;
import anu.edu.cecs.holistay.Utils;
import anu.edu.cecs.holistay.hoteldetails.booking.Booking;
import anu.edu.cecs.holistay.hoteldetails.booking.BookingServiceImpl;
import anu.edu.cecs.holistay.hoteldetails.booking.Status;

/**
 * Custom RecyclerView Adapter to display booking cards
 *
 * @Authors: Srinivasa Sai Damarla (u7370240)
 */
public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
    //    private LayoutInflater layoutInflater;
    private List<Booking> data;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public BookingAdapter(List<Booking> data){
//        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public BookingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookingcardlayout,parent,false);
        return new BookingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        BookingAdapter.ViewHolder viewHolder = holder;

        Booking hotelCard_data = data.get(position);

        Date to_Date= data.get(position).getTo();
        Date from_Date = data.get(position).getFrom();
        String final_date = to_Date.toLocaleString()+" to "+from_Date.toLocaleString();
        holder.room.setText(String.valueOf(data.get(position).getRooms()));
        holder.title.setText(data.get(position).getHotelName());
        holder.price.setText(String.format("$%s", data.get(position).getTotalPrice()));
        Picasso.get()
                .load(data.get(position).getPictureUrl())
                .into(holder.image);
        holder.date.setText(final_date);

        if(data.get(position).getStatus()==Status.CANCELLED){
            holder.cancelButton.setVisibility(View.INVISIBLE);
        }else{
            viewHolder.cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.get(position).setStatus(Status.CANCELLED);
                    BookingServiceImpl bookingService = new BookingServiceImpl();
                    bookingService.cancel(data.get(position), firebaseFirestore);
                    holder.cancelButton.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,date,price,room;
        ImageView image;
        Button cancelButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewName);
            image = itemView.findViewById(R.id.hotelImage);
            room = itemView.findViewById(R.id.textViewRoom);
            price = itemView.findViewById(R.id.textViewPrice);
            date = itemView.findViewById(R.id.textViewDates);
            cancelButton = itemView.findViewById(R.id.buttonCancel);
        }
    }
}
