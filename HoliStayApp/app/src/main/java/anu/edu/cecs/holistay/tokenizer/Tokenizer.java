package anu.edu.cecs.holistay.tokenizer;

/**
 * Abstract class Tokenizer
 *
 * @Author: Aishwarya Sonavane (u7173560)
 */
public abstract class Tokenizer {

    public abstract boolean hasNext();
    public abstract void next();
    public abstract Token current();

}
