package anu.edu.cecs.holistay.parser;

import java.util.List;
import anu.edu.cecs.holistay.datastructures.Tree;
import anu.edu.cecs.holistay.hoteldetails.Hotel;

/**
 * Lower price expression
 *
 * @Author: Aishwarya Sonavane (u7173560)
 */
public class ExpForLowerPrice extends Expression {

    private final int price;

    public ExpForLowerPrice(int price) {
        this.price = price;
    }

    @Override
    public List<Hotel> evaluate(Tree tree) {
        List<Hotel> lowerPrice;
        lowerPrice = tree.lowerPriceHotels(tree, price);
        return lowerPrice;
    }
}
