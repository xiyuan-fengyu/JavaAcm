package com.xiyuan.acm.factory;

import com.xiyuan.acm.model.TreeNode;

/**
 * Created by xiyuan_fengyu on 2016/9/1.
 */
public class TreeNodeFactory {

    public static TreeNode build(Integer[] arr) {
        java.util.Queue<TreeNode> nodeQueue = new java.util.LinkedList<TreeNode>();
        return build(arr, 0, nodeQueue);
    }

    public static TreeNode build(String str) {
        java.util.Queue<TreeNode> nodeQueue = new java.util.LinkedList<TreeNode>();
        String[] split = str.split(",");
        Integer[] arr = new Integer[split.length];
        for (int i = 0, len = split.length; i < len; i++) {
            try {
                arr[i] = Integer.parseInt(split[i].trim());
            }
            catch (Exception e) {
                arr[i] = null;
            }
        }
        return build(arr, 0, nodeQueue);
    }

    private static TreeNode build(Integer[] arr, int index, java.util.Queue<TreeNode> nodeQueue) {
        if (index >= arr.length) {
            return null;
        }

        if (nodeQueue.isEmpty()) {
            Integer first = arr[0];
            if (first == null) {
                return null;
            }
            else {
                TreeNode root = new TreeNode(first);
                nodeQueue.offer(root);
                build(arr, index + 1, nodeQueue);
                return root;
            }
        }
        else {
            TreeNode curParent = nodeQueue.poll();
            if (arr[index] != null) {
                curParent.left = new TreeNode(arr[index]);
                nodeQueue.offer(curParent.left);
            }
            if (index + 1 < arr.length && arr[index + 1] != null) {
                curParent.right = new TreeNode(arr[index + 1]);
                nodeQueue.offer(curParent.right);
            }
            build(arr, index + 2, nodeQueue);
        }
        return null;
    }

}
