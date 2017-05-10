package com.xiyuan.acm2.model;

import java.util.HashMap;

/**
 * Created by xiyuan_fengyu on 2017/4/11.
 * 最近最频繁使用的cache
 */
public class LRUCache {

    private final int capacity;

    private final HashMap<Integer, Entry> caches;

    private Entry head;

    private Entry tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        caches = new HashMap<>(capacity);
        head = new Entry(0, 0);
        tail = new Entry(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    public void set(int key, int value) {
        Entry item = caches.get(key);
        if (item != null) {
            item.value = value;
            moveAfterHead(item);
        }
        else {
            if (caches.size() == this.capacity) {
                removeBeforeTail();
            }
            item = new Entry(key, value);
            caches.put(key, item);
            insertAfterHead(item);
        }
    }

    public int get(int key) {
        Entry item = caches.get(key);
        if (item != null) {
            moveAfterHead(item);
            return item.value;
        }
        return -1;
    }

    private void moveAfterHead(Entry item) {
        Entry prev = item.prev;
        if (prev != head) {
            Entry next = item.next;
            prev.next = next;
            next.prev = prev;
            insertAfterHead(item);
        }
    }

    private void insertAfterHead(Entry item) {
        Entry next = head.next;
        head.next = item;
        item.next = next;
        next.prev = item;
        item.prev = head;
    }

    private void removeBeforeTail() {
        Entry item = tail.prev;
        if (item != head) {
            Entry prev = item.prev;
            prev.next = tail;
            tail.prev = prev;
            caches.remove(item.key);
        }
    }

    private static class Entry {
        private final int key;
        private int value;
        private Entry prev;
        private Entry next;
        private Entry(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

}
