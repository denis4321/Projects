/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.denis.binarytree;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Denis Prokopiuk
 */
public class FullBinaryTreeTest {

    public FullBinaryTreeTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * This test just checks that insertNodesInto works when appending leaf nodes to the end of the tree
     * It is nessesary to assure that testCompareTrees will work correctly, because it depends of it
     * this method also tests isLeaf, bacuse it's almost the same test
     */
    @Test
    public void testTreeApeend(){
        FullBinaryTree t1 = new FullBinaryTree();
        assertNull("leaf node has children when it shouldn't", t1.getLeft());
        assertNull("leaf node has children when it shouldn't", t1.getRight());
        assertTrue("Node is leaf, but isLeaf returns false", t1.isLeaf());
        FullBinaryTree left = new FullBinaryTree();
        FullBinaryTree right = new FullBinaryTree();
        t1.insertNodesIntoLeft(left, right);
        assertSame("left child node is not the same as it should be", left, t1.getLeft());
        assertSame("right child node is not the same as it should be", right, t1.getRight());
        assertFalse("Node is not leaf, but isLeaf returns true", t1.isLeaf());
        left = new FullBinaryTree();
        right = new FullBinaryTree();
        t1.getRight().insertNodesIntoRight(left, right);
        assertSame("left child node is not the same as it should be", left, t1.getRight().getLeft());
        assertSame("right child node is not the same as it should be", right, t1.getRight().getRight());
    }

    /**
     * In this test we use insertNodesInto for creating trees to compare.
     * In other tests, we use compareTrees to test insertNodes
     * To minimize the possibility that both methods are bugged, we create trees
     * in this test only by appending nodes to the end, not by inserting them in the tree.
     * Note that because method equals is recursive, there is no sence to create and test
     * for equity trees which height is bigger then 2, because compareTrees will process such situation as test of subtrees.
     * Strictly saying, there is no sence to test trees which height is bigger than 1 - we only need
     * to distiguish subtrees and leaves for compareTree method to work.
     */
    @Test
    public void testCompareTrees(){
        FullBinaryTree t1 = new FullBinaryTree();
        FullBinaryTree t2 = new FullBinaryTree();
        assertTrue("roots are not equal", FullBinaryTree.compareTrees(t1, t2));
        t1.insertNodesIntoLeft(new FullBinaryTree(), new FullBinaryTree());
        t2.insertNodesIntoLeft(new FullBinaryTree(), new FullBinaryTree());
        assertTrue("Trees are not equal, but they should be", FullBinaryTree.compareTrees(t1, t2));
        t1.getLeft().insertNodesIntoRight(new FullBinaryTree(), new FullBinaryTree());
        assertFalse("Trees are equal, but they shouldn't be", FullBinaryTree.compareTrees(t1, t2));
    }

    @Test
    public void testInsertNodes(){
        FullBinaryTree t1 = new FullBinaryTree();
        FullBinaryTree t2 = new FullBinaryTree();
        try {
            t1.insertNodesIntoLeft(null, new FullBinaryTree());
            fail("null node inserted to the left");
        } catch (NullPointerException ex){
            //ok
        }
        assertTrue("Node is not leaf", t1.isLeaf());
        try {
            t1.insertNodesIntoRight(new FullBinaryTree(), null);
            fail("null node inserted to the right");
        } catch (NullPointerException ex){
            //ok
        }
        assertTrue("Node is not leaf", t1.isLeaf());
        t1.insertNodesIntoLeft(new FullBinaryTree(), new FullBinaryTree());
        t2.insertNodesIntoLeft(new FullBinaryTree(), new FullBinaryTree());
        t1.getLeft().insertNodesIntoLeft(new FullBinaryTree(), new FullBinaryTree());
        t2.insertNodesIntoLeft(new FullBinaryTree(), new FullBinaryTree());
        assertTrue("Trees are not equal, but they should be", FullBinaryTree.compareTrees(t1, t2));
        t1.getRight().insertNodesIntoRight(new FullBinaryTree(), new FullBinaryTree());
        t1.getRight().getRight().insertNodesIntoRight(new FullBinaryTree(), new FullBinaryTree());
        t1.getRight().getRight().getRight().insertNodesIntoRight(new FullBinaryTree(), new FullBinaryTree());
        t2.getRight().insertNodesIntoRight(new FullBinaryTree(), new FullBinaryTree());
        FullBinaryTree subtree = new FullBinaryTree();
        subtree.insertNodesIntoRight(new FullBinaryTree(), new FullBinaryTree());
        t2.getRight().insertNodesIntoRight(new FullBinaryTree(), subtree);
        assertTrue("Trees are not equal, but they should be", FullBinaryTree.compareTrees(t1, t2));
        t2 = t2.getRight();
        try{
            t1.insertNodesIntoRight(new FullBinaryTree(), t2);
            fail("Exception should be thrown because t2 has parent");
        } catch (IllegalArgumentException ex){
            //ok
        }
    }

    @Test
    public void testTreeHeight(){
        FullBinaryTree t1 = new FullBinaryTree();
        assertEquals("Invalid height", 0, t1.getTreeHeight());
        t1.insertNodesIntoLeft(new FullBinaryTree(), new FullBinaryTree());
        t1.getLeft().insertNodesIntoLeft(new FullBinaryTree(), new FullBinaryTree());
        t1.getRight().insertNodesIntoRight(new FullBinaryTree(), new FullBinaryTree());
        t1.getRight().getRight().insertNodesIntoRight(new FullBinaryTree(), new FullBinaryTree());
        t1.getRight().getRight().getRight().insertNodesIntoRight(new FullBinaryTree(), new FullBinaryTree());
        assertEquals("Invalid height", 4, t1.getTreeHeight());
    }

}
