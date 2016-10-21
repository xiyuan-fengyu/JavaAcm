package com.xiyuan.acm.model;

/**
 * Created by xiyuan_fengyu on 2016/10/21.
 */
public class IntervalTreeNode<T> extends BasicTreeNode<Interval> {

    public T data;

    public IntervalTreeNode<T> left;

    public IntervalTreeNode<T> right;

    public IntervalTreeNode(int start, int end, T data) {
        super(null);
        val = new Interval(start, end);
        this.data = data;
    }

    public IntervalTreeNode(Interval val, T data) {
        super(val);
        this.data = data;
    }

    @Override
    public BasicTreeNode<Interval> left() {
        return left;
    }

    @Override
    public BasicTreeNode<Interval> right() {
        return right;
    }

    public interface Actions <T> {
        void build(IntervalTreeNode<T> root);
        T query(T leftData, T rightData);
    }

    public static <T> IntervalTreeNode<T> build(T[] arr, Actions<T> actions) {
        return build(arr, 0, arr.length - 1, actions);
    }

    private static <T> IntervalTreeNode<T> build(T[] arr, int left, int right, Actions<T> actions) {
        if (left == right) {
            return new IntervalTreeNode<>(left, right, arr[left]);
        }
        else {
            IntervalTreeNode<T> root = new IntervalTreeNode<>(left, right, arr[left]);
            root.left = build(arr, left, (left + right) / 2, actions);
            root.right = build(arr, (left + right) / 2 + 1, right, actions);
            actions.build(root);
            return root;
        }
    }

    public static <T> T query(IntervalTreeNode<T> root, int start, int end, Actions<T> actions) {
        if (root == null) {
            return null;
        }
        if (root.val.start == start && root.val.end == end) {
            return root.data;
        }
        else {
            T leftData = null;
            if (root.left.val.start <= start && root.left.val.end >= start) {
                leftData = query(root.left, start, Math.min(root.left.val.end, end), actions);
            }

            T rightData = null;
            if (root.right.val.end >= end && root.right.val.start <= end) {
                rightData = query(root.right, Math.max(root.right.val.start, start), end, actions);
            }

            return actions.query(leftData, rightData);
        }
    }

}
