package anu.edu.cecs.holistay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

import java.util.List;

import anu.edu.cecs.holistay.datastructures.AVLTree;
import anu.edu.cecs.holistay.hoteldetails.Hotel;
import anu.edu.cecs.holistay.parser.Expression;
import anu.edu.cecs.holistay.parser.Parser;
import anu.edu.cecs.holistay.tokenizer.MainTokenizer;
/**
 * Parser Test
 *
 * @Author: Aishwarya Sonavane (u7173560)
 */
public class ParserTest {
    List<Hotel> data;
    String text1 = "price>90";
    String text2 = "price<90";
    String text3 = "price=90";
    String text4 = "rating>90";
    String text5 = "rating<90";
    String text6 = "rating=90";
    String text7 = "name=pretty";
    String text8 = "location=roslindale";
    String text9 = "name=home$price=70";
    String text10 = "next=home";
    String text11 = "name=21";
    String text12 = "location=43";
    String text13 = "price>abc";
    String text14 = "price<abc";
    String text15 = "price=abc";
    String text16 = "rating>abc";
    String text17 = "rating<abc";
    String text18 = "rating=abc";
    AVLTree tree = new AVLTree(new Hotel("1", "Cozy home", "sunny","25", "50", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","roslindale",false))
            .insert(new Hotel("2", "Sunny Bungalow", "sunny","50", "35", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","Jamaica Plain",false))
            .insert(new Hotel("3", "Perfect home", "sunny","70", "39", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","South End",false))
            .insert(new Hotel("4", "Ideal home", "sunny","80", "60", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","roslindale",false))
            .insert(new Hotel("5", "beautiful apartment", "sunny","90", "70", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","South Bay",false))
            .insert(new Hotel("6", "South end studio", "sunny","100", "85", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","South End",false))
            .insert(new Hotel("7", "Victorian Condo unit", "sunny","120", "90", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","roslindale",false))
            .insert(new Hotel("8", "Bright 2BR", "sunny","190", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","Jamaica Plain",false))
            .insert(new Hotel("9", "pretty 2BR", "sunny","250", "91", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","South Bay",false))
            .insert(new Hotel("10", "beautiful deluxe", "sunny","290", "92", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","South End",false))
            .insert(new Hotel("11", "charming home", "sunny","20", "93", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","Jamaica Plain",false))
            .insert(new Hotel("12", "convenient home", "sunny","200", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","South End",false))
            .insert(new Hotel("13", "romantic home", "sunny","1200", "97", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","Jamaica Plain",false));

    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void NullCheck() {
        AVLTree tree = new AVLTree(null);
        MainTokenizer tokenizer1 = new MainTokenizer(text1);
        Expression expression = new Parser(tokenizer1).parseExpression();
        data = expression.evaluate(tree);
        data.clear();
    }
    @Test(timeout = 1000)
    public void testHigherPrice() {
        MainTokenizer tokenizer1 = new MainTokenizer(text1);
        Expression expression = new Parser(tokenizer1).parseExpression();
        data = expression.evaluate(tree);
        assertNotNull(data);
        assertEquals("6",data.get(0).getListing_id());
        data.clear();
    }

    @Test(timeout = 1000)
    public void testLowerPrice() {
        MainTokenizer tokenizer1 = new MainTokenizer(text2);
        Expression expression = new Parser(tokenizer1).parseExpression();
        data = expression.evaluate(tree);
        assertNotNull(data);
        assertEquals("1",data.get(0).getListing_id());
        data.clear();
    }

    @Test(timeout = 1000)
    public void testEqualPrice() {
        MainTokenizer tokenizer1 = new MainTokenizer(text3);
        Expression expression = new Parser(tokenizer1).parseExpression();
        data = expression.evaluate(tree);
        assertNotNull(data);
        assertEquals("5",data.get(0).getListing_id());
        data.clear();
    }
    @Test(timeout = 1000)
    public void testHigherRating() {
        MainTokenizer tokenizer1 = new MainTokenizer(text4);
        Expression expression = new Parser(tokenizer1).parseExpression();
        data = expression.evaluate(tree);
        assertNotNull(data);
        assertEquals("8",data.get(0).getListing_id());
        data.clear();
    }

    @Test(timeout = 1000)
    public void testLowerRating() {
        MainTokenizer tokenizer1 = new MainTokenizer(text5);
        Expression expression = new Parser(tokenizer1).parseExpression();
        data = expression.evaluate(tree);
        assertNotNull(data);
        assertEquals("1",data.get(0).getListing_id());
        data.clear();
    }

    @Test(timeout = 1000)
    public void testEqualRating() {
        MainTokenizer tokenizer1 = new MainTokenizer(text6);
        Expression expression = new Parser(tokenizer1).parseExpression();
        data = expression.evaluate(tree);
        assertNotNull(data);
        assertEquals("7",data.get(0).getListing_id());
        data.clear();
    }
    @Test(timeout = 1000)
    public void testName() {
        MainTokenizer tokenizer1 = new MainTokenizer(text7);
        Expression expression = new Parser(tokenizer1).parseExpression();
        data = expression.evaluate(tree);
        assertNotNull(data);
        assertEquals("9",data.get(0).getListing_id());
        assertEquals("pretty 2BR",data.get(0).getName());
        data.clear();
    }
    @Test(timeout = 1000)
    public void testLocation() {
        MainTokenizer tokenizer1 = new MainTokenizer(text8);
        Expression expression = new Parser(tokenizer1).parseExpression();
        data = expression.evaluate(tree);
        assertNotNull(data);
        assertEquals("1",data.get(0).getListing_id());
        data.clear();
    }

    @Test(timeout = 1000000)
    public void testCombine() {
        MainTokenizer tokenizer1 = new MainTokenizer(text9);
        Expression expression = new Parser(tokenizer1).parseExpression();
        data = expression.evaluate(tree);
        assertNotNull(data);
        assertEquals("3",data.get(0).getListing_id());
        data.clear();
    }

    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testExceptionKeyword() throws NullPointerException {
        MainTokenizer tokenizer1 = new MainTokenizer(text10);
        Expression expression = new Parser(tokenizer1).parseExpression();
        data = expression.evaluate(tree);
        data.clear();
    }
    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testExceptionName() throws IllegalArgumentException {
        MainTokenizer tokenizer1 = new MainTokenizer(text11);
        Expression expression = new Parser(tokenizer1).parseExpression();
        data = expression.evaluate(tree);
        data.clear();
    }
    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testExceptionLocation() throws IllegalArgumentException {
        MainTokenizer tokenizer1 = new MainTokenizer(text12);
        Expression expression = new Parser(tokenizer1).parseExpression();
        data = expression.evaluate(tree);
        data.clear();
    }
    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testExceptionPriceHigher() throws IllegalArgumentException {
        MainTokenizer tokenizer1 = new MainTokenizer(text13);
        Expression expression = new Parser(tokenizer1).parseExpression();
        data = expression.evaluate(tree);
        data.clear();
    }
    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testExceptionPriceLower() throws IllegalArgumentException {
        MainTokenizer tokenizer1 = new MainTokenizer(text14);
        Expression expression = new Parser(tokenizer1).parseExpression();
        data = expression.evaluate(tree);
        data.clear();
    }

    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testExceptionPriceEqual() throws IllegalArgumentException {
        MainTokenizer tokenizer1 = new MainTokenizer(text15);
        Expression expression = new Parser(tokenizer1).parseExpression();
        data = expression.evaluate(tree);
        data.clear();
    }
    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testExceptionRatingHigher() throws IllegalArgumentException {
        MainTokenizer tokenizer1 = new MainTokenizer(text16);
        Expression expression = new Parser(tokenizer1).parseExpression();
        data = expression.evaluate(tree);
        data.clear();
    }
    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testExceptionRatingLower() throws IllegalArgumentException {
        MainTokenizer tokenizer1 = new MainTokenizer(text17);
        Expression expression = new Parser(tokenizer1).parseExpression();
        data = expression.evaluate(tree);
        data.clear();
    }
    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testExceptionRatingEqual() throws IllegalArgumentException {
        MainTokenizer tokenizer1 = new MainTokenizer(text18);
        Expression expression = new Parser(tokenizer1).parseExpression();
        data = expression.evaluate(tree);
        data.clear();
    }
}
