package com.xiyuan.acm.model;

import java.util.Stack;

/**
 * Created by xiyuan_fengyu on 2016/10/21.
 * http://www.lintcode.com/zh-cn/problem/mock-hanoi-tower-by-stacks/
 */
public class Tower {
    private Stack<Integer> disks;

    public Tower(int i) {
        disks = new Stack<>();
    }

    public void add(int d) {
        if (!disks.isEmpty() && disks.peek() <= d) {
            System.out.println("Error placing disk " + d);
        } else {
            disks.push(d);
        }
    }

    public void moveTopTo(Tower t) {
        t.disks.push(this.disks.pop());
    }

    public void moveDisks(int n, Tower destination, Tower buffer) {
        if (n > 0) {
            if (n == 1) {
                moveTopTo(destination);
            }
            else {
                moveDisks(n - 1, buffer, destination);
                moveTopTo(destination);
                buffer.moveDisks(n - 1, destination, this);
            }
        }
    }

    public Stack<Integer> getDisks() {
        return disks;
    }
}
