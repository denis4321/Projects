/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.denis.binarytree;

/**
 * This class represents tree node, which is subtree itself - as it is set for full binary trees.
 * e.g. each node is possibly a tree unless it's a leaf.
 * Therefore, once created each node is a root for its own subtree. nodes can be inserted into other nodes by pairs,
 * shifting according subtrees left or right.
 * @author Denis Prokopiuk
 *  */
public class FullBinaryTree {
    private FullBinaryTree left = null;
    private FullBinaryTree right = null;
    private FullBinaryTree parent = null;
    private long version = 0;

    public FullBinaryTree(){
    }

    public FullBinaryTree getLeft(){
        return left;
    }

    public FullBinaryTree getRight(){
        return right;
    }

    /**
     * returns true if current node is leaf
     * @return
     */
    public boolean isLeaf(){
        return left==null&&right==null;
    }

    /**
     * returns true if current node is a parent
     * @return
     */
    private boolean isRoot(){
        return parent==null;
    }

    /**
     * Inserts two nodes into current tree or subtree
     * possibly shifts current nodes down left
     * @param left
     * @param right
     */
    public void insertNodesIntoLeft(FullBinaryTree left, FullBinaryTree right){
        insertNodes(left, right, true);
    }

    /**
     * Inserts two nodes into current tree or subtree
     * possibly shifts current nodes down right
     * @param left
     * @param right
     */
    public void insertNodesIntoRight(FullBinaryTree left, FullBinaryTree right){
        insertNodes(left, right, false);
    }

    /**
     * service method for inserting nodes into tree
     * variable isLeft defines whether nbodes shoud be inserted into left (true)
     * or right(false) branch;
     * @param left
     * @param right
     * @param isLeft
     */
    private void insertNodes(FullBinaryTree left, FullBinaryTree right, boolean isLeft){
        if (!left.isRoot()) throw new IllegalArgumentException(left+" already has a parent");
        if (!right.isRoot()) throw new IllegalArgumentException(right+" already has a parent");
        version++;
        if (!this.isLeaf()){
            FullBinaryTree tmp = isLeft ? left.getDeepestLeft() : right.getDeepestRight();
            this.left.parent = null;
            this.right.parent = null;
            if (isLeft){
                tmp.insertNodesIntoLeft(this.left, this.right);
            } else {
                tmp.insertNodesIntoRight(this.left, this.right);
            }
        }
        this.left = left;
        this.left.parent = this;
        this.right = right;
        this.right.parent = this;
    }

    /**
     * Returns deepest left element
     * @return
     */
    private FullBinaryTree getDeepestLeft(){
        FullBinaryTree reply = this;
        while (!reply.isLeaf()){
            reply = reply.left;
        }
        return reply;
    }

    /**
     * Returns deepest right element
     * @return
     */
    private FullBinaryTree getDeepestRight(){
        FullBinaryTree reply = this;
        while (!reply.isLeaf()){
            reply = reply.right;
        }
        return reply;
    }

    public int getTreeHeight(){
        return getTreeHeight(0);
    }

    /**
     * Returns tree height on zero basis (e.g. tree with only root node hes height of 0)
     * @param currH
     * @return
     */
    private int getTreeHeight(int currH){
        if (isLeaf()){
            return currH;
        } else {
            int leftH = this.left.getTreeHeight(currH+1);
            int rightH = this.right.getTreeHeight(currH+1);
            return Math.max(leftH, rightH);
        }
    }

    public static boolean compareTrees(FullBinaryTree t1, FullBinaryTree t2){
        if (t1.isLeaf()&&t2.isLeaf()){
            return true;
        } else
        if (!t1.isLeaf()&&!t2.isLeaf()){
            return compareTrees(t1.getLeft(), t2.getLeft())&&compareTrees(t1.getRight(), t2.getRight());
        } else return false;
    }

}
