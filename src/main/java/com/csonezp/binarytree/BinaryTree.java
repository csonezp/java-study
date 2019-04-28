// Copyright (C) 2019 Meituan
// All rights reserved
package com.csonezp.binarytree;


import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

/**
 * @author zhangpeng34
 * Created on 2019/4/28 下午9:47
 **/
public class BinaryTree {
    TreeNode root;

    public BinaryTree(int[] array) {
        root = makeBinaryTreeByArray(array, 1);
    }

    public static TreeNode makeBinaryTreeByArray(int[] array, int index) {
        if (index < array.length) {
            int value = array[index];
            if (value != 0) {
                TreeNode t = new TreeNode(value);
                array[index] = 0;
                t.left = makeBinaryTreeByArray(array, index * 2);
                t.right = makeBinaryTreeByArray(array, index * 2 + 1);
                return t;
            }
        }
        return null;
    }

    /**
     * 深度遍历，用栈实现
     */
    public void depthOrderTraversal() {
        if (root == null) {
            System.out.println("empty tree");
            return;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            TreeNode node = stack.pop();
            System.out.println(node.value);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }

    }

    /**
     * 广度遍历，用队列实现
     */
    public void levelOrderTraversal() {
        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.remove();
            System.out.println(node.value);
            if(node.left!=null){
                queue.add(node.left);
            }
            if(node.right!=null){
                queue.add(node.right);
            }
        }
    }


    /**
     * 相邻层输出顺序相反的广度遍历，用两个stack实现
     */
    public void fantasyTraversal(){
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        stack1.push(root);
        while (!stack1.empty()||!stack2.empty()){
            while (!stack1.empty()){
                TreeNode node = stack1.pop();
                System.out.println(node.value);
                if(node.right!=null){
                    stack2.push(node.right);
                }
                if(node.left!=null){
                    stack2.push(node.left);
                }

            }

            while (!stack2.empty()){
                TreeNode node = stack2.pop();
                System.out.println(node.value);
                if(node.left!=null){
                    stack1.push(node.left);
                }
                if(node.right!=null){
                    stack1.push(node.right);
                }
            }
        }
    }
    static class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }
    }


    public static void main(String[] args) {
        int[] arr = {0, 1, 2, 3, 4, 5, 6, 7,8,9,10,11,12,13,14,15};
        BinaryTree tree = new BinaryTree(arr);
        tree.fantasyTraversal();
    }
}