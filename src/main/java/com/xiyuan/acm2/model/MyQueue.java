package com.xiyuan.acm2.model;

import java.util.Stack;

/**
 * Created by xiyuan_fengyu on 2017/4/12.
 */
public class MyQueue {
    private Stack<Integer> stack1;
    private Stack<Integer> stack2;

    public MyQueue() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    public void push(int element) {
        stack2.push(element);
    }

    public int pop() {
        if (stack1.isEmpty()) {
            stack2ToStack1();
        }
        return stack1.pop();
    }

    public int top() {
        if (stack1.isEmpty()) {
            stack2ToStack1();
        }
        return stack1.peek();
    }

    private void stack2ToStack1() {
        while (!stack2.isEmpty()) {
            stack1.push(stack2.pop());
        }
    }

}
