package com.meizu.datastructure.binarytree;
/**
 * 二叉排序树
 * 二叉排序树，又称二叉查找树、二叉搜索树。
 * 
 * 二叉排序树是具有下列性质的二叉树：

    若左子树不空，则左子树上所有结点的值均小于它的根结点的值；
    若右子树不空，则右子树上所有结点的值均大于或等于它的根结点的值；
    左、右子树也分别为二叉排序树。


  也就是说，二叉排序树中，左子树都比节点小，右子树都比节点大，递归定义。
 * @author haiyang1
 *
 */
public class BinarySearchTree {
    
    private BinaryTreeNode root;
    
    public BinarySearchTree(BinaryTreeNode root) {
        this.root = root;
    }
    
    public BinarySearchTree() {
        
    }
    
    private BinaryTreeNode search(BinaryTreeNode node, int data) {
        if (node == null || node.getData() == data) {
            return node;
        }
        if (data < node.getData()) {
            return search(node.getLeft(), data);
        } else {
            return search(node.getRight(), data);
        }
    }
    
    public BinaryTreeNode search(int x) {
        return search(root, x);
    }
    
    private BinaryTreeNode searchAndInsert(BinaryTreeNode parent, BinaryTreeNode node, int data) {
        if (node == null) {//当前比较节点为 空，说明之前没有这个数据，直接新建、插入
            node = new BinaryTreeNode();
            node.setData(data);
            if (parent != null) {
                if (data < parent.getData()) {
                    parent.setLeft(node);
                } else {
                    parent.setRight(node);
                }
            }
            return node;
        }
        if (node.getData() == data) {
            return node;
        } else if (data < node.getData()) {
            return searchAndInsert(node, node.getLeft(), data);
        } else {
            return searchAndInsert(node, node.getRight(), data);
        }
    }
    
    public void insert(int data) {
        if (root == null) {
            root = new BinaryTreeNode();
            root.setData(data);
            return;
        }
        searchAndInsert(null, root, data);
    }
    
    public static void main(String[] args) {
//        BinarySearchTree binarySearchTree = new BinarySearchTree();
//        binarySearchTree.insert(8);
//        binarySearchTree.insert(3);
//        binarySearchTree.insert(1);
        System.out.println('\u0003');
    }
}
