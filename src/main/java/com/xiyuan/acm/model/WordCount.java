package com.xiyuan.acm.model;

import java.util.Iterator;

/**
 * 单词计数 (Map Reduce版本)   [容易]
 * http://www.lintcode.com/zh-cn/problem/word-count-map-reduce/
 * Created by xiyuan_fengyu on 2016/11/3.
 */
public class WordCount {

    public static class Map {
        public void map(String key, String value, OutputCollector<String, Integer> output) {
            String[] split = value.split(" ");
            for (String item: split) {
                output.collect(item, 1);
            }
        }
    }

    public static class Reduce {
        public void reduce(String key, Iterator<Integer> values,
                           OutputCollector<String, Integer> output) {
            int total = 0;
            while (values.hasNext()) {
                total += values.next();
            }
            output.collect(key, total);
        }
    }

    public static class OutputCollector<K, V> {
        public void collect(K key, V value) {

        }
    }

}
