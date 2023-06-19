package anu.edu.cecs.holistay.parser;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import anu.edu.cecs.holistay.datastructures.Tree;
import anu.edu.cecs.holistay.hoteldetails.Hotel;

/**
 * Combined Expressions
 *
 * @Author: Aishwarya Sonavane (u7173560)
 */
public class ExpressionCombine extends Expression {
    private final Expression term;
    private final Expression expression;

    public ExpressionCombine(Expression term, Expression expression) {
        this.term = term;
        this.expression = expression;
    }

    @Override
    public List<Hotel> evaluate(Tree tree) {
        List<Hotel> termList = term.evaluate(tree);
        List<Hotel> expList = expression.evaluate(tree);
        Set<Hotel> commonSet = new HashSet<>();
        for(Hotel hotel : termList){
            for(Hotel hotelExp : expList){
                if(hotel.getListing_id().equals(hotelExp.getListing_id()))
                    commonSet.add(hotel);
            }
        }
        List<Hotel> commonList = new ArrayList<>(commonSet);
        return commonList;
    }
}
