package com.xiyuan.acm.util;

import java.util.ArrayList;

/**
 * Created by xiyuan_fengyu on 2016/9/24.
 */
public class ArrayListUtil {

    public static <T> ArrayList<T> build(T ... ts) {
        ArrayList<T> result = new ArrayList<>();
        if (ts != null) {
            for (T t: ts) {
                result.add(t);
            }
        }
        return result;
    }

    public static <T> void addTo(ArrayList<T> list, T ... ts) {
        if (list != null && ts != null) {
            for (T t: ts) {
                list.add(t);
            }
        }
    }


}
