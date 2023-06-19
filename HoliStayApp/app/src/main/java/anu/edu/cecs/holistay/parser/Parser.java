package anu.edu.cecs.holistay.parser;
import anu.edu.cecs.holistay.tokenizer.MainTokenizer;
import anu.edu.cecs.holistay.tokenizer.Token;

/**
 * Parser grammar:
 * <expression> ::= <term> | <term> $ <expression>
 * <term> ::= <keyword> <comparison> <value>
 * <value> ::= <integer> | <string>
 * <comparison> ::= "<" | ">" | "="
 *
 * @Author: Aishwarya Sonavane (u7173560)
 */
public class Parser {
    MainTokenizer tokenizer;
    public Parser(MainTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    /**
     * Parse the queries with the form of
     * <expression> ::= <term> | <term> $ <expression>
     */
    public Expression parseExpression() {
        if (tokenizer.current().getType().equals(Token.Type.KEYWORD)) {
            Expression term = parseKeyword();
            if (tokenizer.hasNext()) {
                tokenizer.next();
                if (tokenizer.hasNext() && tokenizer.current().getType().equals(Token.Type.DOLLAR)) {
                    tokenizer.next();
                    Expression expression = parseExpression();
                    return new ExpressionCombine(term, expression);
                }
            }
            return term;
        }
        return null;
    }

    /**
     * Parse the queries with the form of
     * <keyword> ::= "rating" | "price" | "name" | "location"
     */
    public Expression parseKeyword() {
        if (tokenizer.hasNext()) {
            Expression term = null;
            if (tokenizer.current().getToken().equalsIgnoreCase("price")) {
                term = priceParse();
            }
            else if (tokenizer.current().getToken().equalsIgnoreCase("rating")) {
                term = ratingParse();
            }
            else if(tokenizer.current().getToken().equalsIgnoreCase("name")) {
                term = nameParse();
            }
            else if(tokenizer.current().getToken().equalsIgnoreCase("location")){
                term = locationParse();
            }
            return term;
        }
        return null;
    }

    private Expression locationParse() {
        if (tokenizer.hasNext()) {
            tokenizer.next();
            if (tokenizer.current().getType().equals(Token.Type.COMPARISON)) {
                if (tokenizer.current().getToken().equals("=")) {
                    tokenizer.next();
                    if (!tokenizer.current().getType().equals(Token.Type.NAME)) {
                        throw new IllegalArgumentException();
                    }
                    String name = tokenizer.current().getToken();
                    return new ExpForLocation(name);
                }
            }
        }
        else
            throw new IllegalArgumentException();
        return null;
    }

    private Expression nameParse() {
        if (tokenizer.hasNext()) {
            tokenizer.next();
            if (tokenizer.current().getType().equals(Token.Type.COMPARISON)) {
                if (tokenizer.current().getToken().equals("=")) {
                    tokenizer.next();
                    if (!tokenizer.current().getType().equals(Token.Type.NAME)) {
                        throw new IllegalArgumentException();
                    }
                    String name = tokenizer.current().getToken();
                    return new ExpForName(name);
                }
            }
        }
        else
            throw new IllegalArgumentException();
        return null;
    }

    /**
     * parse according to rating
     */
    private Expression ratingParse() {
        if (tokenizer.hasNext()) {
            tokenizer.next();
            if (tokenizer.current().getType().equals(Token.Type.COMPARISON)) {
                switch (tokenizer.current().getToken()) {
                    case "<": {
                        tokenizer.next();
                        if (!tokenizer.current().getType().equals(Token.Type.INT)) {
                            throw new IllegalArgumentException();
                        }
                        int rating = Integer.parseInt(tokenizer.current().getToken());
                        return new ExpForLowerRating(rating);
                    }
                    case ">": {
                        tokenizer.next();
                        if (!tokenizer.current().getType().equals(Token.Type.INT)) {
                            throw new IllegalArgumentException();
                        }
                        int rating = Integer.parseInt(tokenizer.current().getToken());
                        return new ExpForHigherRating(rating);
                    }
                    case "=": {
                        tokenizer.next();
                        if (!tokenizer.current().getType().equals(Token.Type.INT)) {
                            throw new IllegalArgumentException();
                        }
                        int rating = Integer.parseInt(tokenizer.current().getToken());
                        return new ExpForEqualRating(rating);
                    }
                }
            }
        }
        else
            throw new IllegalArgumentException();
        return null;
    }

    /**
     * parse according to price
     */
    public Expression priceParse() {
        if (tokenizer.hasNext()) {
            tokenizer.next();
            if (tokenizer.current().getType().equals(Token.Type.COMPARISON)) {
                switch (tokenizer.current().getToken()) {
                    case "<": {
                        tokenizer.next();
                        if (!tokenizer.current().getType().equals(Token.Type.INT)) {
                            throw new IllegalArgumentException();
                        }
                        int price = Integer.parseInt(tokenizer.current().getToken());
                        return new ExpForLowerPrice(price);
                    }
                    case ">": {
                        tokenizer.next();
                        if (!tokenizer.current().getType().equals(Token.Type.INT)) {
                            throw new IllegalArgumentException();
                        }
                        int price = Integer.parseInt(tokenizer.current().getToken());
                        return new ExpForHigherPrice(price);
                    }
                    case "=": {
                        tokenizer.next();
                        if (!tokenizer.current().getType().equals(Token.Type.INT)) {
                            throw new IllegalArgumentException();
                        }
                        int price = Integer.parseInt(tokenizer.current().getToken());
                        return new ExpForEqualPrice(price);
                    }
                }
            }
        }
        else
            throw new IllegalArgumentException();
        return null;
    }

}
