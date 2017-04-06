package com.xiyuan.acm.model;

import java.util.HashMap;

/**
 * http://www.lintcode.com/zh-cn/problem/lfu-cache/
 * set和get操作都增加一个访问次数
 * Created by xiyuan_fengyu on 2017/4/6.
 */
public class LFUCache {

    private HashMap<Integer, int[]> cache;

    private int capacity;

    private Integer[] keys;

    public LFUCache(int capacity) {
        cache = new HashMap<>();
        keys = new Integer[capacity];
        this.capacity = capacity;
    }

    public void set(int key, int value) {
        if (cache.containsKey(key)) {
            int[] oldValue = cache.get(key);
            oldValue[0] = value;
            oldValue[1]++;
            findKeyAndSort(key);
        }
        else {
            if (cache.size() >= capacity) {
                cache.remove(keys[capacity - 1]);
            }
            cache.put(key, new int[] {value, 1});
            int index = cache.size() - 1;
            keys[index] = key;
            sortKeys(index);
        }
    }

    private void findKeyAndSort(int key) {
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == key) {
                sortKeys(i);
                break;
            }
        }
    }

    private void sortKeys(int index) {
        for (int i = index; i > 0; i--) {
            if (cache.get(keys[i])[1] >= cache.get(keys[i - 1])[1]) {
                swap(i, i - 1);
            }
            else {
                break;
            }
        }
    }

    private void swap(int i1, int i2) {
        Integer temp = keys[i1];
        keys[i1] = keys[i2];
        keys[i2] = temp;
    }

    public int get(int key) {
        int[] value = cache.get(key);
        if (value != null) {
            value[1]++;
            findKeyAndSort(key);
            return value[0];
        }
        return -1;
    }
}
