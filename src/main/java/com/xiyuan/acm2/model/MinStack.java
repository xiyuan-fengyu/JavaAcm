package com.xiyuan.acm2.model;

/**
 * Created by xiyuan_fengyu on 2017/4/10.
 */

import java.util.ArrayList;

/**
 * 采用单个数字记录当前的最小值
 */
public class MinStack {

    ArrayList<Integer> datas = new ArrayList<>();

    int curMin = 0;

    public MinStack() {
    }

    public void push(int number) {
        if (datas.isEmpty()) {
            datas.add(0);
            curMin = number;
        }
        else {
            datas.add(number - curMin);
            if (curMin > number) {
                curMin = number;
            }
        }
    }

    public int pop() {
        int size = datas.size();
        if (size > 0) {
            int temp = datas.remove(size - 1);
            if (temp >= 0) {
                return temp + curMin;
            }
            else {
                int newMin = curMin - temp;
                temp += newMin;
                curMin = newMin;
            }
            return temp;
        }
        return 0;
    }

    public int min() {
        return curMin;
    }

}

///**
// * 采用ArrayList记录最小值
// */
//public class MinStack {
//
//    private ArrayList<Integer> datas = new ArrayList<>();
//
//    private ArrayList<Integer> mins = new ArrayList<>();
//
//    private Integer curMin = null;
//
//    public MinStack() {
//    }
//
//    public void push(int number) {
//        if (curMin == null || curMin > number) {
//            curMin = number;
//        }
//
//        datas.add(number);
//        mins.add(curMin);
//    }
//
//    public int pop() {
//        if (datas.size() > 0) {
//            int removeI = datas.size() - 1;
//            Integer temp = datas.remove(removeI);
//            mins.remove(removeI);
//            curMin = removeI > 0 ? mins.get(removeI - 1) : null;
//            return temp;
//        }
//        return 0;
//    }
//
//    public int min() {
//        return curMin == null ? 0 : curMin;
//    }
//
//}
