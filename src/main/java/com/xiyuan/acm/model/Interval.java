package com.xiyuan.acm.model;

/**
 * Created by xiyuan_fengyu on 2016/10/21.
 */
public class Interval {
    public int start;
    public int end;
    public Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "(" + start + ", " + end + ")";
    }
}
