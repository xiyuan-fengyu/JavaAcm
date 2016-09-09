package com.xiyuan.acm.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by xiyuan_fengyu on 2016/9/9.
 */
public class BasicHeap <T> {

    private ArrayList<T> datas = new ArrayList<>();

    private HashMap<T, Integer> dataIndexs = new HashMap<>();

    private final Comparator<T> comparator;

    private int size = 0;

    public BasicHeap(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void push(T item) {
        datas.add(item);
        size ++;
        dataIndexs.put(item, size - 1);
        checkParent(size - 1);
    }

    public T pop() {
        if (size == 0) {
            return null;
        }
        else if (size == 1) {
            T top = datas.remove(0);
            dataIndexs.remove(top);
            size = 0;
            return top;
        }
        else {
            swap(0, size - 1);
            T top = datas.remove(size - 1);
            size--;
            dataIndexs.remove(top);
            checkChild(0);
            return top;
        }
    }

    public T top() {
        if (size == 0) {
            return null;
        }
        else {
            return datas.get(0);
        }
    }

    public T remove(T item) {
        if (dataIndexs.containsKey(item)) {
            int removeIndex = dataIndexs.get(item);
            if (removeIndex == size - 1) {
                T removeItem = datas.remove(removeIndex);
                size--;
                dataIndexs.remove(removeItem);
                return removeItem;
            }
            else {
                swap(removeIndex, size - 1);
                T removeItem = datas.remove(size - 1);
                size--;
                dataIndexs.remove(removeItem);
                checkChild(removeIndex);
                checkParent(removeIndex);
                return removeItem;
            }
        }
        else {
            return null;
        }
    }


    public boolean isEmpty() {
        return size <= 0;
    }

    private void checkParent(int childIndex) {
        int parentIndex;
        while ((parentIndex = parentIndex(childIndex)) > -1) {
            T child = datas.get(childIndex);
            T parent = datas.get(parentIndex);
            if (comparator.compare(child, parent) < 0) {
                swap(childIndex, parentIndex);
            }
            else {
                break;
            }
            childIndex = parentIndex;
        }
    }

    private void checkChild(int parentIndex) {
        while (leftChildIndex(parentIndex) < size) {
            int leftId = leftChildIndex(parentIndex);
            int rightId = rightChildIndex(parentIndex);
            int son;
            if (rightId >= size || (comparator.compare(datas.get(leftId), datas.get(rightId)) < 0)) {
                son = leftId;
            } else {
                son = rightId;
            }
            if (comparator.compare(datas.get(son), datas.get(parentIndex)) < 0) {
                swap(son, parentIndex);
            } else {
                break;
            }
            parentIndex = son;
        }
    }

    private void swap(int index1, int index2) {
        T item1 = datas.get(index1);
        T item2 = datas.get(index2);
        datas.set(index1, item2);
        datas.set(index2, item1);
        dataIndexs.put(item2, index1);
        dataIndexs.put(item1, index2);
    }

    private int parentIndex(int index) {
        return index == 0? -1: (index - 1) / 2;
    }

    private int leftChildIndex(int index) {
        return index * 2 + 1;
    }

    private int rightChildIndex(int index) {
        return index * 2 + 2;
    }

}
