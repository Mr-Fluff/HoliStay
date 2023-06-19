package anu.edu.cecs.holistay.parser;
import java.util.List;
import anu.edu.cecs.holistay.datastructures.Tree;
import anu.edu.cecs.holistay.hoteldetails.Hotel;

/**
 * Abstract class Expression
 *
 * @Author: Aishwarya Sonavane (u7173560)
 */
public abstract class Expression {
    public abstract List<Hotel> evaluate(Tree tree);
}
