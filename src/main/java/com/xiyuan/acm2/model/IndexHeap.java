package com.xiyuan.acm2.model;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by xiyuan_fengyu on 2017/4/6.
 */
public class IndexHeap<T extends IndexHeap.IndexT> {

    private final Comparator<T> comparator;

    private final ArrayList<T> datas;

    public IndexHeap(Comparator<T> comparator) {
        this.comparator = comparator;
        datas = new ArrayList<>();
    }

    public void push(T newNode) {
        newNode.index = datas.size();
        datas.add(newNode);
        checkParent(newNode.index);
    }

    private void checkParent(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (comparator.compare(datas.get(parentIndex), datas.get(index)) > 0) {
                swap(index, parentIndex);
                index = parentIndex;
            }
            else {
                break;
            }
        }
    }

    private void checkChild(int index) {
        int len = datas.size();
        int childIndex;
        while ((childIndex = index * 2 + 1) < len) {
            if (childIndex + 1 < len) {
                if (comparator.compare(datas.get(childIndex), datas.get(childIndex + 1)) > 0) {
                    childIndex++;
                }
            }

            if (comparator.compare(datas.get(index), datas.get(childIndex)) > 0) {
                swap(index, childIndex);
                index = childIndex;
            }
            else {
                break;
            }
        }
    }

    private void swap(int i0, int i1) {
        T temp0 = datas.get(i0);
        T temp1 = datas.get(i1);
        datas.set(i0, temp1);
        datas.set(i1, temp0);
        temp0.index = i1;
        temp1.index = i0;
    }

    public T pop() {
        if (datas.isEmpty()) {
            return null;
        }
        else {
            int lastIndex = datas.size() - 1;
            swap(0, lastIndex);
            T temp = datas.remove(lastIndex);
            checkChild(0);
            return temp;
        }
    }

    public T top() {
        return datas.isEmpty() ? null : datas.get(0);
    }

    public T remove(T t) {
        if (t == null || datas.isEmpty()) {
            return null;
        }

        int size = datas.size();
        int index = t.index;
        if (index >= size) {
            return null;
        }

        int lastIndex = size - 1;
        if (index == lastIndex) {
            return datas.remove(lastIndex);
        }
        else {
            T temp = datas.get(index);
            swap(index, lastIndex);
            datas.remove(lastIndex);
            checkChild(index);
            checkParent(index);
            return temp;
        }
    }

    public int size() {
        return datas.size();
    }

    public boolean isEmpty() {
        return datas.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, size = datas.size(); i < size; i++) {
            builder.append(datas.get(i));
            if (i + 1 < size) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }

    public static class IndexT {
        protected int index;
    }
}
