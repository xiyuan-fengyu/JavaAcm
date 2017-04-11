package com.xiyuan.acm2.model;

import java.util.List;

/**
 * Created by xiyuan_fengyu on 2017/4/11.
 */
public class NestedIntegerList implements NestedInteger {

    private List<NestedInteger> list;

    public NestedIntegerList(List<NestedInteger> list) {
        this.list = list;
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
