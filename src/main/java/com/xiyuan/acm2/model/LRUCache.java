package com.xiyuan.acm2.model;

import java.util.HashMap;

/**
 * Created by xiyuan_fengyu on 2017/4/11.
 * 最近最频繁使用的cache
 */
public class LRUCache {

    private final HashMap<Integer, Integer> caches;

    private final int[] keys;

    public LRUCache(int capacity) {
        caches = new HashMap<>(capacity);
        keys = new int[capacity];
    }

    public void set(int key, int value) {
        if (caches.containsKey(key)) {
            caches.put(key, value);
//            resortKeys();
        }
        else {
            int size = caches.size();
            if (size == keys.length) {
                caches.remove(keys[size - 1]);
                size--;
            }
            caches.put(key, value);
            keys[size] = key;
//            resortKeys();
        }
    }

    public int get(int key) {
        if (caches.containsKey(key)) {
            int value = caches.get(key);
//            resortKeys();
            return value;
        }
        return -1;
    }

    private void swapKeys(int i1, int i2) {
        int temp = keys[i1];
        keys[i1] = keys[i2];
        keys[i2] = temp;
    }

    private void resortKeys(int index) {

    }

}
