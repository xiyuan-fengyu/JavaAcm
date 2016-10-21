package com.xiyuan.acm.factory;

import com.xiyuan.acm.model.SegmentTreeNode;

/**
 * Created by xiyuan_fengyu on 2016/10/21.
 */
public class SegmentTreeNodeFactory {

    public static SegmentTreeNode build(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        return build(arr, 0, arr.length - 1);
    }

    private static SegmentTreeNode build(int[] arr, int left, int right) {
        if (left == right) {
            return new SegmentTreeNode(left, right, arr[left]);
        }
        else {
            SegmentTreeNode root = new SegmentTreeNode(left, right, arr[left]);
            root.left = build(arr, left, (left + right) / 2);
            root.right = build(arr, (left + right) / 2 + 1, right);
            root.max = Math.max(root.left.max, root.right.max);
            return root;
        }
    }
}
