package anu.edu.cecs.holistay.datastructures;
import anu.edu.cecs.holistay.hoteldetails.Hotel;

/**
 * Abstract Empty Tree extending Tree
 *
 * @Author: Aishwarya Sonavane (u7173560)
 */
public abstract class EmptyTree extends Tree {
    public abstract Tree insert(Hotel element);
    @Override
    public Hotel find(String listingId) {
        return null;
    }

    @Override
    public int getHeight() {
        return -1;
    }
}