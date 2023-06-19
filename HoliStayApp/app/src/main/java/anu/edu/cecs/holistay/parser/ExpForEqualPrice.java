package anu.edu.cecs.holistay.parser;

import java.util.List;
import anu.edu.cecs.holistay.datastructures.Tree;
import anu.edu.cecs.holistay.hoteldetails.Hotel;

/**
 * Equal price expression
 *
 * @Author: Aishwarya Sonavane (u7173560)
 */
public class ExpForEqualPrice extends Expression {

    private final int price;

    public ExpForEqualPrice(int price) {
        this.price = price;
    }

    @Override
    public List<Hotel> evaluate(Tree tree) {
        List<Hotel> equalPrice;
        equalPrice = tree.equalPriceHotels(tree, price);
        return equalPrice;
    }
}
