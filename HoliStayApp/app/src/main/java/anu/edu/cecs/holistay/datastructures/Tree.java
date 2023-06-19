package anu.edu.cecs.holistay.datastructures;
import java.util.ArrayList;
import java.util.List;

import anu.edu.cecs.holistay.hoteldetails.Hotel;

/**
 * Abstract Tree class
 *
 * Functionalities:
 * 1) Find Hotels =, >, < a price value
 * 2) Find Hotels =, >, < a rating value
 * 3) Find Hotels having a certain name
 * 4) Find Hotels having a certain location
 *
 * @Authors: Aishwarya Sonavane (u7173560), Srinivasa Sai Damarla (u7370240)
 */
public abstract class Tree {

    public final Hotel value;
    public Tree leftNode; // store less than value
    public Tree rightNode; // store greater than value

    public List<Hotel> equalPriceHotelList = new ArrayList<>();
    public List<Hotel> lowerPriceHotelList= new ArrayList<>();
    public List<Hotel> highPriceHotelList= new ArrayList<>();
    public List<Hotel> equalRatingHotelList= new ArrayList<>();
    public List<Hotel> lowerRatingHotelList= new ArrayList<>();
    public List<Hotel> highRatingHotelList= new ArrayList<>();
    public List<Hotel> nameHotelList= new ArrayList<>();
    public List<Hotel> locationHotelList= new ArrayList<>();

    public Tree() {
        this.value = null;
    }

    public Tree(Hotel value) {
        if (value == null)
            throw new IllegalArgumentException("Input cannot be null");
        this.value = value;
    }

    public Tree(Hotel value, Tree leftNode, Tree rightNode) {
        if (value == null || leftNode == null || rightNode == null)
            throw new IllegalArgumentException("Inputs cannot be null");
        this.value = value;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    public abstract Hotel find(String listingId);   // Find element according to listing id and return Hotel object
    public abstract Tree insert(Hotel element);   // Inserts the element

    public int getHeight() {
        int leftNodeHeight = leftNode instanceof EmptyTree ? 0 : 1 + leftNode.getHeight();
        int rightNodeHeight = rightNode instanceof EmptyTree ? 0 : 1 + rightNode.getHeight();
        return Math.max(leftNodeHeight, rightNodeHeight);
    }

    // return List of Hotels with Higher Price than the given price
    public List<Hotel> higherPriceHotels(Tree node, int price) {
        if (null == node) {
            return highPriceHotelList;
        } else if (node.leftNode != null) {
            higherPriceHotels(node.leftNode, price);
        }
        if (node.value!=null && node.value.getPrice()!=null && Double.parseDouble(node.value.getPrice()) != 0) {
            if (Double.parseDouble(node.value.getPrice()) > price) {
                highPriceHotelList.add(node.value);
            }
        }
        if (node.rightNode != null) {
            higherPriceHotels(node.rightNode, price);
        }
        return highPriceHotelList;
    }

    // return List of Hotels with Lower Price than the given price
    public List<Hotel> lowerPriceHotels(Tree node, int price) {
        if (null == node) {
            return lowerPriceHotelList ;
        } else if (node.leftNode != null) {
            lowerPriceHotels(node.leftNode, price);
        }
        if (node.value!=null && node.value.getPrice()!=null && Double.parseDouble(node.value.getPrice()) != 0) {
            if (Double.parseDouble(node.value.getPrice()) < price) {
                lowerPriceHotelList.add(node.value);
            }
        }
        if (node.rightNode != null) {
            lowerPriceHotels(node.rightNode, price);
        }
        return lowerPriceHotelList;
    }

    // return List of Hotels with price equal to the given price
    public List<Hotel> equalPriceHotels(Tree node, int price) {
        if (null == node) {
            return equalPriceHotelList;
        } else if (node.leftNode != null) {
            equalPriceHotels(node.leftNode, price);
        }
        if (node.value!=null && node.value.getPrice()!=null && Double.parseDouble(node.value.getPrice()) != 0) {
            if (Double.parseDouble(node.value.getPrice()) == price) {
                equalPriceHotelList.add(node.value);
            }
        }
        if (node.rightNode != null) {
            equalPriceHotels(node.rightNode, price);
        }
        return equalPriceHotelList;
    }

    // return List of Hotels with Higher Rating than the given rating
    public List<Hotel> higherRatingHotels(Tree node, int rating) {
        if (null == node) {
            return highRatingHotelList;
        } else if (node.leftNode != null) {
            higherRatingHotels(node.leftNode, rating );
        }
        if (node.value!=null && node.value.getAvgReview()!=null && Integer.parseInt(node.value.getAvgReview()) != 0) {
            if (Integer.parseInt(node.value.getAvgReview()) > rating) {
                highRatingHotelList.add(node.value);
            }
        }
        if (node.rightNode != null) {
            higherRatingHotels(node.rightNode, rating );
        }
        return highRatingHotelList;
    }

    // return List of Hotels with Lower Rating than the given rating
    public List<Hotel> lowerRatingHotels(Tree node, int rating) {
        if (null == node) {
            return lowerRatingHotelList;
        } else if (node.leftNode != null) {
            lowerRatingHotels(node.leftNode, rating);
        }
        if (node.value!=null && node.value.getAvgReview()!=null && Integer.parseInt(node.value.getAvgReview()) != 0) {
            if (Integer.parseInt(node.value.getAvgReview()) < rating) {
                lowerRatingHotelList.add(node.value);
            }
        }
        if (node.rightNode != null) {
            lowerRatingHotels(node.rightNode, rating);
        }

        return lowerRatingHotelList;
    }

    // return List of Hotels with rating equal to the given rating
    public List<Hotel> equalRatingHotels(Tree node, int rating) {
        if (null == node) {
            return equalRatingHotelList;
        } else if (node.leftNode != null) {
            equalRatingHotels(node.leftNode, rating );
        }
        if (node.value!=null && node.value.getAvgReview()!=null && Integer.parseInt(node.value.getAvgReview()) != 0) {
            if (Integer.parseInt(node.value.getAvgReview()) == rating) {
                equalRatingHotelList.add(node.value);
            }
        }
        if (node.rightNode != null) {
            equalRatingHotels(node.rightNode, rating);
        }
        return equalRatingHotelList;
    }

    // return List of Hotels having the given name
    public List<Hotel> findHotelName(Tree node, String name) {
        if (null == node) {
            return nameHotelList;
        } else if (node.leftNode != null) {
            findHotelName(node.leftNode, name );
        }
        if (node.value!=null && node.value.getName()!=null) {
            if (node.value.getName().toLowerCase().contains(name.toLowerCase())) {
                nameHotelList.add(node.value);
            }
        }
        if (node.rightNode != null) {
            findHotelName(node.rightNode, name);
        }
        return nameHotelList;
    }

    // return List of Hotels in the given location
    public List<Hotel> findHotelLocation(Tree node, String location) {
        if (null == node) {
            return locationHotelList;
        } else if (node.leftNode != null) {
            findHotelLocation(node.leftNode, location);
        }
        if (node.value!=null && node.value.getNeighbourhood()!=null && node.value.getNeighbourhood() != "") {
            if (node.value.getNeighbourhood().toLowerCase().contains(location.toLowerCase())) {
                locationHotelList.add(node.value);
            }
        }
        if (node.rightNode != null) {
            findHotelLocation(node.rightNode, location);
        }
        return locationHotelList;
    }

    // Returns inorder ArrayList of tree
    public ArrayList<Hotel> treeToArraylistInorder(Tree node) {
        ArrayList<Hotel> list = new ArrayList<>();
        if (node.leftNode != null) {
            if (node.leftNode.value != null) {
                list.addAll(treeToArraylistInorder(node.leftNode));
            }
        }
        if (node.value != null) {
            list.add(node.value);
        }
        if (node.rightNode != null) {
            if (node.rightNode.value != null) {
                list.addAll(treeToArraylistInorder(node.rightNode));
            }
        }
        return list;
    }
}
