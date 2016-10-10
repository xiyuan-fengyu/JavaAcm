package com.xiyuan.acm.model;

import java.util.HashMap;

/**
 * Created by xiyuan_fengyu on 2016/10/9.
 * http://www.lintcode.com/zh-cn/problem/lru-cache/
 */
public class Lru {

    private final int capacity;

    private final HashMap<Integer, DataNode> dataMap = new HashMap<>();

    private DataNode head = new DataNode(-1, -1);

    private DataNode tail = new DataNode(-1, -1);

    public Lru(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (!dataMap.containsKey(key)) {
            return -1;
        }

        DataNode node = dataMap.get(key);
        removeNode(node);
        insertBeforeTail(node);
        return node.value;
    }

    public void set(int key, int value) {
        DataNode node = dataMap.get(key);
        if (node != null) {
            node.value = value;
            removeNode(node);
            insertBeforeTail(node);
        }
        else {
            if (dataMap.size() == capacity) {
                dataMap.remove(head.next.key);
                removeNode(head.next);
            }

            DataNode newData = new DataNode(key, value);
            dataMap.put(key, newData);
            insertBeforeTail(newData);
        }
    }

    private void removeNode(DataNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.prev = null;
        node.next = null;
    }

    private void insertBeforeTail(DataNode node) {
        DataNode tailPrev = tail.prev;

        tailPrev.next = node;
        node.prev = tailPrev;

        node.next = tail;
        tail.prev = node;
    }

    private class DataNode {

        private DataNode prev;

        private DataNode next;

        private int key;

        private int value;

        public DataNode(int key, int value) {
            this.key = key;
            this.value = value;
        }

    }

}
