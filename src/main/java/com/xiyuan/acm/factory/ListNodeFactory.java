package com.xiyuan.acm.factory;

import com.xiyuan.acm.model.ListNode;

/**
 * Created by xiyuan_fengyu on 2016/9/1.
 */
public class ListNodeFactory {

    public static ListNode build(String str) {
        String[] split = str.split("->");
        Integer[] arr = new Integer[split.length];
        for (int i = 0, len = split.length; i < len; i++) {
            try {
                arr[i] = Integer.parseInt(split[i].trim());
            }
            catch (Exception e) {
                arr[i] = null;
            }
        }

        ListNode tempHead = new ListNode(0);
        ListNode cur = tempHead;
        for (Integer i: arr) {
            if (i == null) {
                break;
            }
            else {
                cur.next = new ListNode(i);
                cur = cur.next;
            }
        }
        return tempHead.next;
    }

    public static void tailConnectNodeAt(ListNode node, int index) {
        if (!hasCycle(node)) {
            ListNode nodeAtIndex = null;
            while (node != null) {
                if (index == 0) {
                    nodeAtIndex = node;
                }

                ListNode next = node.next;
                if (next == null) {
                    node.next = nodeAtIndex;
                }
                node = next;
                index--;
            }
        }
    }

    public static boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) return false;

        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

}
