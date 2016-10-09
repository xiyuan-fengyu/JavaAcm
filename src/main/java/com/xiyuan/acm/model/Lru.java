package com.xiyuan.acm.model;

import java.util.HashMap;

/**
 * Created by xiyuan_fengyu on 2016/10/9.
 * http://www.lintcode.com/zh-cn/problem/lru-cache/
 */
public class Lru {

    private final int capacity;

    private HashMap<Integer, Data> datas = new HashMap<>();

    public Lru(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (datas.containsKey(key)) {
            Data data = datas.get(key);
            data.ru++;
            //TODO 重排序

            return data.value;
        }
        else return -1;
    }

    public void set(int key, int value) {
        if (datas.containsKey(key)) {
            Data data = datas.get(key);
            data.value = value;
            data.ru++;
            //TODO 重排序

        }
        else {
            if (datas.size() < capacity) {
                datas.put(key, new Data(value));
                //TODO 重排序

            }
            else {
                //TODO 将堆顶元素替换掉

            }
        }
    }

    private class Data {
        public int value;
        public int ru;

        public Data(int value) {
            this.value = value;
            ru = 0;
        }
    }

    private class DataHeap {

    }

}
