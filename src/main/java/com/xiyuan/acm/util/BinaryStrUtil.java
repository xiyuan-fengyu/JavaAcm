package com.xiyuan.acm.util;

import java.util.Arrays;

/**
 * Created by xiyuan_fengyu on 2016/10/13.
 */
public class BinaryStrUtil {

    public static String intToBinaryStr(int i) {
        if (i >= 0) {
            String str = Integer.toBinaryString(i);
            char[] zeros = new char[32 - str.length()];
            Arrays.fill(zeros, '0');
            return new String(zeros) + str;
        }
        else {
            return Integer.toBinaryString(i);
        }
    }

}
