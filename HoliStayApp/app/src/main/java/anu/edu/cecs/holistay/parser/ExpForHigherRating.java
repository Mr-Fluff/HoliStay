package anu.edu.cecs.holistay.parser;

import java.util.List;
import anu.edu.cecs.holistay.datastructures.Tree;
import anu.edu.cecs.holistay.hoteldetails.Hotel;

/**
 * Higher rating expression
 *
 * @Author: Aishwarya Sonavane (u7173560)
 */
public class ExpForHigherRating extends Expression {

    private final int rating;

    public ExpForHigherRating(int rating) {
        this.rating = rating;
    }

    @Override
    public List<Hotel> evaluate(Tree tree) {
        List<Hotel> higherRating;
        higherRating = tree.higherRatingHotels(tree, rating);
        return higherRating;
    }
}
