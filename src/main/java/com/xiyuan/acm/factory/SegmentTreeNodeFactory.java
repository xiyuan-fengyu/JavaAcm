package com.xiyuan.acm.factory;

import com.xiyuan.acm.model.SegmentTreeNode;

import java.util.Comparator;

/**
 * Created by xiyuan_fengyu on 2016/10/21.
 */
public class SegmentTreeNodeFactory {

    public static SegmentTreeNode build(int[] arr) {
        return build(arr, "min");
    }

    public static SegmentTreeNode build(int[] arr, String treeType) {
        Comparator<Integer> comparator = null;
        if (treeType.toLowerCase().equals("max")) {
            comparator = new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2 - o1;
                }
            };
        }
        else {
            comparator = new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1 - o2;
                }
            };
        }
        return build(arr, comparator);
    }

    public static SegmentTreeNode build(int[] arr, Comparator<Integer> comparator) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        return build(arr, 0, arr.length - 1, comparator);
    }

    private static SegmentTreeNode build(int[] arr, int left, int right, Comparator<Integer> comparator) {
        if (left == right) {
            return new SegmentTreeNode(left, right, arr[left]);
        }
        else {
            SegmentTreeNode root = new SegmentTreeNode(left, right, arr[left]);
            root.left = build(arr, left, (left + right) / 2, comparator);
            root.right = build(arr, (left + right) / 2 + 1, right, comparator);
            root.max = comparator.compare(root.left.max, root.right.max) < 0? root.left.max: root.right.max;
            return root;
        }
    }

    public static int query(SegmentTreeNode root, int start, int end, Comparator<Integer> comparator) {
        if (root == null) {
            return 0;
        }

        if (root.start == start && root.end == end) {
            return root.max;
        }
        else {
            int leftMax = 0;
            int rightMax = 0;

            if (start <= root.left.end) {
                leftMax = query(root.left, start, Math.min(root.left.end, end), comparator);
                if (end >= root.right.start) {
                    rightMax = query(root.right, Math.max(root.right.start, start), end, comparator);
                    return comparator.compare(leftMax, rightMax) < 0? leftMax: rightMax;
                }
                else {
                    return leftMax;
                }
            }
            else {
                if (end >= root.right.start) {
                    return query(root.right, Math.max(root.right.start, start), end, comparator);
                }
                else {
                    return 0;
                }
            }
        }
    }

    public static void modify(SegmentTreeNode root, int index, int value, Comparator<Integer> comparator) {
        if (root == null || root.start > index || root.end < index) {
        }
        else if (root.start == index && root.end == index) {
            root.max = value;
        }
        else {
            if (root.left.end >= index) {
                modify(root.left, index, value, comparator);
            }
            else {
                modify(root.right, index, value, comparator);
            }
            root.max = comparator.compare(root.left.max, root.right.max) < 0? root.left.max: root.right.max;
        }
    }

}
