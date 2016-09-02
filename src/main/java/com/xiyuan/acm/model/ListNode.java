package com.xiyuan.acm.model;

/**
 * Created by xiyuan_fengyu on 2016/9/1.
 */
public class ListNode {
    public int val;
    public ListNode next;
    public ListNode(int val) {
        this.val = val;
        this.next = null;
    }

    private int toStringVisitIndex = 0;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ListNode cur = this;
        toStringVisitIndex = 0;
        ListNode tail = findCircleTail(cur, 0);
        boolean isTailVisied = false;
        while (cur != null) {
            if (cur == tail) {
                if (isTailVisied) {
                    sb.append("connects to node[").append(cur.toStringVisitIndex).append("]");
                    break;
                }
                else {
                    isTailVisied = true;
                    sb.append(cur.val);
                    cur = cur.next;
                    if (cur != null) {
                        sb.append(" -> ");
                    }
                }
            }
            else {
                sb.append(cur.val);
                cur = cur.next;
                if (cur != null) {
                    sb.append(" -> ");
                }
            }
        }
        return sb.toString();
    }

    private ListNode findCircleTail(ListNode node, int index) {
        if (node.next != null) {
            ListNode next = node.next;
            node.next = null;
            node.toStringVisitIndex = index;
            ListNode tail = findCircleTail(next, index + 1);
            node.next = next;
            return tail;
        }
        else {
            return node;
        }
    }
}
