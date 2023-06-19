package anu.edu.cecs.holistay.tokenizer;

import java.util.Arrays;

/**
 * Our Tokenizer
 *
 * @Author: Aishwarya Sonavane (u7173560)
 */
public class MainTokenizer extends Tokenizer {

    private String mainBuffer;
    private Token currentToken;

    public MainTokenizer(String text) {
        mainBuffer = text;
        next();
    }

    public Token current() {
        return currentToken;
    }

    public boolean hasNext() {
        return currentToken != null;
    }

    public void next() {
        mainBuffer = mainBuffer.trim();
        if (mainBuffer.isEmpty()) {
            currentToken = null;
            return;
        }
        char currentChar = mainBuffer.charAt(0);
        if (currentChar == '$') {
            currentToken = new Token("$", Token.Type.DOLLAR);
        }
        if (currentChar == '=' || currentChar == '<' || currentChar == '>') {
            currentToken = new Token(Character.toString(currentChar), Token.Type.COMPARISON);
        }
        if (Character.isDigit(currentChar)) {
            String currentKeyword = Character.toString(currentChar);
            for (int i = 1; i < mainBuffer.length() && Character.isDigit(mainBuffer.charAt(i)); i++) {
                currentKeyword += mainBuffer.charAt(i);
            }
            currentToken = new Token(currentKeyword, Token.Type.INT);
        }
        if (Character.isLetter(currentChar)) {
            int i=0;
            for(i=0 ; i < mainBuffer.length() && Character.isLetter(mainBuffer.charAt(i)) ; i++){
            }
            String obtainedKeyword = mainBuffer.substring(0, i).toLowerCase();
            if (Token.containsPrice(obtainedKeyword)) {
                obtainedKeyword = "price";
            }
            else if(Token.containsRating(obtainedKeyword)){
                obtainedKeyword = "rating";
            }
            else if(Token.containsName(obtainedKeyword)){
                obtainedKeyword = "name";
            }
            else if(Token.containsLocation(obtainedKeyword)){
                obtainedKeyword = "location";
            }
            if (Arrays.asList(Token.keyword).contains(obtainedKeyword.toLowerCase())) {
                currentToken = new Token(obtainedKeyword, Token.Type.KEYWORD);
            }
            else{
                for(i= obtainedKeyword.length() ; i < mainBuffer.length() && !Character.toString(mainBuffer.charAt(i)).equals("$") ; i++)
                    obtainedKeyword+=mainBuffer.charAt(i);
                currentToken = new Token(obtainedKeyword, Token.Type.NAME);

            }
        }
        mainBuffer = mainBuffer.substring(currentToken.getToken().length());
    }
}
