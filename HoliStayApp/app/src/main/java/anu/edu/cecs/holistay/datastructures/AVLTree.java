package anu.edu.cecs.holistay.datastructures;

import anu.edu.cecs.holistay.hoteldetails.Hotel;

/**
 * AVL Tree
 *
 * @Author: Aishwarya Sonavane (u7173560)
 */
public class AVLTree extends Tree{

    public AVLTree(Hotel value) {
        super(value);
        this.leftNode = new EmptyAVL(); // store less than value
        this.rightNode = new EmptyAVL(); // store greater than value
    }

    public AVLTree(Hotel value, Tree leftNode, Tree rightNode) {
        super(value, leftNode, rightNode);
    }

    public int getBalanceFactor() {
        return leftNode.getHeight() - rightNode.getHeight();
    }

    // Insertion of Hotel element in AVL tree according to the Listing Id of Hotel
    @Override
    public AVLTree insert(Hotel element) {
        AVLTree newTree ;
        if (element == null)
            throw new IllegalArgumentException("Input cannot be null");

        if (value!=null && Integer.parseInt(element.getListing_id())>Integer.parseInt(value.getListing_id())) {
            newTree = new AVLTree(value, leftNode, rightNode.insert(element));
        } else if (value!=null && Integer.parseInt(element.getListing_id())<Integer.parseInt(value.getListing_id())) {
            newTree = new AVLTree(value, leftNode.insert(element), rightNode);
        } else {
            newTree = new AVLTree(value, leftNode, rightNode);
        }
        if (newTree.getBalanceFactor() < -1) {
            if(newTree.rightNode.value!=null && Integer.parseInt(element.getListing_id())>Integer.parseInt(value.getListing_id())){
                newTree=newTree.leftRotate();
            } else{
                newTree.rightNode=((AVLTree)newTree.rightNode).rightRotate();
                newTree=newTree.leftRotate();
            }
        }
        if (newTree.getBalanceFactor() > 1){
             if(newTree.leftNode.value!=null &&Integer.parseInt(element.getListing_id())<Integer.parseInt(value.getListing_id()))
                newTree= newTree.rightRotate();
                else {
                newTree.leftNode=((AVLTree)newTree.leftNode).leftRotate();
                newTree=newTree.rightRotate();
                }
        }
        return newTree;
    }

    // leftRotate according to balance Factor
    public AVLTree leftRotate() {
        Tree newParent = this.rightNode;
        this.rightNode = newParent.leftNode;
        newParent.leftNode = this;
        return (AVLTree) newParent;
    }

    // rightRotate according to balance Factor
    public AVLTree rightRotate() {
        Tree newParent = this.leftNode;
        this.leftNode = newParent.rightNode;
        newParent.rightNode = this;
        return (AVLTree) newParent;
    }

    // Find a particular listingId in the tree and return Hotel Object
    @Override
    public Hotel find(String listingId) {
        if (listingId == null)
            throw new IllegalArgumentException("Input cant be null");
        if (value!=null && Integer.parseInt(listingId)==Integer.parseInt(value.getListing_id())) {
            return this.value;
        } else if (value!=null && Integer.parseInt(listingId)<Integer.parseInt(value.getListing_id())){
            return leftNode.find(listingId);
        } else {
            return rightNode.find(listingId);
        }
    }

    public static class EmptyAVL extends EmptyTree {
        @Override
        public Tree insert(Hotel element) {
            return new AVLTree(element);
        }
    }
}
