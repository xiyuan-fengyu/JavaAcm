package com.xiyuan.acm2.model;

import java.util.List;

/**
 * Created by xiyuan_fengyu on 2017/4/11.
 */
public class NestedIntegerItem implements NestedInteger {

    private Integer val;

    public NestedIntegerItem(Integer val) {
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
