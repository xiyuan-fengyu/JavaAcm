package com.xiyuan.acm.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xiyuan_fengyu on 2016/11/23.
 * http://www.lintcode.com/zh-cn/problem/flatten-nested-list-iterator/
 */
public class NestedIterator implements Iterator<Integer>{

    private List<Integer> data;

    private int index;

    public NestedIterator(List<NestedInteger> nestedList) {
        index = 0;
        data = new ArrayList<>();
        for (NestedInteger node: nestedList) {
            flatList(node);
        }
    }

    public void flatList(NestedInteger nested) {
        if (nested.isInteger()) {
            data.add(nested.getInteger());
        }
        else {
            for (NestedInteger node: nested.getList()) {
                flatList(node);
            }
        }
    }

    // @return {int} the next element in the iteration
    @Override
    public Integer next() {
        return data.get(index++);
    }

    // @return {boolean} true if the iteration has more element or false
    @Override
    public boolean hasNext() {
        return index < data.size();
    }

    @Override
    public void remove() {

    }


    public static void test() {
        List<NestedInteger> list = new ArrayList<>();

        NestedIntegerList listNode1 = new NestedIntegerList();
        listNode1.add(new NestedIntegerNode(1));
        listNode1.add(new NestedIntegerNode(2));
        list.add(listNode1);

        list.add(new NestedIntegerNode(3));

        NestedIntegerList listNode2 = new NestedIntegerList();
        listNode1.add(new NestedIntegerNode(4));
        NestedIntegerList listNode3 = new NestedIntegerList();
        listNode3.add(new NestedIntegerNode(5));
        listNode3.add(new NestedIntegerNode(6));
        listNode2.add(listNode3);
        list.add(listNode2);

        NestedIterator it = new NestedIterator(list);
        while (it.hasNext()) {
            System.out.println(it.next());
            it.remove();
        }

        System.out.println(it.hasNext());
    }

    public interface NestedInteger {
        boolean isInteger();
        Integer getInteger();
        List<NestedInteger> getList();
    }

    private static class NestedIntegerNode implements NestedInteger {

        private Integer val;

        public NestedIntegerNode(Integer val) {
            this.val = val;
        }

        @Override
        public boolean isInteger() {
            return true;
        }

        @Override
        public Integer getInteger() {
            return val;
        }

        @Override
        public List<NestedInteger> getList() {
            return null;
        }
    }

    private static class NestedIntegerList implements NestedInteger {

        private List<NestedInteger> list;

        public NestedIntegerList() {
            this.list = new ArrayList<>();
        }

        public void add(NestedInteger item) {
            list.add(item);
        }

        @Override
        public boolean isInteger() {
            return false;
        }

        @Override
        public Integer getInteger() {
            return null;
        }

        @Override
        public List<NestedInteger> getList() {
            return list;
        }
    }

}
