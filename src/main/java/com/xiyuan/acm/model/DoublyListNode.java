package com.xiyuan.acm.model;

/**
 * Created by xiyuan_fengyu on 2017/4/6.
 */
public class DoublyListNode {

    public int val;
    public DoublyListNode next;
    public DoublyListNode prev;

    public DoublyListNode(int val) {
        this.val = val;
        this.next = null;
        this.prev = null;
    }

    @Override
    public String toString() {
        return val + (next == null ? "" : " <-> " + next.toString());
    }
}
