package com.meizu.datastructure.binarytree;
/**
 * 二叉树
 * @author haiyang1
 *
 */
public class BinaryTree {
    
    BinaryTreeNode root;
    
    public BinaryTree(BinaryTreeNode node) {
        this.root = node;
    }
    
    public void checkEmpty() {
        if (root == null) {
            throw new IllegalStateException("null tree");
        }
    }
    
    public void insertAsLeftChild(BinaryTreeNode left) {
        checkEmpty();
        root.setLeft(left);
    }
    
    public void insertAsRightChild(BinaryTreeNode right) {
        checkEmpty();
        root.setRight(right);
    }
    
    public void deleteNode(BinaryTreeNode node) {
        checkEmpty();
        if (node == null) {
            return;
        }
        deleteNode(node.getLeft());
        deleteNode(node.getRight());
        node = null;
    }
    
    public void clear() {
        if (root != null) {
            deleteNode(root);
        }
    }
    
    public int getNodeHeight(BinaryTreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftHeight = getNodeHeight(node.getLeft());
        int rightHeight = getNodeHeight(node.getRight());
        return Math.max(leftHeight, rightHeight) + 1;
    }
    
    public int getTreeHeight() {
        return getNodeHeight(root);
    }
    
    
    
    public int getNodeSize(BinaryTreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftSize = getNodeSize(node.getLeft());
        int rightSize = getNodeSize(node.getRight());
        return leftSize + rightSize + 1;
        
    }
    public int getTreeSize() {
        return getNodeSize(root);
    }
    
    public BinaryTreeNode getParentNode(BinaryTreeNode tree, BinaryTreeNode node) {
        if (tree == null || node == null) {
            return null;
        }
        if (tree.getLeft() == node || tree.getRight() == node) {
            return tree;
        }
        
        BinaryTreeNode parent; 
        if ((parent = getParentNode(tree.getLeft(), node)) != null) {
            return parent;
        } else {
            return getParentNode(tree.getRight(), node);
        }
    }
    
    public BinaryTreeNode getParent(BinaryTreeNode node) {
        return getParentNode(root, node);
    }
    
    
    
    public void iterateFirstOrder(BinaryTreeNode node) {
        if (node == null) {
            return;
        }
        System.out.println(node.getData());
        iterateFirstOrder(node.getLeft());
        iterateFirstOrder(node.getRight());
    }
    
    public void iterateMediumOrder(BinaryTreeNode node) {
        if (node == null) {
            return;
        }
        iterateMediumOrder(node.getLeft());
        System.out.println(node.getData());
        iterateMediumOrder(node.getRight());
    }
    
    
    public void iterateLastOrder(BinaryTreeNode node) {
        if (node == null) {
            return;
        }
        iterateLastOrder(node.getLeft());
        iterateLastOrder(node.getRight());
        System.out.println(node.getData());
    }
    

    public BinaryTreeNode getRoot() {
        return root;
    }

    public void setRoot(BinaryTreeNode root) {
        this.root = root;
    }
    
    
    public BinaryTreeNode assembleTestLeftData() {
        BinaryTreeNode rootLeft = new BinaryTreeNode();
        rootLeft.setData(2);
        
        BinaryTreeNode child3 = new BinaryTreeNode(3);
        BinaryTreeNode child4 = new BinaryTreeNode(4);
        rootLeft.setLeft(child3);
        rootLeft.setRight(child4);
        
        BinaryTreeNode child5 = new BinaryTreeNode(5);
        BinaryTreeNode child6 = new BinaryTreeNode(6);
        child3.setLeft(child5);
        child3.setRight(child6);
        
        return rootLeft;
    }
    
    public BinaryTreeNode assembleTestRightData() {
        BinaryTreeNode rootRight = new BinaryTreeNode();
        rootRight.setData(9);
        
        BinaryTreeNode child13 = new BinaryTreeNode(13);
        BinaryTreeNode child14 = new BinaryTreeNode(14);
        rootRight.setLeft(child13);
        rootRight.setRight(child14);
        
        BinaryTreeNode child15 = new BinaryTreeNode(15);
        BinaryTreeNode child16 = new BinaryTreeNode(16);
        child13.setLeft(child15);
        child13.setRight(child16);
        
        return rootRight;
    }
    
    public static void main(String[] args) {
        BinaryTreeNode root = new BinaryTreeNode(1);
        BinaryTree tree = new BinaryTree(root);
        
        tree.insertAsLeftChild(tree.assembleTestLeftData());
        tree.insertAsRightChild(tree.assembleTestRightData());
        
        
        //tree.iterateFirstOrder(root);
        //tree.iterateMediumOrder(root);
        tree.iterateLastOrder(root);
    }
    
}
