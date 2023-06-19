package anu.edu.cecs.holistay;
import org.junit.Test;
import static org.junit.Assert.*;

import anu.edu.cecs.holistay.tokenizer.MainTokenizer;
import anu.edu.cecs.holistay.tokenizer.Token;

/**
 * Tokenizer Test
 *
 * @Author: Aishwarya Sonavane (u7173560)
 */
public class TokenizerTest {

    String text1 = "price>90";
    String text2 = "price>90";
    String text3 = "rating<95";
    String text4 = "rating=90";
    String text5 = "name=cozy";
    String text6 = "location=boston";

    @Test(timeout = 1000)
    public void testTokenizerEquals() {
        // test for equal tokens
        MainTokenizer tokenizer1 = new MainTokenizer(text1);
        MainTokenizer tokenizer2 = new MainTokenizer(text2);
        while (tokenizer1.hasNext() && tokenizer2.hasNext()) {
            assertEquals(tokenizer1.current().getToken(), tokenizer2.current().getToken());
            tokenizer1.next();
            tokenizer2.next();
        }
    }

    @Test(timeout = 1000)
    public void testTokenizerNotEquals() {
        // test for unequal tokens
        MainTokenizer tokenizer1 = new MainTokenizer(text2);
        MainTokenizer tokenizer2 = new MainTokenizer(text3);
        assertNotEquals(true, Token.containsRating(tokenizer1.current().getToken()));
        assertNotEquals(tokenizer1.current().getToken(), tokenizer2.current().getToken());
    }

    @Test(timeout = 1000)
    public void testTokenizerCurrentNotEquals() {
        MainTokenizer tokenizer1 = new MainTokenizer(text3);
        MainTokenizer tokenizer2 = new MainTokenizer(text4);
        tokenizer1.next();
        assertNotEquals(tokenizer1.current().getToken(), tokenizer2.current().getToken());
        assertNotEquals(tokenizer1.current().getType(),tokenizer2.current().getType());
    }

    @Test(timeout = 1000)
    public void testTokenizerName() {
        MainTokenizer tokenizer1 = new MainTokenizer(text5);
        tokenizer1.next();
        tokenizer1.next();
        assertEquals("cozy",tokenizer1.current().getToken());
        assertEquals(Token.Type.NAME,tokenizer1.current().getType());
    }
    @Test(timeout = 1000)
    public void testTokenizerLocation() {
        MainTokenizer tokenizer1 = new MainTokenizer(text6);
        tokenizer1.next();
        tokenizer1.next();
        assertEquals("boston",tokenizer1.current().getToken());
        assertEquals(Token.Type.NAME,tokenizer1.current().getType());
    }
}

