package anu.edu.cecs.holistay.parser;

import java.util.List;

import anu.edu.cecs.holistay.datastructures.Tree;
import anu.edu.cecs.holistay.hoteldetails.Hotel;

/**
 * Location expression
 *
 * @Author: Aishwarya Sonavane (u7173560)
 */
public class ExpForLocation extends Expression{
    private final String location;

    public ExpForLocation(String location) {
        this.location = location;
    }

    @Override
    public List<Hotel> evaluate(Tree tree) {
        List<Hotel> findLocation;
        findLocation = tree.findHotelLocation(tree, location);
        return findLocation;
    }
}
