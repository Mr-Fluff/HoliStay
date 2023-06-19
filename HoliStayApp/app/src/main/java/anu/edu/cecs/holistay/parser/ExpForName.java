package anu.edu.cecs.holistay.parser;

import java.util.List;

import anu.edu.cecs.holistay.datastructures.Tree;
import anu.edu.cecs.holistay.hoteldetails.Hotel;

/**
 * Name expression
 *
 * @Author: Aishwarya Sonavane (u7173560)
 */
public class ExpForName extends Expression{

    private final String name;

    public ExpForName(String name) {
        this.name = name;
    }

    @Override
    public List<Hotel> evaluate(Tree tree) {
        List<Hotel> findName;
        findName = tree.findHotelName(tree, name);
        return findName;
    }
}
