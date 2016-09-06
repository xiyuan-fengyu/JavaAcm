package com.xiyuan.acm.model;

/**
 * Created by xiyuan_fengyu on 2016/9/1.
 */
public class ExpressionTreeNode extends BasicTreeNode<String> {

    public ExpressionTreeNode left;

    public ExpressionTreeNode right;

    public ExpressionTreeNode(String val) {
        super(val);
    }

    @Override
    public BasicTreeNode<String> left() {
        return left;
    }

    @Override
    public BasicTreeNode<String> right() {
        return right;
    }
}