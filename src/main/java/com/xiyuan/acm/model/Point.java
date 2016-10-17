package com.xiyuan.acm.model;

/**
 * Created by xiyuan_fengyu on 2016/10/17.
 */
public class Point {

    public int x;

    public int y;

    public Point() {
        this.x = 0;
        this.y = 0;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
