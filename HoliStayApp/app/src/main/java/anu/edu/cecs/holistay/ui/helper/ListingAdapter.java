package anu.edu.cecs.holistay.ui.helper;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

import anu.edu.cecs.holistay.hoteldetails.Hotel;
import anu.edu.cecs.holistay.R;
import anu.edu.cecs.holistay.ui.SelectedHotelActivity;
import anu.edu.cecs.holistay.Utils;

/**
 * Custom RecyclerView Adapter to display hotel cards
 *
 * @Authors: Srinivasa Sai Damarla (u7370240), Aishwarya Sonavane (u7173560)
 */
public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ViewHolder> {
//    private LayoutInflater layoutInflater;
    private List<Hotel> data;

    public ListingAdapter(List<Hotel> data){
//        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotelcardlayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewHolder viewHolder = holder;
        Hotel hotelCard_data = data.get(position);

        holder.title.setText(data.get(position).getName());
        holder.desc.setText(data.get(position).getDescription());
        holder.price.setText(String.format("$%s", data.get(position).getPrice()));
        holder.avgReview.setText(data.get(position).getAvgReview());
        Picasso.get()
                .load(data.get(position).getPicture_url())
                .into(holder.image);
        if(hotelCard_data.isBookmarked()) {
            holder.bookmark.setImageResource(R.drawable.ic_bookmark_added);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SelectedHotelActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("key", hotelCard_data);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });

        viewHolder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hotelCard_data.isBookmarked()) {
                    Utils.removeBookmark(hotelCard_data.getListing_id(), FirebaseFirestore.getInstance(), FirebaseAuth.getInstance().getCurrentUser().getUid());
                    hotelCard_data.setBookmarked(false);
                    viewHolder.bookmark.setImageResource(R.drawable.ic_bookmark);
                } else {
                    Utils.addBookmark(hotelCard_data.getListing_id(), FirebaseFirestore.getInstance(), FirebaseAuth.getInstance().getCurrentUser().getUid());
                    hotelCard_data.setBookmarked(true);
                    viewHolder.bookmark.setImageResource(R.drawable.ic_bookmark_added);
                }

            }
        });
}

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,desc,price,avgReview;
        ImageView image;
        ImageView bookmark;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewName);
            desc = itemView.findViewById(R.id.textViewDesc);
            image = itemView.findViewById(R.id.holiStayLogo);
            price = itemView.findViewById(R.id.textViewPrice);
            avgReview = itemView.findViewById(R.id.textViewReview);
            bookmark = itemView.findViewById(R.id.addBookmark);
        }
    }
}
