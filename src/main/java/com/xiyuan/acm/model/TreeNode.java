package com.xiyuan.acm.model;

/**
 * Created by xiyuan_fengyu on 2016/9/1.
 */
public class TreeNode extends BasicTreeNode<Integer> {

    public TreeNode left;

    public TreeNode right;

    public TreeNode(Integer val) {
        super(val);
    }

    @Override
    public BasicTreeNode<Integer> left() {
        return left;
    }

    @Override
    public BasicTreeNode<Integer> right() {
        return right;
    }
}