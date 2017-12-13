package com.xiyuan.leetcode;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

/**
 * Created by xiyuan_fengyu on 2017/12/6 18:54.
 */
public class Util {

    private static JsonArray toJsonArray(String str) {
        return new JsonParser().parse(str).getAsJsonArray();
    }

//    private static Object newArr(Class clazz, JsonArray jsonArray) {
//        int len = jsonArray.size();
//        if (int.class == clazz) return new int[len];
//        if (long.class == clazz) return new long[len];
//        if (float.class == clazz) return new float[len];
//        if (double.class == clazz) return new double[len];
//        if (short.class == clazz) return new short[len];
//        return new Object[len];
//    }

    public static int[][] toIntArr2(String str) {
        JsonArray jsonArray = toJsonArray(str);
        int size = jsonArray.size();
        int[][] res = new int[size][];
        for (int i = 0; i < size; i++) {
            JsonArray sub = jsonArray.get(i).getAsJsonArray();
            int subSize = sub.size();
            res[i] = new int[subSize];
            for (int j = 0; j < subSize; j++) {
                res[i][j] = sub.get(j).getAsInt();
            }
        }
        return res;
    }

}
