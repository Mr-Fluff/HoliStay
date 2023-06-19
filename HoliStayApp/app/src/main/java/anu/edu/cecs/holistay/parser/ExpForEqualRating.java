package anu.edu.cecs.holistay.parser;

import java.util.List;
import anu.edu.cecs.holistay.datastructures.Tree;
import anu.edu.cecs.holistay.hoteldetails.Hotel;

/**
 * Equal rating expression
 *
 * @Author: Aishwarya Sonavane (u7173560)
 */
public class ExpForEqualRating extends Expression {

    private final int rating;

    public ExpForEqualRating(int rating) {
        this.rating = rating;
    }

    @Override
    public List<Hotel> evaluate(Tree tree) {
        List<Hotel> equalRating;
        equalRating = tree.equalRatingHotels(tree, rating);
        return equalRating;
    }
}
