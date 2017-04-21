package com.xiyuan.acm2.model;

import com.xiyuan.acm.model.TreeNode;

import java.util.Map;
import java.util.Stack;

/**
 * Created by xiyuan_fengyu on 2017/4/21.
 */
public class BSTIterator {

    private Stack<TreeNode> stack = new Stack<>();

    private TreeNode next;

    public BSTIterator(TreeNode root) {
        next = root;
    }

    private void addToNode(TreeNode node) {
        while (node != null) {
            stack.add(node);
            node = node.left;
        }
    }

    public boolean hasNext() {
        if (next != null) {
            addToNode(next);
            next = null;
        }
        return !stack.isEmpty();
    }

    public TreeNode next() {
        if (hasNext()) {
            TreeNode result = stack.pop();
            next = result.right;
            return result;
        }
        else return null;
    }

}
