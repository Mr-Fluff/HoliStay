package anu.edu.cecs.holistay.parser;

import java.util.List;
import anu.edu.cecs.holistay.datastructures.Tree;
import anu.edu.cecs.holistay.hoteldetails.Hotel;

/**
 * Higher price expression
 *
 * @Author: Aishwarya Sonavane (u7173560)
 */
public class ExpForHigherPrice extends Expression {

    private final int price;

    public ExpForHigherPrice(int price) {
        this.price = price;
    }

    @Override
    public List<Hotel> evaluate(Tree tree) {
        List<Hotel> higherPrice;
        higherPrice = tree.higherPriceHotels(tree, price);
        return higherPrice;
    }
}
