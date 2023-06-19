package anu.edu.cecs.holistay.ui;

import static anu.edu.cecs.holistay.ui.HotelListActivity.tree;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import anu.edu.cecs.holistay.Logger;
import anu.edu.cecs.holistay.hoteldetails.Hotel;
import anu.edu.cecs.holistay.parser.Expression;
import anu.edu.cecs.holistay.parser.Parser;
import anu.edu.cecs.holistay.R;
import anu.edu.cecs.holistay.tokenizer.MainTokenizer;
import anu.edu.cecs.holistay.ui.helper.ListingAdapter;

/**
 * List the hotels resultant from the search query:
 *
 * Functionalities:
 * 1) Sort the hotels in ascending or descending order, either by price or rating
 *
 * @Authors: Srinivasa Sai Damarla (u7370240), Aishwarya Sonavane (u7173560)
 */
public class SearchResultActivity extends AppCompatActivity {

    RecyclerView recyclerView ;
    ListingAdapter listingAdapter;
    List<Hotel> data;
    TextView searchText, sortPrice, sortRating;
    ImageView _priceAscending, _priceDescending, _ratingAscending, _ratingDescending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        // Set views to variables
        setViewIDs();

        _priceAscending.setVisibility(View.INVISIBLE);
        _priceDescending.setVisibility(View.INVISIBLE);
        _ratingAscending.setVisibility(View.INVISIBLE);
        _ratingDescending.setVisibility(View.INVISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        String searchQuery = intent.getStringExtra("searchQuery");
        //Default Search Query
        MainTokenizer mainTokenizer ;
        Expression expression;
        try {
            mainTokenizer = new MainTokenizer(searchQuery);
            expression = new Parser(mainTokenizer).parseExpression();
            data = expression.evaluate(tree);
        }catch (Exception e){
            if(data!=null)
                data.clear();
            mainTokenizer = new MainTokenizer("price>50");
            expression = new Parser(mainTokenizer).parseExpression();
            data = expression.evaluate(tree);
            Toast.makeText(getApplicationContext(),"Invalid search!!!!",Toast.LENGTH_LONG).show();
            Logger.getInstance().info(this.getClass(), "Error while searching!" + e );
        }
        listingAdapter = new ListingAdapter(data);
        recyclerView.setAdapter(listingAdapter);
        searchText.setText(String.format("Showing results for %s", searchQuery));
        // Set listeners
        setClickListeners();
    }

    public void setClickListeners(){
        // sortPrice listener: sort hotels w.r.t price
        sortPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _ratingAscending.setVisibility(View.INVISIBLE);
                _ratingDescending.setVisibility(View.INVISIBLE);
                if(_priceAscending.getVisibility()==View.INVISIBLE && _priceDescending.getVisibility()==View.INVISIBLE){
                    Collections.sort(data,new Comparator<Hotel>() {
                        @Override
                        public int compare(Hotel h1, Hotel h2) {
                            return Double.parseDouble(h1.getPrice())>=Double.parseDouble((h2.getPrice()))?1:-1;
                        }
                    });
                    _priceAscending.setVisibility(View.VISIBLE);
                }else if(_priceAscending.getVisibility()==View.VISIBLE && _priceDescending.getVisibility()==View.INVISIBLE){
                    Collections.sort(data,new Comparator<Hotel>() {
                        @Override
                        public int compare(Hotel h1, Hotel h2) {
                            return Double.parseDouble(h1.getPrice())<=Double.parseDouble((h2.getPrice()))?1:-1;
                        }
                    });
                    _priceAscending.setVisibility(View.INVISIBLE);
                    _priceDescending.setVisibility(View.VISIBLE);
                }else if(_priceAscending.getVisibility()==View.INVISIBLE && _priceDescending.getVisibility()==View.VISIBLE){
                    Collections.sort(data,new Comparator<Hotel>() {
                        @Override
                        public int compare(Hotel h1, Hotel h2) {
                            return Double.parseDouble(h1.getPrice())>=Double.parseDouble((h2.getPrice()))?1:-1;
                        }
                    });
                    _priceAscending.setVisibility(View.VISIBLE);
                    _priceDescending.setVisibility(View.INVISIBLE);
                }
                listingAdapter = new ListingAdapter(data);
                recyclerView.setAdapter(listingAdapter);
            }
        });

        // sortRating listener: sort hotels w.r.t rating
        sortRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _priceAscending.setVisibility(View.INVISIBLE);
                _priceDescending.setVisibility(View.INVISIBLE);
                if(_ratingAscending.getVisibility()==View.INVISIBLE && _ratingDescending.getVisibility()==View.INVISIBLE) {
                    Collections.sort(data, new Comparator<Hotel>() {
                        @Override
                        public int compare(Hotel h1, Hotel h2) {
                            return Integer.parseInt(h1.getAvgReview())>=Integer.parseInt((h2.getAvgReview()))?1:-1;
                        }
                    });
                    _ratingAscending.setVisibility(View.VISIBLE);
                    listingAdapter = new ListingAdapter(data);
                    recyclerView.setAdapter(listingAdapter);
                }else if(_ratingAscending.getVisibility()==View.VISIBLE && _ratingDescending.getVisibility()==View.INVISIBLE){
                    Collections.sort(data,new Comparator<Hotel>() {
                        @Override
                        public int compare(Hotel h1, Hotel h2) {
                            return Integer.parseInt(h1.getAvgReview())<=Integer.parseInt((h2.getAvgReview()))?1:-1;
                        }
                    });
                    _ratingAscending.setVisibility(View.INVISIBLE);
                    _ratingDescending.setVisibility(View.VISIBLE);
                    listingAdapter = new ListingAdapter(data);
                    recyclerView.setAdapter(listingAdapter);
                }else if(_ratingAscending.getVisibility()==View.INVISIBLE && _ratingDescending.getVisibility()==View.VISIBLE){
                    Collections.sort(data,new Comparator<Hotel>() {
                        @Override
                        public int compare(Hotel h1, Hotel h2) {
                            return Integer.parseInt(h1.getAvgReview())>=Integer.parseInt((h2.getAvgReview()))?1:-1;
                        }
                    });
                    _ratingAscending.setVisibility(View.VISIBLE);
                    _ratingDescending.setVisibility(View.INVISIBLE);
                    listingAdapter = new ListingAdapter(data);
                    recyclerView.setAdapter(listingAdapter);
                }
            }
        });
    }

    public void setViewIDs(){
        recyclerView = findViewById(R.id.recyclerView);
        sortPrice = findViewById(R.id.sortPrice);
        sortRating = findViewById(R.id.sortRating);
        searchText = findViewById(R.id.searchResult);
        _priceAscending = findViewById(R.id.priceAscending);
        _priceDescending = findViewById(R.id.priceDescending);
        _ratingAscending = findViewById(R.id.ratingAscending);
        _ratingDescending = findViewById(R.id.ratingDescending);
    }
}