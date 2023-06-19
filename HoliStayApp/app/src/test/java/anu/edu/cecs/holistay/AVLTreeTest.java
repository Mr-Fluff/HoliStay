package anu.edu.cecs.holistay;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

import anu.edu.cecs.holistay.datastructures.AVLTree;
import anu.edu.cecs.holistay.hoteldetails.Hotel;

/**
 * AVL Tree Test
 *
 * @Author: Aishwarya Sonavane (u7173560)
 */
public class AVLTreeTest {

    @Test(timeout = 1000)
    public void testIfImmutable() {
        Hotel hotelCard = new Hotel("1", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false);
        AVLTree tree = new AVLTree(hotelCard);
        hotelCard = new Hotel("2", "Cozy hut", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30",  "", "", "", "","",false);
        tree.insert(hotelCard);
        assertNull(tree.find("2"));
    }

    @Test(timeout = 1000)
    public void insertBasicTest() {
        // basic tree insertion test
        Hotel hotelCard = new Hotel("7", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30","", "", "", "","",false);
        AVLTree tree = new AVLTree(hotelCard);
        hotelCard = new Hotel("10", "Cozy hut", "dark","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false);
        tree = tree.insert(hotelCard);
        assertEquals("10", tree.rightNode.value.getListing_id());
        hotelCard = new Hotel("1", "Sunny apartment", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false);
        tree = tree.insert(hotelCard);
        assertNotNull(tree.leftNode.value);
        assertEquals("1", tree.leftNode.value.getListing_id());
        Hotel hotel = tree.find("1");
        assertEquals("1", hotel.getListing_id());
        hotel = tree.find("10");
        assertEquals("10", hotel.getListing_id());
        ArrayList<Hotel> list = tree.treeToArraylistInorder(tree);
        assertEquals("1",list.get(0).getListing_id());
    }

    @Test(timeout = 1000)
    public void duplicateInsertTest() {
        // check if tree inserts duplicate hotels
        Hotel hotelCard = new Hotel("7", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false);
        AVLTree tree = new AVLTree(hotelCard);
        hotelCard = new Hotel("7", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false);
        tree = tree.insert(hotelCard);
        assertEquals(0, tree.getHeight());
        assertNull(tree.leftNode.value);
        assertNull(tree.rightNode.value);
    }

    @Test(timeout = 1000)
    public void leftRotateTest() {
        Hotel hotelCard = new Hotel("4", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false);
        AVLTree tree = new AVLTree(hotelCard);
        hotelCard = new Hotel("7", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false);
        tree = tree.insert(hotelCard);
        hotelCard = new Hotel("12", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false);
        tree = tree.insert(hotelCard);
        // root value after left rotate
        assertNotNull(tree.value);
        assertEquals("7", tree.value.getListing_id());
        // left node value after left rotate
        assertNotNull(tree.leftNode.value);
        assertEquals("4", tree.leftNode.value.getListing_id());
        // right node value after left rotate
        assertNotNull(tree.rightNode.value);
        assertEquals("12", tree.rightNode.value.getListing_id());
    }
    @Test(timeout = 1000)
    public void rightRotateTest() {
        Hotel hotelCard = new Hotel("12", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false);
        AVLTree tree = new AVLTree(hotelCard);
        hotelCard = new Hotel("7", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false);
        tree = tree.insert(hotelCard);
        hotelCard = new Hotel("4", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false);
        tree = tree.insert(hotelCard);
        // root value after left rotate
        assertNotNull(tree.value);
        assertEquals("7", tree.value.getListing_id());
        // left node value after left rotate
        assertNotNull(tree.leftNode.value);
        assertEquals("4", tree.leftNode.value.getListing_id());
        // right node value after left rotate
        assertNotNull(tree.rightNode.value);
        assertEquals("12", tree.rightNode.value.getListing_id());
    }

    @Test(timeout = 1000)
    public void heightTest() {
        Hotel hotelCard = new Hotel("7", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false);
        AVLTree tree = new AVLTree(hotelCard).insert(new Hotel("11", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false)).insert(new Hotel("20", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false));
        assertEquals(0, tree.getBalanceFactor());
        tree = tree.insert(new Hotel("42", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false)).insert(new Hotel("41", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false));
        assertEquals(-1, tree.getBalanceFactor());
        tree = tree.insert(new Hotel("43", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false));
        assertEquals(0, tree.getBalanceFactor());
    }

    @Test(timeout = 1000)
    public void otherRotationTests() {
        AVLTree tree = new AVLTree(new Hotel("23", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false))
                .insert(new Hotel("26", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false))
                .insert(new Hotel("20", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false))
                .insert(new Hotel("16", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false))
                .insert(new Hotel("62", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false))
                .insert(new Hotel("13", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false))
                .insert(new Hotel("22", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false))
                .insert(new Hotel("21", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false))
                .insert(new Hotel("17", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false))
                .insert(new Hotel("69", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false))
                .insert(new Hotel("28", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false))
                .insert(new Hotel("25", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false))
                .insert(new Hotel("29", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false));

        assertNotNull(tree.value);
        assertNotNull(tree.leftNode.value);
        assertEquals(-1, tree.getBalanceFactor());
        assertEquals("23", tree.value.getListing_id());
        assertEquals("20", tree.leftNode.value.getListing_id());
        assertEquals("26", tree.rightNode.value.getListing_id());

    }

    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testExceptionsFind() throws IllegalArgumentException {
        Hotel hotelCard = new Hotel("12", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false);
        AVLTree tree = new AVLTree(hotelCard);
        tree.find(null);
    }
    @Test(timeout = 1000, expected = IllegalArgumentException.class)
    public void testExceptionsInsertion() throws IllegalArgumentException {
        Hotel hotelCard = new Hotel("12", "Cozy home", "sunny","$250", "95", "42.3411334", "-71.08001119", "2", "2", "2", "3", "", "30", "", "", "", "","",false);
        AVLTree tree = new AVLTree(hotelCard);
        tree = tree.insert(null);
    }

}
