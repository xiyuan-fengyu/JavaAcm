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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ListNode cur = this;
        while (cur != null) {
            sb.append(cur.val);
            cur = cur.next;
            if(cur != null)
            {
                sb.append(" -> ");
            }
        }
        return sb.toString();
    }
}
