package com.xiyuan.acm.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by xiyuan_fengyu on 2017/4/6.
 */
public class Heap<T> {

    private final Comparator<T> comparator;

    private final ArrayList<T> datas;

    public Heap(Comparator<T> comparator) {
        this.comparator = comparator;
        datas = new ArrayList<>();
    }

    public void push(T newNode) {
        datas.add(newNode);
        checkParent(datas.size() - 1);
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
        T temp = datas.get(i0);
        datas.set(i0, datas.get(i1));
        datas.set(i1, temp);
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

        for (int i = 0, size = datas.size(); i < size; i++) {
            T temp = datas.get(i);
            if (t.equals(temp)) {
                int lastIndex = size - 1;
                if (i == lastIndex) {
                    return datas.remove(lastIndex);
                }
                else {
                    swap(i, lastIndex);
                    datas.remove(lastIndex);
                    checkChild(i);
                    checkParent(i);
                    return temp;
                }
            }
        }
        return null;
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
}
