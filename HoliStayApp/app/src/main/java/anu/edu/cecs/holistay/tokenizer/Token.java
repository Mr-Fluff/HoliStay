package anu.edu.cecs.holistay.tokenizer;

/**
 * Tokens types:
 * KEYWORD, COMPARISON , INT , DOLLAR , NAME
 *
 * @Author: Aishwarya Sonavane (u7173560)
 */
public class Token {
    public enum Type { KEYWORD, COMPARISON , INT , DOLLAR , NAME }
    static final String[] keyword = {"price","rating","name","location"};
    private final String token;
    private final Type type;

    public Token(String token, Type type) {
        this.token = token;
        this.type = type;
    }

    public static boolean containsPrice(String input) {
        if (keyword[0].equals(input)) {
            return true;
        }
        return false;
    }

    public static boolean containsRating(String input) {
        if (keyword[1].equals(input)) {
            return true;
        }
        return false;
    }

    public static boolean containsName(String input) {
        if (keyword[2].equals(input)) {
            return true;
        }
        return false;
    }

    public static boolean containsLocation(String input) {
        if (keyword[3].equals(input)) {
            return true;
        }
        return false;
    }

    public String getToken() {
        return token;
    }

    public Type getType() {
        return type;
    }

}
