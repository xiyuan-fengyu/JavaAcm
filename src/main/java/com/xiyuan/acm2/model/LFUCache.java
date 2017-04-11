package com.xiyuan.acm2.model;

import java.util.HashMap;

/**
 * Created by xiyuan_fengyu on 2017/4/11.
 * 最近最频繁使用的cache
 */
public class LFUCache {

    private final HashMap<Integer, int[]> caches;

    private final int[] keys;

    public LFUCache(int capacity) {
        caches = new HashMap<>(capacity);
        keys = new int[capacity];
    }

    public void set(int key, int value) {
        if (caches.containsKey(key)) {
            int[] valueAndCount = caches.get(key);
            valueAndCount[0] = value;
            valueAndCount[1]++;
            resortKeys(keyIndex(key));
        }
        else {
            int size = caches.size();
            if (size == keys.length) {
                caches.remove(keys[size - 1]);
            }

            caches.put(key, new int[] {value, 0});
            int index = caches.size() - 1;
            keys[index] = key;
            resortKeys(index);
        }
    }

    public int get(int key) {
        if (caches.containsKey(key)) {
            int[] valueAndCount = caches.get(key);
            valueAndCount[1]++;
            resortKeys(keyIndex(key));
            return valueAndCount[0];
        }
        return -1;
    }

    private void swapKeys(int i1, int i2) {
        int temp = keys[i1];
        keys[i1] = keys[i2];
        keys[i2] = temp;
    }

    private int keyIndex(int key) {
        for (int i = 0, size = caches.size(); i < size; i++) {
            if (key == keys[i]) {
                return i;
            }
        }
        return -1;
    }

    private void resortKeys(int index) {
        while (index > 0) {
            if (caches.get(keys[index])[1] >= caches.get(keys[index - 1])[1]) {
                swapKeys(index, index - 1);
            }
            else {
                break;
            }
            index--;
        }
    }

}
