package anu.edu.cecs.holistay.parser;

import java.util.List;
import anu.edu.cecs.holistay.datastructures.Tree;
import anu.edu.cecs.holistay.hoteldetails.Hotel;

/**
 * Lower rating expression
 *
 * @Author: Aishwarya Sonavane (u7173560)
 */
public class ExpForLowerRating extends Expression {

    private final int rating;

    public ExpForLowerRating(int rating) {
        this.rating = rating;
    }

    @Override
    public List<Hotel> evaluate(Tree tree) {
        List<Hotel> lowerRating;
        lowerRating = tree.lowerRatingHotels(tree, rating);
        return lowerRating;
    }
}
