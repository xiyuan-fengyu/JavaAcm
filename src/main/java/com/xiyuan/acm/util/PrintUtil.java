package com.xiyuan.acm.util;

/**
 * Created by xiyuan_fengyu on 2016/10/27.
 */
public class PrintUtil {

    public static void indexs(int from, int to, int perNumLen, int gap) {
        StringBuilder strBld = new StringBuilder();
        int delta = from < to? 1: -1;
        String gapStr = "";
        for (int i = 0; i < gap; ++i) {
            gapStr += " ";
        }
        for (int i = from, end = to + delta; i != end; i += delta) {
            strBld.append(String.format("%" + perNumLen + "d", i)).append(gapStr);
        }
        System.out.println(strBld.toString());
    }

    public static void arr(int[] arr, int perNumLen, int gap) {
        StringBuilder strBld = new StringBuilder();
        String gapStr = "";
        for (int i = 0; i < gap; ++i) {
            gapStr += " ";
        }
        for (int i: arr) {
            strBld.append(String.format("%" + perNumLen + "d", i)).append(gapStr);
        }
        System.out.println(strBld.toString());
    }

}
